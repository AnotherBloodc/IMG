package com.example.SeetLeft.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.TableS.User_UserStyleMapper;
import com.example.SeetLeft.MapperPro.LoginMapperPro;
import com.example.SeetLeft.Service.StyleService;
import jakarta.annotation.Resource;

import java.util.List;

public class StyleServiceImpl extends ServiceImpl<LoginMapperPro, User> implements StyleService {

    @Resource
    private User_UserStyleMapper user_UserStyleMapper;

    /**
     * user表：根据ID删除信息
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public boolean DeleteUser(int Id) {
        return this.removeById(Id);
    }

    /**
     * user表：修改用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public boolean update(User user) {
        return this.updateById(user);
    }

    /**
     * user,user_style表：多表条件查询
     *
     * @param user 查询条件
     * @return 包含查询信息的用户列表
     */
    public List<User> selectStyle(User user) {
        return user_UserStyleMapper.SelectUser_UserStyle(user);
    }


    /**
     * User表：查询用户信息MybatisPlus
     *
     * @param user 查询条件，如果为null则返回所有用户
     * @return 符合条件的用户列表
     */
    public List<User> list(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user != null) {
            queryWrapper.eq(user.getUsername() != null, "username", user.getUsername());
        }
        return this.list(queryWrapper);
    }

    /**
     * user表：分页查询用户信息MybatisPlus：最大页数，最小页数
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    public Page<User> selectPage(Integer pageNum, Integer pageSize) {
        System.out.println("访问:分页查询用户信息(selectPage)页码：" + pageNum + "，每页数量：" + pageSize);
        // 设置默认值，如果参数为null则使用默认值
        pageNum = pageNum != null ? pageNum : 1;
        pageSize = pageSize != null ? pageSize : 5;
        Page<User> page = new Page<>(pageNum, pageSize);
        return this.page(page, null);
    }
}
