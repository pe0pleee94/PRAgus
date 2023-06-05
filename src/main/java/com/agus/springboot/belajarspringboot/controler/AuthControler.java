package com.agus.springboot.belajarspringboot.controler;

import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.LoginUserRequest;
import com.agus.springboot.belajarspringboot.model.TokenResponse;
import com.agus.springboot.belajarspringboot.model.WebResponse;
import com.agus.springboot.belajarspringboot.service.impl.AuthService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class AuthControler {

    @Autowired
    AuthService authService;

    @PostMapping(path = "/api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse>login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);

        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();

    }
    @DeleteMapping(path = "/api/auth/logout",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout (User user){
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();
    }
}
