package com.example.SeetLeft.Mapper.Impl;

import com.example.SeetLeft.Entity.User;

import com.example.SeetLeft.Entity.UserStyle;
import com.example.SeetLeft.Mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMapperImpl implements UserMapper {

    @Resource
    private UserMapper userMapper;

    /**
     * 插入用户信息到数据库
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    /**
     * 根据ID删除记录
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    @Override
    public int delete(int Id) {
        return userMapper.delete(Id);
    }


    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    /**
     * 获取用户列表
     *
     * @return 包含用户对象的列表
     */
    @Override
    public List<User> server(User user) {
        return userMapper.server(user);
    }

    /**
     * 新增操作记录
     *
     * @param us 操作的记录
     * @return 受影响的行数
     */
    @Override
    public int insertUS(UserStyle us) {
        return userMapper.insertUS(us);
    }

}
