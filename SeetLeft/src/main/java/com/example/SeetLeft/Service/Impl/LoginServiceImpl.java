package com.example.SeetLeft.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.MapperPro.LoginMapperPro;
import com.example.SeetLeft.Service.LoginService;
import com.example.SeetLeft.Tool.UserValidator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginServiceImpl extends ServiceImpl<LoginMapperPro, User> implements LoginService {

    @Resource
    private UserValidator userValidator;

    /**
     * 登录：查询单个用户，如果存在则返回用户信息
     *
     * @return 用户信息
     */
    public User GetUser(User user) {
        if (user != null) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(user.getUsername() != null, "username", user.getUsername()).eq(user.getUsername() != null, "password", user.getPassword());
            return this.getOne(queryWrapper);
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

            //判断用户名是否符合规则
            String userMsg = userValidator.validateUser(user.getUsername()).getUserMsg();
            String passMsg = userValidator.validateUser(user.getUsername()).getPassMsg();
            Boolean username = userValidator.validateUser(user.getUsername()).getUsername();
            Boolean password = userValidator.validateUser(user.getUsername()).getPassword();

            //开始判断
            if (username && password) {
                log.info(userMsg);
                log.info(passMsg);
                return 0; // 或者抛出异常
            }

            //判断用户是否存在
            QueryWrapper<User> checkWrapper = new QueryWrapper<>();
            checkWrapper.eq("username", user.getUsername());
            if (this.getOne(checkWrapper) != null) {
                log.info("注册失败:用户已存在");
                return 0;
            }

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
