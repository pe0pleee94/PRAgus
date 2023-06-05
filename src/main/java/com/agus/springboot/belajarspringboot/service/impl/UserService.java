package com.agus.springboot.belajarspringboot.service.impl;

import com.agus.springboot.belajarspringboot.BCrypt.BCrypt;
import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.ContactResponse;
import com.agus.springboot.belajarspringboot.model.RegisterUserRequest;
import com.agus.springboot.belajarspringboot.model.UpdateUserRequest;
import com.agus.springboot.belajarspringboot.model.UserResponse;
import com.agus.springboot.belajarspringboot.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;


    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);



        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username all ready use/register");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);


    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();

    }
    @ Transactional
    public UserResponse update(User user, UpdateUserRequest request) {
        validationService.validate(request);

        log.info("Request : {}",request);

        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }
        if (Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
        userRepository.save(user);

        log.info("Request : {}",user.getName());

        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }



}
