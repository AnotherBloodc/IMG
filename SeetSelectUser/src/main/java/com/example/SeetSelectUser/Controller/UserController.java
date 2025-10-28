package com.example.SeetSelectUser.Controller;

import com.example.SeetSelectUser.Entity.User;
import com.example.SeetSelectUser.Service.Impl.UserServiceImpl;
import com.example.SeetSelectUser.Tool.JwtUtils;
import com.example.SeetSelectUser.Tool.Return;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserServiceImpl userServiceImpl;

    @GetMapping("/t")
    public Return select(@ModelAttribute User user) {
        String token = JwtUtils.generateToken(user.getUsername());
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        List<User> userList = userServiceImpl.getAllUser();//查询所有用户
        System.out.println(user);
        System.out.println("token：" + token);
        System.out.println("用户名：" + username);
        return Return.ok(user.getUsername(), token);
    }

    @GetMapping("/f")
    public Return select1(@RequestParam String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        System.out.println("用户名：" + username + "-----------");
        System.out.println("token：" + token + "-----------");
        return Return.ok(username + "a", token);
    }
}
