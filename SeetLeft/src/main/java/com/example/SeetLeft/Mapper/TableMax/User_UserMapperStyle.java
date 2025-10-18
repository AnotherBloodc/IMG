package com.example.SeetLeft.Mapper.TableMax;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Entity.UserStyle;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 必须添加 @Mapper 注解，否则MyBatis无法扫描并生成代理对象
@Mapper
public interface User_UserMapperStyle extends BaseMapper<User> {

    /**
     * 根据用户ID查询该用户的所有风格（供用户一对多关联）
     *
     * @param Userid 用户ID
     * @return 风格列表
     */
    @Select("select * from `Seetpro`.`User_Style` where `Userid` = #{userid}")
    List<UserStyle> selectById(@Param("Userid") int Userid); // 加 @Param 确保参数绑定


    /**
     * 根据用户ID查询单个用户（供风格多对一关联）
     *
     * @param Id 用户ID
     * @return 单个用户
     */
    @Select("select * from `Seetpro`.`User` where `Id` = #{id}")
    User selectUserById(@Param("Id") int Id); // 加 @Param 确保参数绑定


    /**
     * 查询所有风格，关联所属用户（多对一）
     *
     * @return 风格列表（含关联用户）
     */
    @Select("select * from `Seetpro`.`User_Style`")
    @Results({
            @Result(column = "Id", property = "id"),
            @Result(column = "Datetime", property = "datetime"),
            @Result(column = "Name", property = "name"),
            @Result(column = "Userid", property = "user", javaType = User.class,
                    // 修正：关联当前接口中的 selectUserById 方法（路径正确）
                    one = @One(select = "com.example.SeetLeft.Mapper.TableMax.User_UserMapperStyle.selectUserById"))
    })
    List<UserStyle> selectUserStyle(User user); // 移除无用参数 User user（SQL中未使用）


    /**
     * 查询所有用户，关联其所有风格（一对多）
     *
     * @return 用户列表（含关联风格）
     */
    @Select("select * from `Seetpro`.`User`")
    @Results({
            @Result(column = "Id", property = "id"),
            @Result(column = "Username", property = "username"),
            @Result(column = "Password", property = "password"),
            @Result(column = "Email", property = "email"),
            @Result(column = "Id", property = "userStyle", javaType = List.class,
                    many = @Many(select = "com.example.SeetLeft.Mapper.TableMax.User_UserMapperStyle.selectById"))
    })
    List<User> selectUser(User user);

}