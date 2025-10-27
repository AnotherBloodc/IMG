package com.example.SeetLeft.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.UserService;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Result;
import com.example.SeetLeft.Tool.ToolController;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping("/user")//或者是@RequestMapping(value = "/Insert", method = RequestMethod.POST)注解，效果一样
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
    @DeleteMapping("/user/{id}")//或者是@RequestMapping(value = "/Delete", method = RequestMethod.DELETE)注解，效果一样
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
    @PutMapping("/user")//或者是@RequestMapping(value = "/Update", method = RequestMethod.PUT)注解，效果一样
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
     * 查询用户信息：JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping("/user")//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    public Result select(@RequestBody(required = false) User user) {
        System.out.print("-----------------Server-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("用户信息user对象为空");
            return Result.error();
        } else {
            toolController.usetStyle("访问：GetMapping.user,根据用户名获取用户列表,条件：" + user);//存储操作记录
            String token = JwtUtils.generateToken(user.getUsername());//获取token
            Result.ok().data("token", token);//设置token
            System.out.println("token:" + token);
            List<User> list = userService.select(user);//查询用户列表
            System.out.println("查询用户信息成功：" + list);
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
    @PostMapping("/user/postFile")
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

    /**
     * 查询多表用户信息（一对多：User→UserStyle）：JSON
     *
     * @param user 查询条件（用@ModelAttribute绑定GET参数）
     * @return 用户列表（含关联的风格）
     */
    @GetMapping("/user/userStyle")
    public Result selectStyle(@RequestBody(required = false) User user) {
        System.out.print("-----------------selectStyle-----------------");
        if (user == null) {
            System.out.println("查询用户信息user对象为空");
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
     * 查询用户信息PRO：JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping("/user/pro")
    public Result selectPro(@RequestBody(required = false) User user) {
        System.out.print("-----------------selectPro-----------------");
        if (user == null) {
            System.out.println("selectPro.user为空：");
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
     * 分页查询用户信息
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    @PostMapping("/user/selectPage")
    public Result selectPag(Integer pageNum, Integer pageSize) {
        System.out.print("-----------------selectPage-----------------");
        if (pageNum == null || pageSize == null || pageNum + pageSize > 1) {
            System.out.println("传递过来的页码有问题");
            return Result.error();
        } else {
            System.out.println("访问:分页查询用户信息(selectPag)页码：" + pageNum + "，每页数量：" + pageSize);
            toolController.usetStyle("访问：selectPage,分页查询用户信息");//存储操作记录
            IPage<User> ipage = userService.selectPage(pageNum, pageSize);
            System.out.println("分页查询用户信息成功：" + ipage);
            return Result.ok();
        }
    }
}