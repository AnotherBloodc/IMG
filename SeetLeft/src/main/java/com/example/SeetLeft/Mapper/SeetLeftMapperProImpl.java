package com.example.SeetLeft.Mapper;

import com.example.SeetLeft.Entity.User;

import java.util.Collections;
import java.util.List;

public class SeetLeftMapperProImpl {

    private SeetLeftMapperPro seetLeftMapperPro;

    /**
     * 插入用户信息到数据库
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    public int insert(User user) {
        return seetLeftMapperPro.insert(user);
    }

    /**
     * 根据ID删除记录
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public int delete(int Id) {
        return seetLeftMapperPro.deleteById(Id);
    }


    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public int update(User user) {
        return seetLeftMapperPro.updateById(user);
    }

    /**
     * 获取用户列表
     *
     * @return 包含用户对象的列表
     */
    public List<User> server(int user) {
        return Collections.singletonList(seetLeftMapperPro.selectById(user));
    }

}