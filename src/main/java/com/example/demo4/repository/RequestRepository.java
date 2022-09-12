package com.example.demo4.repository;

import com.example.demo4.contant.RequestLeaveType;
import com.example.demo4.contant.RequestStatus;
import com.example.demo4.contant.RequestType;
import com.example.demo4.domain.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends MongoRepository<Request , String > {
    List<Request> findRequestsByUserIdOrderByCreateRequestTimeDesc(String id);

    Optional<Request> findRequestsByIdAndReceiverEmail(String requestId, String email);

    List<Request> findRequestsByReceiverEmailAndRequestStatus(String email, RequestStatus requestStatus);

    Request findFirstByUserIdOrderByIdDesc(String userId);

    List<Request> findRequestsByYearRequestAndRequestType(int year, RequestType type);


}
