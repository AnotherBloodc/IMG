package com.example.SeetLeft.Service;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.Impl.UserStyleMapperImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStyleSerivce {

    @Resource
    private UserStyleMapperImpl userStyleMapperImpl;

    public List<User> select(User user) {
        return userStyleMapperImpl.select(user);
    }
}
