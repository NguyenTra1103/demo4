package com.example.demo4.service.Impl;


import com.example.demo4.domain.User;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.UserRepository;

import com.example.demo4.contant.Role;
import com.example.demo4.contant.UserStatus;

import com.example.demo4.request.user.CreateUserRequest;
import com.example.demo4.request.user.UpdateUserRequest;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.service.JwtService;

import com.example.demo4.service.UploadFileService;
import com.example.demo4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadFileService uploadFileService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUserAdmin(CreateUserRequest userRequest) {
        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setAvatarId(userRequest.getAvatar().getName());
        user.setUsername(userRequest.getUsername());
        user.setAge(userRequest.getAge());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRole(Role.ADMIN);
        return user;
    }

    @Override
    public User insertUserAdmin(CreateUserRequest userRequest) {
        if (userRequest.getKey() == null) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,
                    ObjectResponse.MESSAGE_USER_KEY_NULL);
        }
        if (isEmailDuplicate(userRequest.getEmail())) {
            throw new CustomException(ObjectResponse.STATUS_CODE_BAD_REQUEST,
                    ObjectResponse.MESSAGE_EMAIL_EXISTED);

        }
        if (isUserNameDuplicate(userRequest.getUsername())) {
            throw new CustomException(ObjectResponse.STATUS_CODE_BAD_REQUEST,
                    ObjectResponse.MESSAGE_USERNAME_EXISTED);
        }

        User user = createUserAdmin(userRequest);
        return userRepository.insert(user);
    }
    @Override
    public User insertUser(CreateUserRequest userRequest, String token) throws IOException {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (isUserNameDuplicate(userRequest.getUsername())) {
            throw new CustomException(ObjectResponse.STATUS_CODE_BAD_REQUEST, ObjectResponse.MESSAGE_USERNAME_EXISTED);
        }
        if (isEmailDuplicate(userRequest.getEmail())) {
            throw new CustomException(ObjectResponse.STATUS_CODE_BAD_REQUEST,ObjectResponse.MESSAGE_EMAIL_EXISTED);
        }
        if(!role.equals(Role.ADMIN)){
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED,ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        User user = createUser(userRequest);
        return userRepository.insert(user);
    }
    public User createUser(CreateUserRequest userRequest) throws IOException {
        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setAvatarId(uploadFileService.addFile(userRequest.getAvatar()));
        user.setAge(userRequest.getAge());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setRole(Role.USER);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(String token, UpdateUserRequest updateUserRequest, String id) {
        Role role = Role.valueOf(jwtService.testRole(token));
        String checkUser = jwtService.parseTokenToId(token);
        Optional<User> userToDelete = userRepository.findById(id);

        if (!isUserExist(id)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }
        if (role.equals(Role.ADMIN) || (checkUser.equals(userToDelete.get().getId()))) {
            String uuid = jwtService.parseTokenToId(token);
            Optional<User> user = userRepository.findById(updateUserRequest.getId());
            user.get().setUsername(updateUserRequest.getUsername());
            user.get().setAge(updateUserRequest.getAge());
            user.get().setAvatarId(updateUserRequest.getAvatar());
            return userRepository.save(user.get());
        }
        throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);

    }

    @Override
    public User changeStatus(String id, UpdateUserRequest updateUserRequest, String token) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (!isUserExist(id)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }
        if (!role.equals(Role.ADMIN)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        Optional<User> userChange = userRepository.findById((id));
        userChange.get().setUserStatus(updateUserRequest.getUserStatus());
        return userRepository.save(userChange.get());

    }



    private boolean isEmailDuplicate(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent())
            return true;
        return false;
    }

    private boolean isUserNameDuplicate(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isPresent())
            return true;
        return false;
    }

    public boolean isUserExist(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return true;
        return false;
    }
}
