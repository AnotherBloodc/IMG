package com.example.SeetLeft.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.Impl.UserServiceImpl;
import com.example.SeetLeft.Tool.Return;
import com.example.SeetLeft.Tool.ToolController;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController：类，这个类下的所有方法返回值的返回形式设置成HTTP响应体形式返回
//@Controller：类，这个类下的所有方法返回值的返回形式设置成视图对象的形式返回
//@RequestMapping：类，方法，类上添加可设置类下所有方法统一映射至这个路径，方法上添加可设置方法下所有参数统一映射至这个路径

/**
 * Spring Boot 默认的视图解析器是 Thymeleaf 或 InternalResourceViewResolver如下<br/>
 * (1) Thymeleaf（推荐）<br/>
 * 默认模板目录： src/main/resources/static/<br/>
 * (2) JSP（传统方式）（用于 JSP）<br/>
 * 默认模板目录： src/main/webapp/WEB-INF/views/
 */
@CrossOrigin
@RestController
@RequestMapping("/User")
@Slf4j
public class UserController {

    @Resource
    private UserServiceImpl UserService;

    @Resource
    private ToolController T;

    /**
     * 删除用户信息：ID <br/>
     * 注解：@PathVariable：用于将请求体中的变量值绑定到方法的参数上
     * 注解：@DeleteMapping: 用于设置传递过来的Delete请求的访问路径
     *
     * @param id 要删除的记录ID
     * @return 删除操作影响的行数
     */
    @DeleteMapping("/{id}")//或者是@RequestMapping(value = "/Delete", method = RequestMethod.DELETE)注解，效果一样
    public Return<Void> Delete(@PathVariable int id) {
        log.info("\n\n用户删除(Delete/){}", "\n条件：" + id + "\n结果：");

        //判断删除条件ID是否合法
        if (id > 0) {
            if (UserService.DeleteUser(id)) {
                log.info("删除成功");
                T.userStyle("访问：DeleteMapping.Delete,根据ID删除记录,ID" + id);//存储操作记录
                return Return.ok();
            }
            log.info("删除失败:逻辑");
            return Return.error();
        }
        log.info("删除失败:条件\n");
        return Return.error();
    }

    /**
     * 修改用户信息：JSON
     * 注解：@PutMapping: 用于设置传递过来的Put请求的访问路径
     *
     * @param user 要更新的用户对象，可以为null
     * @return 更新操作影响的行数
     */
    @PutMapping//或者是@RequestMapping(value = "/Update", method = RequestMethod.PUT)注解，效果一样
    public Return<Void> Update(@RequestBody(required = false) User user) {
        log.info("\n\n用户修改(put/){}", "\n条件" + user + "\n结果=");

        //非空验证
        if (user != null) {
            if (UserService.update(user) > 0) {
                log.info("修改成功");
                T.userStyle("访问：PutMapping.user,更新用户信息,更新记录:" + user);//存储操作记录
                return Return.ok();
            }
            log.info("修改失败:逻辑");
            return Return.error();
        }
        log.info("修改失败:条件");
        return Return.error();
    }

    /**
     * 查询用户信息 JSON<br/>
     * 注解@ModelAttribute：获取请求路径中的对象类型参数，如果对象为null，这创建所有属性为空的对象
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    public Return<List<User>> select(@ModelAttribute User user) {
        log.info("\n\n用户查询(get/){}", "\n条件" + user + "\n结果=");
        List<User> list = UserService.list(user);
        if (list != null) {
            log.info("查询成功");
            T.userStyle("访问：GetMapping.user,根据用户名获取用户列表,条件：" + user);//存储操作记录
            return Return.ok(list);
        }

        return Return.error();
    }


    /**
     * 查询多表用户信息 1对多（User→UserStyle） JSON
     *
     * @param user 查询条件（用@ModelAttribute绑定GET参数）
     * @return 用户列表（含关联的风格）
     */
    @PostMapping("/user_userStyle")
    public Return<Void> user_userStyle(@RequestBody User user) {
        log.info("\n\n查询多表用户信息(/user_userStyle){}", "\n条件：" + user + "\n结果：");

        if (user != null) {
            List<User> list = UserService.selectStyle(user);
            log.info("查询成功：{}", list);
            System.out.println(list);
            T.userStyle("访问：userStyle,一对多查询,条件：" + user);//存储操作记录
            return Return.ok();
        }
        log.info("查询失败：条件");
        return Return.error();

    }

    /**
     * 查询用户信息 PRO JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @PostMapping("/pro")
    public Return<Void> pro(@RequestBody User user) {
        log.info("\n\n查询用户信息 PRO(/pro){}", "\n条件：" + user + "\n结果：");
        if (user != null) {

            T.userStyle("访问：pro,查询用户信息PRO,条件：" + user);//存储操作记录
            List<User> userList = UserService.list(user);
            log.info("查询成功PRO：{}", userList);
            return Return.ok();

        }
        log.info("查询失败PRO：条件");
        return Return.error();
    }

    /**
     * 查询用户信息 分页 JSON
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    @GetMapping("/Page")
    public Return<Void> Page(@RequestParam(defaultValue = "1") Integer pageNum, Integer pageSize) {
        log.info("\n\n分页查询信息(/Page){}", "\n条件：pageNum:" + pageNum + "pageSize:" + pageSize + "\n结果：");
        if (pageNum + pageSize > 2) {
            T.userStyle("访问：Page,分页查询用户信息");//存储操作记录
            IPage<User> ipage = UserService.selectPage(pageNum, pageSize);
            log.info("分页查询成功{}", ipage);
            return Return.ok();
        }
        log.info("分页查询失败：条件");
        return Return.error();
    }

}