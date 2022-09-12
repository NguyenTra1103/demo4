package com.example.demo4.controller;

import com.example.demo4.contant.Role;
import com.example.demo4.domain.Comment;
import com.example.demo4.request.comment.CreateCommentRequest;
import com.example.demo4.response.CommentResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.service.CommentService;
import com.example.demo4.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("demo/v1/comment")
@RequiredArgsConstructor
public class CommentController{
    private final JwtService jwtService;
    private final CommentService commentService;
    @PostMapping()
    public ResponseEntity<CommentResponse> createComment(@RequestHeader("Authorization") String token,
                                                         @Valid @RequestBody CreateCommentRequest createCommentRequest){
        Role role = Role.valueOf(jwtService.testRole(token));
        if(role != null){
            Comment comment = commentService.insertComment(token, createCommentRequest);
            return ResponseEntity.ok(new CommentResponse(HttpStatus.CREATED.value(),
                    "CREAT COMMENT SUCCESS",
                    comment.getId()));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new CommentResponse(HttpStatus.NO_CONTENT.value(),
                "CREATE COMMENT FAILED",
                null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectResponse> deleteComment(@PathVariable(name = "id") String id,
                                                        @RequestHeader("Authorization") String token){
        Role role = Role.valueOf(jwtService.testRole(token));
        String uid = jwtService.parseTokenToId(token);
        Optional<Comment> commentUserId = commentService.findById(id);
        if(role.equals(Role.ADMIN)){
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                    "DELETE COMMENT SUCCESSFUL",
                    null));
        }
        if(uid.equals(commentUserId.get().getUserId())){
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                    "DELETE COMMENT SUCCESSFUL",
                    null));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ObjectResponse(HttpStatus.OK.value(),
                "DELETE COMMENT FAILED",
                null));
    }
    @GetMapping()
    public List<Comment> getComment(@RequestParam(value = "skip", defaultValue = "0") int skip,
                                    @RequestParam(value = "take", defaultValue = "24") int take,
                                    @RequestParam(value = "sort", defaultValue = "content") String sortAttr,
                                    @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        Sort sort = Sort.by(sortAttr);
        if (desc)
            sort = sort.descending();
        Page<Comment> commentPage = commentService.findAllComment(PageRequest.of(skip, take, sort));
        return commentPage.getContent();
    }


}
