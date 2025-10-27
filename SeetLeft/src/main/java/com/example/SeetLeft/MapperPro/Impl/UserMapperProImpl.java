package com.example.SeetLeft.MapperPro.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 新增用户:JSON
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    public int insert(User user) {
        return userMapperPro.insert(user);
    }

    /**
     * 删除用户：ID
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public int delete(int Id) {
        return userMapperPro.deleteById(Id);
    }


    /**
     * 修改用户信息:JSON
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public int update(User user) {
        return userMapperPro.updateById(user);
    }

    /**
     * 查询用户信息：ID
     *
     * @return 包含用户对象的列表
     */
    public List<User> select(int user) {
        return Collections.singletonList(userMapperPro.selectById(user));
    }

    /**
     * 查询用户信息pro
     *
     * @param user 查询条件，如果为null则返回所有用户
     * @return 符合条件的用户列表
     */
    public List<User> selectPro(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(user.getUsername() != null, "username", user.getUsername())
                .eq(user.getEmail() != null, "email", user.getEmail())
                .eq(user.getId() != null, "id", user.getId());

        return userMapperPro.selectList(queryWrapper);
    }

    /**
     * 分页查询用户信息：最大页数，最小页数
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    public Page<User> selectPage(Integer pageNum, Integer pageSize) {
        System.out.println("访问:分页查询用户信息(selectPage)页码：" + pageNum + "，每页数量：" + pageSize);
        // 设置默认值，如果参数为null则使用默认值
        pageNum = (pageNum != null && pageNum > 0) ? pageNum : 1;
        pageSize = (pageSize != null && pageSize > 0) ? pageSize : 5;

        Page<User> page = new Page<>(pageNum, pageSize);
        return userMapperPro.selectPage(page, null);
    }

}