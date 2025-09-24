package com.example.SeetLeft.Service;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.SeetLeftMapper;
import com.example.SeetLeft.Mapper.SeetLeftMapperImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeetLeftService implements SeetLeftMapper {

    @Resource
    private SeetLeftMapperImpl seetLeftMapperImpl;

    /**
     * 插入用户信息到数据库
     *
     * @param user 要插入的用户对象
     * @return 受影响的行数
     */
    public int insert(User user) {
        return seetLeftMapperImpl.insert(user);
    }

    /**
     * 根据ID删除记录
     *
     * @param Id 要删除的记录ID
     * @return 受影响的行数
     */
    public int delete(int Id) {
        return seetLeftMapperImpl.delete(Id);
    }


    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 受影响的行数
     */
    public int update(User user) {
        return seetLeftMapperImpl.update(user);
    }

    /**
     * 获取用户列表
     *
     * @return 包含用户对象的列表
     */
    public List<User> server(User user) {
        return seetLeftMapperImpl.server(user);
    }

}
