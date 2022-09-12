package com.example.demo4.controller;

import com.example.demo4.domain.User;
import com.example.demo4.request.requests.CreateRequestDelayType;
import com.example.demo4.request.requests.CreateRequestLeaveType;
import com.example.demo4.request.requests.UpdateStatusRequest;
import com.example.demo4.request.user.CreateUserRequest;
import com.example.demo4.request.user.UpdateUserRequest;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.request.RequestByEmailResponse;
import com.example.demo4.response.request.RequestResponse;
import com.example.demo4.service.RequestService;
import com.example.demo4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("demo/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @PostMapping("/admin")
    public ResponseEntity<ObjectResponse> createUserAdmin(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.insertUserAdmin(createUserRequest);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                "CREAT USER ADMIN SUCCESS", user));
    }

    @PostMapping
    public ResponseEntity<ObjectResponse> createUser(@Valid @RequestHeader("Authorization") String token,
                                                     @ModelAttribute CreateUserRequest createUserRequest) throws IOException {
        User user = userService.insertUser(createUserRequest, token);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                "CREATE USER SUCCESS", user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse> updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        updateUserRequest.setId(id);
        User user = userService.updateUser(token, updateUserRequest, id);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                "UPDATE USER SUCCESS", user));
    }

    @PutMapping({"/status/{id}"})
    public ResponseEntity<ObjectResponse> updateStatusUser(@RequestHeader("Authorization") String token,
                                                           @PathVariable("id") String id,
                                                           @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        updateUserRequest.setId(id);
        User user = userService.changeStatus(id, updateUserRequest, token);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                "UPDATE USER SUCCESS", user));
    }

    // REQUEST USER

    @PostMapping("/late-early")
    public ResponseEntity<ObjectResponse> createDelayRequest(@Valid @RequestBody CreateRequestDelayType requestDelayType,
                                                                @RequestHeader("Authorization") String token) {
        RequestResponse response = requestService.insertLateEarlyRequest(token, requestDelayType);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(), "Create request delay complete", response));
    }
    @PostMapping("/leave")
    public ResponseEntity<ObjectResponse> createLeaveRequest(@Valid @RequestBody CreateRequestLeaveType requestLeaveType,
                                                                @RequestHeader("Authorization") String token) {
        RequestResponse response = requestService.insertLeaveRequest(token, requestLeaveType);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(), "Create request leave complete", response));
    }
    @GetMapping("/requests")
    public ResponseEntity<ObjectResponse> getList(@RequestHeader("Authorization") String token){
        List<RequestByEmailResponse> list = requestService.findListRequestByReceiverEmail(token);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(), "Get List Request", list));
    }
    @PutMapping("/request/{requestId}")
    public ResponseEntity<ObjectResponse> approveRequest(@PathVariable(name = "requestId") String requestId,
                                                         @RequestHeader("Authorization") String token,
                                                         @Valid @RequestBody UpdateStatusRequest updateStatusRequest) {
        updateStatusRequest.setId(requestId);
        RequestResponse response = requestService.approveRequest(requestId,token,updateStatusRequest );
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(), "Request status set to ",response));
    }
}
