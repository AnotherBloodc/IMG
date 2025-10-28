package com.example.SeetLeft.Mapper.TableS.Impl;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.TableS.User_UserMapperStyle;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User_UserStyleMapperImpl {

    @Resource
    private User_UserMapperStyle userStyle;

    /**
     * 多表条件查询
     *
     * @param user 查询条件
     * @return 包含查询信息的用户列表
     */
    public List<User> selectStyle(User user) {
        return userStyle.selectUser(user);
    }
}
