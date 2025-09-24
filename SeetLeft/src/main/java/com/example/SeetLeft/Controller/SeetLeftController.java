package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.SeetLeftService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SeetLeftController {

    @Resource
    private SeetLeftService seetLeftService;


    /**
     * 处理根路径的POST请求
     *
     * @return 固定返回1表示请求处理成功
     */
    @GetMapping("/")
    @ResponseBody
    public int Default() {
        System.out.println("1");
        return 1;
    }

    /**
     * 插入用户信息
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/Insert")
    @ResponseBody
    public Integer Insert(@RequestBody(required = false) User user) {
        System.out.printf("-----------------Controller-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("插入用户信息User对象为空");
            return 0;
        }
        return seetLeftService.insert(user);
    }

    /**
     * 根据ID删除记录
     *
     * @param id 要删除的记录ID
     * @return 删除操作影响的行数
     */
    @DeleteMapping("/Delete")
    @ResponseBody
    public int Delete(@RequestBody(required = false) int id) {
        System.out.printf("-----------------Delete-----------------" + id);
        //非空验证
        if (id == 0) {
            System.out.println("删除用户信息id为空");
            return 0;
        }
        return seetLeftService.delete(id);
    }

    /**
     * 更新用户信息
     *
     * @param user 要更新的用户对象，可以为null
     * @return 更新操作影响的行数
     */
    @PutMapping("/Update")
    @ResponseBody
    public int Update(@RequestBody(required = false) User user) {
        System.out.printf("-----------------Update-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("更新用户信息User对象为空");
            return 0;
        }
        return seetLeftService.update(user);
    }

    /**
     * 根据用户名获取用户列表
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping("/Server")
    @ResponseBody
    public List<User> Server(@RequestBody(required = false) User user) {
        System.out.printf("-----------------Server-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("查询用户信息user对象为空");
            return null;
        }
        return seetLeftService.server(user);
    }

}
