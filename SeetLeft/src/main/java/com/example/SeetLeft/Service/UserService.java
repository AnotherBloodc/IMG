package com.example.SeetLeft.Service;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Entity.UserStyle;
import com.example.SeetLeft.Mapper.UserMapper;
import com.example.SeetLeft.Mapper.Impl.UserMapperImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserMapper {

    @Resource
    private UserMapperImpl userMapperImpl;

    /**
     * 插入用户信息到数据库
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    public int insert(User user) {
        return userMapperImpl.insert(user);
    }

    /**
     * 根据ID删除记录
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public int delete(int Id) {
        return userMapperImpl.delete(Id);
    }


    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public int update(User user) {
        return userMapperImpl.update(user);
    }

    /**
     * 获取用户列表
     *
     * @return 包含用户对象的列表
     */
    public List<User> server(User user) {
        return userMapperImpl.server(user);
    }

    /**
     * 新增操作记录
     *
     * @param us 操作的记录
     * @return 受影响的行数
     */
    public int insertUS(UserStyle us) {
        return userMapperImpl.insertUS(us);
    }
}
