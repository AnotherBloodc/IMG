package com.example.SeetLeft.Mapper.TableMax.Impl;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.TableMax.User_UserStyle;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User_UserStyleImpl {

    @Resource
    private User_UserStyle userStyle;

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
