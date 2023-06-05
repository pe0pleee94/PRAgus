package com.agus.springboot.belajarspringboot.service.impl;

import com.agus.springboot.belajarspringboot.BCrypt.BCrypt;
import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.LoginUserRequest;
import com.agus.springboot.belajarspringboot.model.TokenResponse;
import com.agus.springboot.belajarspringboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request){
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can't Found Username"));

    if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
    user.setToken(UUID.randomUUID().toString());
    user.setTokenExpiredAt(next30Days());
    userRepository.save(user);

    return TokenResponse.builder()
            .token(user.getToken())
            .expiredAt(user.getTokenExpiredAt())
            .build();

    }else{
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username or passsword Wrong");
    }
    }
    private Long next30Days(){
        return System.currentTimeMillis() + (100 * 16 * 24 * 30);
    }
    public void logout (User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }
}
