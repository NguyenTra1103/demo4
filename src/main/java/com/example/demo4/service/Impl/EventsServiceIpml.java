package com.example.demo4.service.Impl;

import com.example.demo4.contant.Status;
import com.example.demo4.converter.DateTimeConverter;
import com.example.demo4.domain.Events;
import com.example.demo4.repository.EventsRepository;

import com.example.demo4.request.event.CreateEventsRequest;
import com.example.demo4.request.event.UpdateEventsRequest;
import com.example.demo4.response.EventsResponse;
import com.example.demo4.response.EventsSearchResponse;
import com.example.demo4.service.EventsService;
import com.example.demo4.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class EventsServiceIpml implements EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public Events insertEvents(String token, CreateEventsRequest creatEventsRequest) {
        String uid = jwtService.parseTokenToId(token);
        Events events = new Events();
        events.setTitle(creatEventsRequest.getTitle());
        events.setContent(creatEventsRequest.getContent());
        events.setFinishTime(DateTimeConverter.fromDateToString(creatEventsRequest.getFinishTime()));
        events.setStartTime(DateTimeConverter.fromDateToString(creatEventsRequest.getStartTime()));
        events.setBanner(creatEventsRequest.getBanner());
        eventsRepository.insert(events);
        return events;
    }

    @Override
    public Optional<Events> findById(String id) {
        return eventsRepository.findById(id);
    }

    @Override
    public Events updateEvents(String id, String token, UpdateEventsRequest updateEventsRequest) {
        Events events = eventsRepository.findById(id).get();
        events.setTitle(updateEventsRequest.getTitle());
        events.setContent(updateEventsRequest.getContent());
        events.setBanner(updateEventsRequest.getBanner());
        events.setStartTime(updateEventsRequest.getStartTime());
        events.setFinishTime(updateEventsRequest.getFinishTime());
        return eventsRepository.save(events);
    }

    @Override
    public void deleteNewsById(String id) {
        Optional<Events> deleteNews = findById(id);
        if (deleteNews.isPresent()) {
            eventsRepository.deleteById(id);
        }
    }

    @Override
    public Events getEventStatus(Events events) {

        Date start = DateTimeConverter.fromStringToDate(events.getStartTime());
        Date end = DateTimeConverter.fromStringToDate(events.getFinishTime());
        Date now = new Date(System.currentTimeMillis());

        if (now.before(start)) {
            events.setStatus(Status.INCOMING);
        } else if (now.after(end)) {
            events.setStatus(Status.ENDED);
        } else {
            events.setStatus(Status.HAPPENING);
        }
        eventsRepository.save(events);
        return events;
    }

    @Override
    public List<Events> getListEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public List<EventsSearchResponse> getListEventStatus(Status status) {
        return eventsRepository.findEventsByStatusIs(status)
                .stream()
                .map(event -> new EventsSearchResponse(event.getTitle(),
                        event.getStartTime(),
                        event.getFinishTime(),
                        event.getStatus()))
                .collect(Collectors.toList());
    }

}
