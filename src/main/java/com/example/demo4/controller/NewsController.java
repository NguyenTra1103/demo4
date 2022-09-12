package com.example.demo4.controller;

import com.example.demo4.contant.Role;
import com.example.demo4.domain.News;


import com.example.demo4.request.news.CreateNewsRequest;
import com.example.demo4.request.news.UpdateNewsRequest;
import com.example.demo4.response.NewsResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("demo/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final JwtService jwtService;
    private final NewsService newsService;


    @PostMapping()
    public ResponseEntity<ObjectResponse> createNews(@RequestHeader("Authorization") String token,
                                                     @Valid @RequestBody CreateNewsRequest createNewsRequest) {

            News insertNews = newsService.insertNews(token, createNewsRequest);
            return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                    "CREAT NEWS SUCCESS",
                    insertNews));
    }


    @GetMapping
    public List<NewsResponse> getNewsByHashTags(@RequestParam(value = "limit", required = false,defaultValue = "10") int limit,
                                        @RequestParam(value = "hashTag", required = false) String hashTag,
                                        @RequestParam(value = "skip", required = false,defaultValue = "0") int skip,
                                        @PageableDefault(size = 10) Pageable pageable) {

        return newsService.findAll(hashTag,skip,limit );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse> updateNews(@Valid @RequestHeader("Authorization") String token,
                                                     @PathVariable("id") String id,
                                                     @RequestBody UpdateNewsRequest updateNewsRequest) {

        Role role = Role.valueOf(jwtService.testRole(token));

        if (!newsService.isNewsExist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ObjectResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                            "NOT FOUND",
                            null));
        }
        if (role.equals(Role.ADMIN)) {
            updateNewsRequest.setId(id);
            News news = newsService.updateNews(token, updateNewsRequest);
            return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                    "UPDATE NEWS SUCCESS",
                    news));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ObjectResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                        "PERMISSION DENIED",
                        null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectResponse> deleteNews(@PathVariable(name = "id") String id,
                                                     @RequestHeader("Authorization") String token) {
        String checkID = jwtService.parseTokenToId(token);
        Optional<News> newsToDelete = newsService.findById(id);
        if (!checkID.equals(newsToDelete.get().getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new
                    ObjectResponse(HttpStatus.UNAUTHORIZED.value(),
                    "UNAUTHORIZED TO DELETE NEWS",
                    null));
        }
        newsService.deleteNewsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                "DELETE NEWS WITH ID: " + id + " SUCCESSFUL",
                null));
    }
   @GetMapping("/{id}")
   public ResponseEntity<ObjectResponse> getNewsById(@PathVariable(name = "id") String id) {
       Optional<News> news = newsService.findById(id);
       if (news.isPresent()) {
           Optional<News> newsById = newsService.findById(id);
           return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                   "GET NEWS BY ID SUCCESS",
                   newsById));
       }
       return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
               "GET NEWS BY ID FAILED",
               null));
   }
}

