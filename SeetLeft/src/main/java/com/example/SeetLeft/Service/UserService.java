package com.example.SeetLeft.Service;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Entity.UserStyle;
import com.example.SeetLeft.Mapper.Table.Impl.UserMapperImpl;
import com.example.SeetLeft.Mapper.TableMax.Impl.User_UserStyleImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapperImpl userMapperImpl;

    @Resource
    private User_UserStyleImpl userStyleImpl;

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
    public List<User> select(User user) {
        return userMapperImpl.select(user);
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

    /**
     * 多表条件查询
     *
     * @param user 查询条件
     * @return 包含查询信息的用户列表
     */
    public List<User> selectStyle(User user) {
        return userStyleImpl.selectStyle(user);
    }

}
