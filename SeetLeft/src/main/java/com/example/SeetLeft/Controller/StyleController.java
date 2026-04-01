package com.example.SeetLeft.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Service.Impl.StyleServiceImpl;
import com.example.SeetLeft.Tool.ControllerUtils;
import com.example.SeetLeft.Tool.Return;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/SeetStyle")
@Slf4j
@Tag(name = "管理员操作", description = "对系统的用户进行增删改查")
public class StyleController {

    @Resource
    private StyleServiceImpl StyleService;
    /**
     * 删除用户信息：ID <br/>
     * 注解：@PathVariable：用于将请求体中的变量值绑定到方法的参数上
     * 注解：@DeleteMapping: 用于设置传递过来的Delete请求的访问路径
     *
     * @param id 要删除的记录ID
     * @return 删除操作影响的行数
     */
    @DeleteMapping("/{id}")//或者是@RequestMapping(value = "/Delete", method = RequestMethod.DELETE)注解，效果一样
    @Operation(summary = "删除用户", description = "提供ID删除用户")
    public Return<Boolean> Delete(@PathVariable int id) {
        log.info(LoginEnums.GO + "你在删除用户，你提供的ID：{}", id);
        //判断删除条件
        if (id < 0) {
            return Return.error("没有提供删除条件");
        }
        //执行删除操作
        boolean list = StyleService.DeleteUser(id);
        Return<Boolean> R = ControllerUtils.messageHandler(() -> list);
        //判断是否成功
        if (!list) {
            return Return.error();
        }
        return R;

    }
    /**
     * 更新用户信息：JSON
     * 注解：@PutMapping: 用于设置传递过来的Put请求的访问路径
     *
     * @param user 要更新的用户对象，可以为null
     * @return 更新操作影响的行数
     */
    @PutMapping//或者是@RequestMapping(value = "/Update", method = RequestMethod.PUT)注解，效果一样
    @Operation(summary = "更新用户", description = "根据提供的User的id更新用户信息")
    public Return<Boolean> Update(@RequestBody(required = false) User user) {
        log.info(LoginEnums.GO + "更新用户，新数据：{}", user);
        //判断更新条件
        if (user == null) {
            return Return.error("没有提供用户信息");
        }
        //执行更新操作
        boolean list = StyleService.update(user);
        Return<Boolean> R = ControllerUtils.messageHandler(() -> list);
        //判断是否成功
        if (!list) {
            return Return.error("数据无变动");
        }
        return R;
    }

    /**
     * 查询用户信息 JSON<br/>
     * 形参注解配置：@ModelAttribute：如果给此请求传递空参数，则为此参数设置全属性为空的对象
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @PostMapping//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    @Operation(summary = "查询用户", description = "提供User对象查询用户")
    public Return<List<User>> select(User user) {
        log.info(LoginEnums.GO + "查询用户，条件：{}", user);
        //判断查询条件
        if (user == null) {
            return Return.error("没有提供用户信息");
        }
        //执行查询操作
        return ControllerUtils.messageHandler(() -> StyleService.list(user));
    }

    /**
     * 查询用户信息 JSON<br/>
     * 形参注解配置：@ModelAttribute：如果给此请求传递空参数，则为此参数设置全属性为空的对象
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @PostMapping("/serverPRO")//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    @Operation(summary = "查询用户", description = "提供User对象查询用户")
    public Return<List<User>> selectPRO(User user) {
        log.info(LoginEnums.GO + "查询用户PRO，条件：{}", user);
        //判断查询条件
        if (user == null) {
            return Return.error("没有提供用户信息");
        }
        //执行查询操作
        return ControllerUtils.messageHandler(() -> StyleService.list(user));
    }


    /**
     * 查询多表用户信息 1对多（User→UserStyle） JSON
     * 形参注解配置：@ModelAttribute：如果给此请求传递空参数，则为此参数设置全属性为空的对象
     *
     * @param user 查询条件
     * @return 用户列表（含关联的风格）
     */
    @PostMapping("/user_userStyle")
    public Return<List<User>> user_userStyle(@RequestBody User user) {
        log.info(LoginEnums.GO + "执行多表一对多查询");
        //判断查询条件
        if (user == null) {
            return Return.error("没有提供用户信息");
        }
        //执行查询操作
        return ControllerUtils.messageHandler(() -> StyleService.selectStyle(user));
    }

    /**
     * 查询用户信息 分页 JSON
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    @GetMapping("/Page")
    public Return<IPage<User>> Page(@RequestParam(defaultValue = "1") Integer pageNum, Integer pageSize) {
        log.info(LoginEnums.GO + "执行查询用户分页查询");
        //判断查询条件
        if (pageNum + pageSize <= 2) {
            return Return.error();
        }
        //执行查询操作
        return ControllerUtils.messageHandler(() -> StyleService.selectPage(pageNum, pageSize));
    }
}