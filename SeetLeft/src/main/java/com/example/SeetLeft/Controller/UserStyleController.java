package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.UserStyleSerivce;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserStyleController {

    @Resource
    private UserStyleSerivce userStyleSerivce;

    @PostMapping("/userStyle")
    public List<User> select(User user) {
        return userStyleSerivce.select(user);
    }
}
