package com.example.demo4.service.Impl;

import com.example.demo4.contant.RequestStatus;
import com.example.demo4.contant.RequestType;
import com.example.demo4.converter.DateTimeConverter;
import com.example.demo4.converter.RequestConverter;
import com.example.demo4.domain.Request;
import com.example.demo4.domain.User;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.RequestRepository;
import com.example.demo4.repository.UserRepository;
import com.example.demo4.request.requests.CreateRequestDelayType;
import com.example.demo4.request.requests.CreateRequestLeaveType;
import com.example.demo4.request.requests.UpdateStatusRequest;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.request.ListRequestResponse;
import com.example.demo4.response.request.RequestByEmailResponse;
import com.example.demo4.response.request.RequestResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;


    @Override
    public RequestResponse insertLateEarlyRequest(String token, CreateRequestDelayType createRequestDelayType) {
        String userId = jwtService.parseTokenToId(token);

        boolean checkEmailUser = userRepository.existsUserByEmail(createRequestDelayType.getRequiredEmail());

        if (!checkEmailUser) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }
        Request request = requestRepository.findFirstByUserIdOrderByIdDesc(userId);

        Request requestToInsert;

        if (request == null) {
            requestToInsert = RequestConverter.fromDelayResponse(userId,createRequestDelayType );
      /*  } else if () {*/
            
        } else {
            requestToInsert = RequestConverter.fromDelayResponse(userId, createRequestDelayType, request.getDayRequest(), request.getRemainTime());
        }
        if (requestToInsert.getRemainTime()< 0) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_ACCEPTABLE, ObjectResponse.MESSAGE_NO_REMAINING_FOR_CREATE_LATESOON_REQUEST);
        }
        Request requestInsertDelay = RequestConverter.fromDelayResponse(userId, createRequestDelayType);
        Request insert = requestRepository.insert(requestInsertDelay);
        RequestResponse response = RequestConverter.toResponse(insert);
        return response;
    }

    @Override
    public RequestResponse insertLeaveRequest(String token, CreateRequestLeaveType requestLeaveType) {
        String userId = jwtService.parseTokenToId(token);

        boolean checkEmailUser = userRepository.existsUserByEmail(requestLeaveType.getRequiredEmail());
        if (!checkEmailUser) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }
        Request requestToInsert = RequestConverter.fromLeaveResponse(userId, requestLeaveType);
        List<Request> requests = requestRepository.findRequestsByYearRequestAndRequestType(DateTimeConverter.getCurrentYear(), RequestType.OFF);
        int sum = requests.stream().mapToInt(request -> (int) request.getTotalDay()).sum();
        if (sum + requestToInsert.getTotalDay() > 180) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_ACCEPTABLE, ObjectResponse.MESSAGE_NO_REMAINING_DAY_FOR_OFF_REQUEST);
        }
        Request insert = requestRepository.insert(requestToInsert);
        RequestResponse response = RequestConverter.fromLeaveResponse(insert, 180 - requestToInsert.getTotalDay());
        return response;

    }

    @Override
    public List<ListRequestResponse> findListByUserId(String token) {
        String userId = jwtService.parseTokenToId(token);
        List<ListRequestResponse> responses = (List<ListRequestResponse>) requestRepository.findRequestsByUserIdOrderByCreateRequestTimeDesc(userId)
                .stream()
                .map(request -> {
                    User user = userRepository.findUsersById(request.getUserId());
                    return ListRequestResponse.builder()
                            .requestTitle(request.getRequestTitle())
                            .content(request.getContent())
                            .requestStatus(request.getRequestStatus())
                            .timeRequest(RequestConverter.getTimeNeed(request))
                            .userName(user.getUsername())
                            .build();
                })
                .collect(Collectors.toList());
        return responses;
    }
    @Override
    public List<RequestByEmailResponse> findListRequestByReceiverEmail(String token) {
        String id = jwtService.parseTokenToId(token);
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,ObjectResponse.MESSAGE_USER_NOT_FOUND +id);
        }
        User user = optionalUser.get();
        List<RequestByEmailResponse> collect = requestRepository.findRequestsByReceiverEmailAndRequestStatus(user.getEmail(),RequestStatus.PENDING)
                .stream()
                .map(request -> {
                    User requestCreate = userRepository.findUsersById(request.getUserId());
                    return RequestByEmailResponse.builder()
                            .id(request.getId())
                            .requestStatus(request.getRequestStatus())
                            .requestTitle(request.getRequestTitle())
                            .timeRequest(request.getDayRequest())
                            .content(request.getContent())
                            .userName(requestCreate.getUsername())
                            .timeTotal(RequestConverter.getTimeNeed(request))
                            .build();
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public RequestResponse approveRequest(String requestId, String token, UpdateStatusRequest updateStatusRequest) {
       String email = jwtService.parseTokenToEmail(token);
       Optional<Request> optionalRequest = requestRepository.findRequestsByIdAndReceiverEmail(requestId,email);
       if (optionalRequest.isEmpty()){
           throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,ObjectResponse.MESSAGE_USER_NOT_FOUND+requestId);

       }
       Request request = optionalRequest.get();
       request.setRequestStatus(request.getRequestStatus());
       Request save = requestRepository.save(request);
      RequestResponse response = RequestConverter.toResponse(save);
       return response;

    }
}