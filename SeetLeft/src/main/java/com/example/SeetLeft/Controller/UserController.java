package com.example.SeetLeft.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.UserService;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Result;
import com.example.SeetLeft.Tool.ToolController;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.reflect.Field;
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
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private ToolController toolController;

    /**
     * 处理根路径的Get请求<br/>
     * 注解：@GetMapping: 用于设置传递过来的GET请求的访问路径
     * 注解：@RequestBody：用于将请求体中的JSON数据绑定到方法的参数上，required = false：允许请求体中的JSON数据可以为空
     *
     * @return 固定返回1表示请求处理成功
     */
    @GetMapping("/") //可用@RequestMapping(value = "/*", method = RequestMethod.GET)注解代替，效果一样
    public Result Default(@RequestParam(required = false) String name) {
        System.out.println("你访问了默认页面" + name);
        return Result.ok();
    }

    /**
     * 新增用户信息：JSON<br/>
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping//或者是@RequestMapping(value = "/Insert", method = RequestMethod.POST)注解，效果一样
    public Result Insert(@RequestBody(required = false) User user) {
        System.out.print("-----------------insert-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("插入用户信息User对象为空");
            return Result.error();
        } else {
            toolController.usetStyle("访问：PostMapping.Insert,插入用户信息,插入记录:" + user);//存储操作记录
            int i = userService.insert(user);
            System.out.println("新增用户信息成功：" + i);
            return Result.ok();
        }
    }

    /**
     * 删除用户信息：ID <br/>
     * 注解：@PathVariable：用于将请求体中的变量值绑定到方法的参数上
     * 注解：@DeleteMapping: 用于设置传递过来的Delete请求的访问路径
     *
     * @param id 要删除的记录ID
     * @return 删除操作影响的行数
     */
    @DeleteMapping("/{id}")//或者是@RequestMapping(value = "/Delete", method = RequestMethod.DELETE)注解，效果一样
    public Result Delete(@PathVariable int id) {
        System.out.print("-----------------Delete-----------------" + id);
        //判断ID是否合法
        if (id < 1) {
            System.out.println("删除用户信息的ID为空");
            return Result.error();
        } else {
            toolController.usetStyle("访问：DeleteMapping.Delete,根据ID删除记录,ID" + id);//存储操作记录
            int i = userService.delete(id);
            System.out.println("删除用户信息成功：" + i);
            return Result.ok();
        }
    }

    /**
     * 修改用户信息：JSON
     * 注解：@PutMapping: 用于设置传递过来的Put请求的访问路径
     *
     * @param user 要更新的用户对象，可以为null
     * @return 更新操作影响的行数
     */
    @PutMapping//或者是@RequestMapping(value = "/Update", method = RequestMethod.PUT)注解，效果一样
    public Result Update(@RequestBody(required = false) User user) {
        System.out.print("-----------------Update-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("更新用户信息User对象为空");
            return Result.error();
        } else {
            toolController.usetStyle("访问：PutMapping.user,更新用户信息,更新记录:" + user);//存储操作记录
            int i = userService.update(user);
            System.out.println("修改用户信息成功：" + i);
            return Result.ok();
        }
    }

    /**
     * 查询用户信息 JSON<br/>
     * 注解@ModelAttribute：获取请求路径中的对象类型参数，如果对象为null，这创建所有属性为空的对象
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    public Result select(@ModelAttribute User user) {
        System.out.println("-----------------Server-----------------" + user);
        //非空验证
        Class<?> clazz = user.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(user);
                System.out.println(fieldName + ":" + fieldValue);
                if (fieldName.equals("username") && fieldValue == null) {
                    return Result.error();
                }
            } catch (IllegalAccessException e) {
                logger.error("反射访问字段时发生异常", e);
            }
        }
        toolController.usetStyle("访问：GetMapping.user,根据用户名获取用户列表,条件：" + user);//存储操作记录
        SecretKey token = JwtUtils.generateToken(user.getUsername());//获取token
        Result r = Result.ok().data(user.getUsername(), token);//设置token的键值对
        List<User> list = userService.select(user);//查询用户列表
        System.out.println("查询用户信息成功：" + list);
        System.out.println("r:" + r);

        Claims token2 = JwtUtils.getClaimsByToken(r.getData().get(token), user.getUsername());//获取token
        System.out.println("token2:" + token2);
        return Result.ok();
    }


    /**
     * 查询多表用户信息 1对多（User→UserStyle） JSON
     *
     * @param user 查询条件（用@ModelAttribute绑定GET参数）
     * @return 用户列表（含关联的风格）
     */
    @GetMapping("/userStyle")
    public Result selectStyle(@ModelAttribute User user) {
        System.out.print("-----------------selectStyle-----------------");
        if (user.getId() == null) {
            System.out.println("查询多表用户信息条件为空:" + user);
            return Result.error();
        } else {
            toolController.usetStyle("访问：selectStyle,一对多查询,条件：" + user);//存储操作记录
            System.out.println("访问:一对多查询(selectStyle)，条件：" + user);
            List<User> list = userService.selectStyle(user);
            System.out.println("查询多表用户信息查询成功：" + list);
            return Result.ok();
        }
    }

    /**
     * 查询用户信息 PRO JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping("/pro")
    public Result selectPro(@ModelAttribute User user) {
        System.out.print("-----------------selectPro-----------------");
        if (user.getId() == null) {
            System.out.println("查询用户信息PRO条件为空：" + user);
            return Result.error();
        } else {
            toolController.usetStyle("访问：selectPro,查询用户信息PRO,条件：" + user);//存储操作记录
            System.out.println("访问:查询用户信息PRO(selectPro),条件：" + user);
            List<User> list = userService.selectPro(user);
            System.out.println("查询用户信息PRO查询成功：" + list);
            return Result.ok();

        }
    }

    /**
     * 查询用户信息 分页 JSON
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    @PostMapping("/Page")
    public Result selectPage(Integer pageNum, Integer pageSize) {
        System.out.print("-----------------selectPage-----------------");
        if ((pageNum + pageSize) < 1) {
            System.out.println("传递过来的页码有问题");
            return Result.error();
        } else {
            System.out.println("访问:分页查询用户信息(selectPage)页码：" + pageNum + "，每页数量：" + pageSize);
            toolController.usetStyle("访问：selectPage,分页查询用户信息");//存储操作记录
            IPage<User> ipage = userService.selectPage(pageNum, pageSize);
            System.out.println("分页查询用户信息成功：" + ipage);
            return Result.ok();
        }
    }

    /**
     * 上传文件：文件和名字<br/>
     * 注解：@PostMapping: 用于设置传递过来的Post请求的访问路径
     *
     * @param name 文件名
     * @param file 文件
     * @return postFile
     */
    @PostMapping("/postFile")
    public Result postFile(String name, MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.print("-----------------postFile-----------------");
        if (file == null) {
            System.out.println("上传文件file为空");
            return Result.error();
        } else {
            toolController.usetStyle("访问：postFile,上传文件");//存储操作记录
            System.out.println("访问:文件上传(postFile)");
            String s = toolController.saveFile(name, file, request);//保存文件
            System.out.println("上传文件文件保存成功：" + s);
            return Result.ok();
        }
    }
}