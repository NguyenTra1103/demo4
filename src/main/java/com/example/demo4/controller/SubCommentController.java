package com.example.demo4.controller;

import com.example.demo4.contant.Role;
import com.example.demo4.domain.Comment;
import com.example.demo4.domain.SubComment;
import com.example.demo4.request.subcomment.CreateSubCommentRequest;
import com.example.demo4.response.CommentResponse;
import com.example.demo4.service.CommentService;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("demo/v1/subcomments")
@RequiredArgsConstructor
@Document(collection = "subcomments")
public class SubCommentController {
    private final JwtService jwtService;
    private final SubCommentService subCommentService;
    private final CommentService commentService;
    @PostMapping()
    public ResponseEntity<CommentResponse> createSubComment(@RequestHeader("Authorization") String token,
                                                            @Valid @RequestBody CreateSubCommentRequest subCommentRequest){
        Role role = Role.valueOf(jwtService.testRole(token));
        if(role != null){
            SubComment subComment = subCommentService.insertSubComment(token, subCommentRequest);
            return ResponseEntity.ok(new CommentResponse(HttpStatus.CREATED.value(),
                    "CREATE SUB COMMENT SUCCESS",
                    subComment.getId()));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new CommentResponse(HttpStatus.NO_CONTENT.value(),
                "CREATE SUB COMMENT FAILED",
                null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponse> deleteSubComment(@RequestHeader("Authorization") String token,
                                                            @PathVariable(name = "id") String id){
        Role role = Role.valueOf(jwtService.testRole(token));
        String uid = jwtService.parseTokenToId(token);
        SubComment subComment = subCommentService.findById(id);
        if(role.equals(Role.ADMIN)){
            subCommentService.deleteSubComment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CommentResponse(HttpStatus.CONTINUE.value(),
                    "DELETE SUB COMMENT SUCCESS",
                    null));
        }
        if(subComment.getUserId().equals(uid)){
            subCommentService.deleteSubComment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CommentResponse(HttpStatus.CONTINUE.value(),
                    "DELETE SUB COMMENT SUCCESS",
                    null));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new CommentResponse(HttpStatus.OK.value(),
                "DELETE SUB COMMENT FAILED",
                null));
    }
    @GetMapping
    public List<SubComment> getComment(@RequestParam(value = "skip", defaultValue = "0") int skip,
                                       @RequestParam(value = "take", defaultValue = "20") int take,
                                       @RequestParam(value = "sort", defaultValue = "content") String sortAttr,
                                       @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        Sort sort = Sort.by(sortAttr);
        if (desc)
            sort = sort.descending();
        Page<SubComment> subCommentsPage = subCommentService.findAllSubComment(PageRequest.of(skip, take, sort));

        return subCommentsPage.getContent();
    }
    @GetMapping("/{commentId}")
    public List<SubComment> getSubComment(@PathVariable(name = "commentId") String commentId) {
        Optional<Comment> comment = commentService.findById(commentId);
        if(comment.isPresent()){
            List<SubComment> subCommentByCommentId = subCommentService.findSubCommentByCommentId(commentId);
            return subCommentByCommentId;
        }
        throw new RuntimeException("CAN'T NOT FOUND COMMENT BY ID" + commentId) ;
    }
}
