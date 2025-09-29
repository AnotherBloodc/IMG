package com.example.SeetLeft.Mapper;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Entity.UserStyle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 插入数据并返回受影响的行数
     *
     * @return 插入操作影响的行数
     */
    int insert(User user);

    /**
     * 执行删除操作
     *
     * @return 删除操作影响的行数
     */
    int delete(int Id);

    /**
     * 更新操作
     *
     * @return 更新操作影响的行数
     */
    int update(User user);

    /**
     * 获取用户列表
     *
     * @return 包含用户对象的列表
     */
    List<User> server(User user);


    /**
     * 新增操作记录
     *
     * @return 包含用户对象的列表
     */
    int insertUS(UserStyle us);
}
