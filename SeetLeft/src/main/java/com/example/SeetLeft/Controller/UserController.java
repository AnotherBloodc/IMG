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
import java.io.InputStream;
import java.util.List;

//RestController：用于将返回值直接作为HTTP响应体返回给客户端
//Controller：用于将返回值作为视图名称，然后由视图解析器解析为实际的视图对象

/**
 * Spring Boot 默认的视图解析器是 Thymeleaf 或 InternalResourceViewResolver （用于 JSP）如下<br/>
 * (1) Thymeleaf（推荐）<br/>
 * 默认模板目录： src/main/resources/static/<br/>
 * (2) JSP（传统方式）<br/>
 * 默认模板目录： src/main/webapp/WEB-INF/views/
 */
@CrossOrigin
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ToolController toolController;

    /**
     * 处理根路径的Get请求
     * 注解：GetMapping: 用于处理前端传递过来的GET请求
     * 注解：@RequestBody：用于将请求体中的JSON数据绑定到方法的参数上，required = false：允许请求体中的JSON数据可以为空
     *
     * @return 固定返回1表示请求处理成功
     */
    @GetMapping("/") //或者是@RequestMapping(value = "/*", method = RequestMethod.GET)注解，效果一样
    public String Default(@RequestBody(required = false) String name) {
        System.out.println("你访问了默认页面" + name);
        return "罗启略大王";
    }

    /**
     * 新增用户信息：JSON<br/>
     * 注解：@RequestBody：用于将请求体中的JSON数据绑定到方法的参数上，required = false：允许请求体中的JSON数据可以为空
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/user")//或者是@RequestMapping(value = "/Insert", method = RequestMethod.POST)注解，效果一样
    public Integer Insert(@RequestBody(required = false) User user, InputStream inputStream) {
        System.out.print("-----------------insert-----------------" + user);
        toolController.usetStyle("访问：PostMapping.Insert,插入用户信息,插入记录:" + user);//存储操作记录
        //非空验证
        if (user == null) {
            System.out.println("插入用户信息User对象为空");
            return 0;
        }
        return userService.insert(user);
    }

    /**
     * 删除用户信息：ID <br/>
     * 注解：@PathVariable：用于将请求体中的变量值绑定到方法的参数上
     *
     * @param id 要删除的记录ID
     * @return 删除操作影响的行数
     */
    @DeleteMapping("/user/{id}")//或者是@RequestMapping(value = "/Delete", method = RequestMethod.DELETE)注解，效果一样
    public int Delete(@PathVariable int id) {
        System.out.print("-----------------Delete-----------------" + id);
        if (id < 1) {
            System.out.println("删除用户信息id为空");
            return 0;
        }
        toolController.usetStyle("访问：DeleteMapping.Delete,根据ID删除记录,ID" + id);//存储操作记录
        return userService.delete(id);
    }

    /**
     * 修改用户信息：JSON
     *
     * @param user 要更新的用户对象，可以为null
     * @return 更新操作影响的行数
     */
    @PutMapping("/user")//或者是@RequestMapping(value = "/Update", method = RequestMethod.PUT)注解，效果一样
    public int Update(@RequestBody(required = false) User user) {
        System.out.print("-----------------Update-----------------" + user);
        if (user == null) {
            System.out.println("更新用户信息User对象为空");
            return 0;
        }
        toolController.usetStyle("访问：PutMappping.user,更新用户信息,更新记录:" + user);//存储操作记录
        return userService.update(user);
    }

    /**
     * 查询用户信息：JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping("/user")//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    public List<User> select(@RequestBody(required = false) User user) {
        System.out.print("-----------------Server-----------------" + user);
        if (user == null) {
            System.out.println("用户信息user对象为空");
            return null;
        }
        String token = JwtUtils.generateToken(user.getUsername());
        Result.ok().data("token", token);
        System.out.println("token" + token);
        toolController.usetStyle("访问：GetMapping.user,根据用户名获取用户列表,条件：" + user);//存储操作记录
        return userService.select(user);
    }

    /**
     * 上传文件：文件和名字
     *
     * @param name 文件名
     * @param file 文件
     * @return postFile
     */
    @PostMapping("/user/postFile")
    public String postFile(String name, MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.print("-----------------postFile-----------------");
        if (file == null) {
            System.out.println("上传文件file为空");
            return "上传文件不存在";
        }
        System.out.println("访问:文件上传(postFile)");
        toolController.usetStyle("访问：postFile,上传文件");//存储操作记录
        return toolController.saveFile(name, file, request);//保存文件
    }

    /**
     * 查询多表用户信息（一对多：User→UserStyle）：JSON<br/>
     * 注解@ModelAttribute：用于将请求参数绑定到方法的参数上，required = false：允许请求参数可以为空
     *
     * @param user 查询条件（用@ModelAttribute绑定GET参数）
     * @return 用户列表（含关联的风格）
     */
    @GetMapping("/user/userStyle")
    public List<User> selectStyle(@RequestBody(required = false) User user) {
        System.out.print("-----------------selectStyle-----------------");
        if (user == null) {
            System.out.println("查询用户信息user对象为空");
            return null;
        }
        System.out.println("访问:一对多查询(selectStyle)，条件：" + user);
        toolController.usetStyle("访问：selectStyle,一对多查询,条件：" + user);//存储操作记录
        return userService.selectStyle(user);
    }

    /**
     * 查询用户信息PRO：JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @GetMapping("/user/pro")
    public List<User> selectPro(@RequestBody(required = false) User user) {
        System.out.print("-----------------selectPro-----------------");
        if (user == null) {
            System.out.println("selectPro.user为空：");
            return null;
        }
        System.out.println("访问:查询用户信息PRO(selectPro),条件：" + user);
        toolController.usetStyle("访问：selectPro,查询用户信息PRO,条件：" + user);//存储操作记录
        return userService.selectPro(user);
    }

    /**
     * 分页查询用户信息
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    @PostMapping("/user/selectPage")
    public IPage<User> selectPag(Integer pageNum, Integer pageSize) {
        System.out.print("-----------------selectPage-----------------");
        System.out.println("访问:分页查询用户信息(selectPag)页码：" + pageNum + "，每页数量：" + pageSize);
        toolController.usetStyle("访问：selectPage,分页查询用户信息");//存储操作记录
        return userService.selectPage(pageNum, pageSize);
    }
}