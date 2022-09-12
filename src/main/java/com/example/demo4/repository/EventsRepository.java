package com.example.demo4.repository;

import com.example.demo4.contant.Status;
import com.example.demo4.domain.Events;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface EventsRepository extends MongoRepository<Events, String>, PagingAndSortingRepository<Events,String> {
     Optional<Events> findById(String id);
     List<Events> findEventsByStatusIs(Status status);

  /*   List<Events> findEventsByStatusAndOrderByStartTime(Status status);

     List<Events> findAllBy*/
}
