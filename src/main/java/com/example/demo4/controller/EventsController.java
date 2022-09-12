package com.example.demo4.controller;

import com.example.demo4.contant.Role;
import com.example.demo4.contant.Status;
import com.example.demo4.domain.Events;
import com.example.demo4.request.event.CreateEventsRequest;
import com.example.demo4.request.event.UpdateEventsRequest;
import com.example.demo4.response.EventsSearchResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.service.EventsService;

import com.example.demo4.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("demo/v1/events")
@RequiredArgsConstructor
public class EventsController {
    private final JwtService jwtService;
    private final EventsService eventsService;

    @PostMapping()
    public ResponseEntity<ObjectResponse> createEvents(@RequestHeader("Authorization") String token,
                                                       @Valid @RequestBody CreateEventsRequest creatEventsRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));
        Events insertEvents = eventsService.insertEvents(token, creatEventsRequest);
        if (role.equals(Role.ADMIN)) {
            return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                    "CREAT EVENTS SUCCESS",
                    insertEvents));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ObjectResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                        "PERMISSION DENIED",
                        null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse> updateEvents(@Valid @RequestHeader("Authorization") String token,
                                                       @PathVariable("id") String id,
                                                       @RequestBody UpdateEventsRequest updateEventsRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));

        if (!isNews(id)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ObjectResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                            "NOT FOUND",
                            null));
        }
        if (role.equals(Role.ADMIN)) {
            Events events = eventsService.updateEvents(id, token, updateEventsRequest);
            return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                    "UPDATE EVENTS SUCCESS",
                    events));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ObjectResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                        "PERMISSION DENIED",
                        null));
    }

    public boolean isNews(String id) {
        Optional<Events> news = eventsService.findById(id);
        if (news.isPresent()) {
            return true;
        }
        return false;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectResponse> deleteNews(@PathVariable(name = "id") String id,
                                                     @RequestHeader("Authorization") String token) {
        String checkID = jwtService.parseTokenToId(token);
        Optional<Events> newsToDelete = eventsService.findById(id);
        if (!checkID.equals(newsToDelete.get().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new
                    ObjectResponse(HttpStatus.UNAUTHORIZED.value(),
                    "UNAUTHORIZED TO DELETE EVENTS",
                    null));
        }
        eventsService.deleteNewsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                "DELETE EVENTS WITH ID: " + id + " SUCCESS",
                null));
    }

    @GetMapping("{id}")
    public ResponseEntity<ObjectResponse> getEventById(@PathVariable(name = "id") String id) {
        Optional<Events> findById = eventsService.findById(id);
        if (findById.isPresent()) {
            Events event = eventsService.getEventStatus(findById.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                    "GET EVENT BY ID " + id,
                    event));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ObjectResponse(HttpStatus.NO_CONTENT.value(),
                        "EVENTS WITH ID " + id + " IS EMPTY!",
                        null));
    }

    @GetMapping
    public ResponseEntity<ObjectResponse> getListNews(
            @RequestParam(required = false) String status) {

        if (status == null) {
            List<Events> newList = eventsService.getListEvents();
            return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(),
                    "GET LIST EVENT SUCCESS",
                    newList));
        }
        List<EventsSearchResponse> page = eventsService.getListEventStatus(Status.valueOf(status));
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(),
                "GET LIST EVENT SUCCESS" +status,
                page));
    }
}

