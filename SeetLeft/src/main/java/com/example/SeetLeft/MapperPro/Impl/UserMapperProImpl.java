package com.example.SeetLeft.MapperPro.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.MapperPro.UserMapperPro;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class UserMapperProImpl {


    @Resource
    private UserMapperPro userMapperPro;

    /**
     * 插入用户信息到数据库
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    public int insert(User user) {
        return userMapperPro.insert(user);
    }

    /**
     * 根据ID删除记录
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public int delete(int Id) {
        return userMapperPro.deleteById(Id);
    }


    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public int update(User user) {
        return userMapperPro.updateById(user);
    }

    /**
     * 获取用户列表
     *
     * @return 包含用户对象的列表
     */
    public List<User> select(int user) {
        return Collections.singletonList(userMapperPro.selectById(user));
    }

    /**
     * 根据条件查询用户列表
     *
     * @param user 查询条件，如果为null则返回所有用户
     * @return 符合条件的用户列表
     */
    public List<User> selectPro(User user) {
        if (user == null) {
            // 如果user为null，返回所有用户
            return userMapperPro.selectList(null);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(user.getUsername() != null, "username", user.getUsername())
                .eq(user.getEmail() != null, "email", user.getEmail())
                .eq(user.getId() != null, "id", user.getId());

        return userMapperPro.selectList(queryWrapper);
    }

}