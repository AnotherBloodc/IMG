package com.example.SeetLeft.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SeetLeft.Controller.UserController;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Entity.UserStyle;
import com.example.SeetLeft.Mapper.Table.UserMapper;
import com.example.SeetLeft.Mapper.TableS.User_UserStyleMapper;
import com.example.SeetLeft.MapperPro.UserMapperPro;
import com.example.SeetLeft.Service.UserService;
import com.example.SeetLeft.Tool.ToolController;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapperPro, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Resource
    private UserMapper userMapper;

    @Resource
    private User_UserStyleMapper user_UserStyleMapper;

    @Resource
    @Lazy
    private ToolController T;

    /**
     * user表：新增信息
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    public int insert(User user) {
        return userMapper.insert(user);
    }

    /**
     * user表：根据ID删除信息
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public int delete(int Id) {
        return userMapper.delete(Id);
    }


    /**
     * user表：修改用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public int update(User user) {
        return userMapper.update(user);
    }

    /**
     * user表，查询用户列表
     *
     * @return 包含用户对象的列表
     */
    public List<User> select(User user) {
        return userMapper.select(user);
    }

    /**
     * 获取单用户
     *
     * @return 获取单用户
     */
    public User selectUser(User user) {
        return userMapper.selectUser(user);
    }

    /**
     * user_style表：新增信息
     *
     * @param us 操作的记录
     * @return 受影响的行数
     */
    public int insertUS(UserStyle us) {
        return userMapper.insertUS(us);
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
     * user表：查询用户信息MybatisPlus
     *
     * @param user 查询条件，如果为null则返回所有用户
     * @return 符合条件的用户列表
     */
    public List<User> list(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(T.isNull(user.getId()), "id", user.getId())
                .eq(T.isNull(user.getUsername()), "username", user.getUsername())
                .eq(T.isNull(user.getEmail()), "email", user.getEmail());
        return this.list(queryWrapper);
    }

    /**
     * 获取单用户MybatisPlus
     *
     * @return 获取单用户MybatisPlus
     */
    public User getOne(User user) {
        if (T.isNull(user, user.getUsername(), user.getPassword())) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq(T.isNull(user.getId()), "id", user.getId())
                    .eq("username", user.getUsername())
                    .eq("password", user.getPassword())
                    .eq(T.isNull(user.getEmail()), "email", user.getEmail());
            return this.getOne(queryWrapper);
        }
        logger.info("登录失败：用户不存在");
        return null;
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
        pageNum = (T.isNull(pageNum)) ? pageNum : 1;
        pageSize = (T.isNull(pageSize)) ? pageSize : 5;

        Page<User> page = new Page<>(pageNum, pageSize);
        return this.page(page, null);
    }
}
