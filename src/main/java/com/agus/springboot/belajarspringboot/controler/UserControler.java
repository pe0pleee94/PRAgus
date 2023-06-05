package com.agus.springboot.belajarspringboot.controler;

import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.RegisterUserRequest;
import com.agus.springboot.belajarspringboot.model.UpdateUserRequest;
import com.agus.springboot.belajarspringboot.model.UserResponse;
import com.agus.springboot.belajarspringboot.model.WebResponse;
import com.agus.springboot.belajarspringboot.service.impl.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserControler {
    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register (@RequestBody  RegisterUserRequest request){
userService.register(request);
        log.info("Login Request: {}", new Gson().toJson(request).replace("\"local", "\""));

return WebResponse.<String>builder().data("Succes Register").build();

    }
    @GetMapping(path = "/api/users/current",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> get (User user){
    UserResponse userResponse = userService.get(user);
    return WebResponse.<UserResponse>builder().data(userResponse).build();



    }
@PatchMapping(path = "/api/users/current",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse>update(User user, @RequestBody UpdateUserRequest request){
    UserResponse userResponse = userService.update(user,request);
    return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
}
