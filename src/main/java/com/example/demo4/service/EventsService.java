package com.example.demo4.service;

import com.example.demo4.contant.Status;
import com.example.demo4.domain.Events;
import com.example.demo4.request.event.CreateEventsRequest;
import com.example.demo4.request.event.UpdateEventsRequest;
import com.example.demo4.response.EventsSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface EventsService {
    Events insertEvents(String token, CreateEventsRequest creatEventsRequest);

    Optional<Events> findById(String id);

    Events updateEvents(String id,String token, UpdateEventsRequest updateEventsRequest);

    void deleteNewsById(String id);
    Events getEventStatus(Events events);
    List<Events> getListEvents();
    List<EventsSearchResponse> getListEventStatus(Status status);
   
    
}
