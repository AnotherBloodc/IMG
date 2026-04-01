package com.example.SeetLeft.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.Table.UserMapper;
import com.example.SeetLeft.Mapper.TableS.User_UserStyleMapper;
import com.example.SeetLeft.MapperPro.UserMapperPro;
import com.example.SeetLeft.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapperPro, User> implements UserService {



}
