package com.example.SeetLeft.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.MapperPro.LoginMapperPro;
import com.example.SeetLeft.Service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapperPro, User> implements LoginService {

    /**
     * 登录：用户登录，需要ID和邮箱，查询单个用户，如果存在则返回用户信息
     *
     * @return 用户信息
     */
    public User GetUser(int id, String username) {
        log.info(LoginEnums.GO + "Token 用户登录，需要ID和邮箱");
        if (id < 0 && username == null) {
            return null;
        }
        QueryWrapper<User> list = new QueryWrapper<>();
        list.eq("id", id);
        list.eq("username", username);
        return this.getOne(list);
    }

    /**
     * 登录：需要账号密码，查询单个用户，如果存在则返回用户信息
     *
     * @return 用户信息
     */
    public User GetUser(User user) {
        log.info(LoginEnums.GO + "账号密码用户登录，需要密码，还有手机号或者邮箱");

        //判断账号密码是否为空
        if (user.getPassword() == null || (user.getUsername() == null && user.getPhone() == null)) {
            log.info("账号或密码为空");
            return null;
        }
        //先通过账号获取用户的信息，然后判断密码是否正确
        QueryWrapper<User> list = new QueryWrapper<>();
        list.eq("username", user.getUsername());//设定条件，账号
        String password = user.getPassword();//这是用户提供的密码
        user = getOne(list);//获取数据库中的用户对象
        if (user == null) {
            log.info("用户提供的账号不存在");
            return null;
        }
        String passwordBCrypt = user.getPassword();//获取数据库中对应的密码

        //校验用户密码
        if (BCrypt.checkpw(password, passwordBCrypt)) {
            return user;
        }
        return null;
    }

    /**
     * 注册: 新增一个用户，成功则返回新增的用户数量
     *
     * @return 新增的用户数量
     */
    public int SetUser(User user) {
        //判断注册条件是否合法
        if (user != null) {

            //判断用户是否存在
            QueryWrapper<User> checkWrapper = new QueryWrapper<>();
            checkWrapper.eq("username", user.getUsername());
            if (this.getOne(checkWrapper) != null) {
                log.info("注册失败:用户已存在");
                return 0;
            }
            log.info("此用户在数据库中不存在，可注册");

            //密码加密
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            //执行注册
            boolean saveResult = this.save(user);
            if (saveResult) {
                log.info("注册成功");
                return 1;
            } else {
                log.info("注册失败:数据库操作异常");
                return 0;
            }
        }
        log.info("注册失败:用户信息为空");
        return 0;
    }

}
