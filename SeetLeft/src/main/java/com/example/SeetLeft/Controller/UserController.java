package com.example.SeetLeft.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.Impl.UserServiceImpl;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Return;
import com.example.SeetLeft.Tool.ToolController;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private UserServiceImpl userServiceImpl;

    @Resource
    private ToolController T;

    /**
     * 处理根路径的Get请求<br/>
     * 注解：@GetMapping: 用于设置传递过来的GET请求的访问路径
     * 注解：@RequestBody：用于将请求体中的JSON数据绑定到方法的参数上，required = false：允许请求体中的JSON数据可以为空
     *
     * @return 固定返回1表示请求处理成功
     */
    @GetMapping("/") //可用@RequestMapping(value = "/*", method = RequestMethod.GET)注解代替，效果一样
    public Return Default(@RequestParam(required = false) String name) {
        logger.info("访问了默认页面{}", name);
        return Return.ok();
    }

    /**
     * 注册用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/register")
    public Return register(@RequestBody User user) {
        logger.info("{}\n用户注册(post/register)：", "\n条件=" + user + "\n结果=");
        //判断注册条件是否合法
        if (T.isNull(user) && T.isNull(user.getUsername(), user.getPassword(), user.getEmail())) {

            //判断用户是否存在
            if (T.isNull(userServiceImpl.list(user))) {
                logger.info("注册失败:用户已存在");
                return Return.error();
            }

            //判断注册是否成功
            if (T.isNull(userServiceImpl.insert(user))) {
                logger.info("注册成功");
                return Return.ok();
            }

        }
        logger.info("注册失败：条件\n");
        return Return.error();
    }

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 登录操作的结果状态码
     */
    @GetMapping("/login")
    public Return login(@RequestBody User user, HttpServletRequest request) {
        logger.info("用户登录(/login){}", "\n条件：" + user + "\n结果：");

        if (T.isNull(user) && T.isNull(user.getUsername(), user.getPassword())) {
            //根据条件查询此用户
            User users = userServiceImpl.getOne(user);

            //判断查询出来的用户是否合法
            if (T.isNull(users)) {

                //根据Username生成token
                String token = JwtUtils.generateToken(users.getUsername());

                //保存token
                request.getSession().setAttribute("token", token);

                logger.info("登录成功");

                //存储操作记录
                T.usetStyle("访问：GetMapping.login,用户查询,插入记录:" + user);
                return Return.ok().data("token", token);
            }
            logger.info("登录失败：逻辑");
            return Return.error();
        }

        logger.info("登录失败：条件");
        return Return.error();

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
    public Return Delete(@PathVariable int id) {
        logger.info("用户删除：{}", "\n条件：" + id + "\n结果：");
        //判断删除条件ID是否合法
        if (T.isNull(id)) {
            if (T.isNull(userServiceImpl.delete(id))) {
                logger.info("删除成功");
                T.usetStyle("访问：DeleteMapping.Delete,根据ID删除记录,ID" + id);//存储操作记录
                return Return.ok();
            }
            logger.info("删除失败:逻辑");
            return Return.error();
        }
        logger.info("删除失败:条件\n");
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
    public Return Update(@RequestBody(required = false) User user) {
        logger.info("用户修改：{}", "\n条件" + user + "\n结果=");
        //非空验证
        if (T.isNull(user) && T.isNull(user.getUsername(), user.getPassword(), user.getEmail())) {
            if (T.isNull(userServiceImpl.update(user))) {
                logger.info("修改成功");
                T.usetStyle("访问：PutMapping.user,更新用户信息,更新记录:" + user);//存储操作记录
                return Return.ok();
            }
            logger.info("修改失败:逻辑");
            return Return.error();
        }
        logger.info("修改失败:条件");
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
    public Return select(@ModelAttribute User user) {
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
                    return Return.error();
                }
            } catch (IllegalAccessException e) {
                logger.error("反射访问字段时发生异常", e);
            }
        }
        T.usetStyle("访问：GetMapping.user,根据用户名获取用户列表,条件：" + user);//存储操作记录
        String token = JwtUtils.generateToken(user.getUsername());//通过用户名生成token
        Return r = Return.ok().data(user.getUsername(), token);//设置token的键值对
        List<User> list = userServiceImpl.select(user);//查询用户列表
        Claims claims = JwtUtils.getClaimsByToken(token);//解密token

        System.out.println("查询用户信息成功：" + list);
        System.out.println("----------------------------------");
        System.out.println("r:" + r);
        System.out.println("----------------------------------");
        System.out.println("r.getData().get(user.getUsername()):" + r.getData().get(user.getUsername()));
        System.out.println("----------------------------------");
        System.out.println("token2:" + claims);
        System.out.println("用户名:" + claims.getSubject());
        System.out.println("之前设置的过期时间:" + claims.getExpiration());
        return Return.ok();
    }

    /**
     * 查询多表用户信息 1对多（User→UserStyle） JSON
     *
     * @param user 查询条件（用@ModelAttribute绑定GET参数）
     * @return 用户列表（含关联的风格）
     */
    @PostMapping("/user_userStyle")
    public Return user_userStyle(@RequestBody User user) {
        System.out.print("-----------------user_userStyle-----------------");
        if (T.isNull(user)) {
            System.out.println("查询多表用户信息条件为空");
            return Return.error();
        }
        T.usetStyle("访问：userStyle,一对多查询,条件：" + user);//存储操作记录
        System.out.println("访问:一对多查询(userStyle)，条件：" + user);
        List<User> list = userServiceImpl.selectStyle(user);
        System.out.println("查询多表用户信息查询成功：" + list);
        return Return.ok();
    }

    /**
     * 查询用户信息 PRO JSON
     *
     * @param user 要查询的用户名
     * @return 匹配的用户列表
     */
    @PostMapping("/pro")
    public Return pro(@RequestBody User user) {
        System.out.print("-----------------pro-----------------:" + user);
        if (T.isNull(user)) {
            System.out.println("查询用户信息PRO条件为空");
            return Return.error();
        }
        T.usetStyle("访问：pro,查询用户信息PRO,条件：" + user);//存储操作记录
        System.out.println("访问:查询用户信息PRO(pro),条件：" + user);
        List<User> userList = userServiceImpl.list(user);
        System.out.println("查询用户信息PRO查询成功：" + userList);
        return Return.ok();
    }

    /**
     * 查询用户信息 分页 JSON
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页查询结果
     */
    @GetMapping("/Page")
    public Return Page(@RequestParam(defaultValue = "1") Integer pageNum, Integer pageSize) {
        System.out.print("-----------------Page-----------------");
        if (T.isNull(pageNum + pageSize)) {
            System.out.println("传递过来的页码有问题");
            return Return.error();
        } else {
            System.out.println("访问:分页查询用户信息(Page)页码：" + pageNum + "，每页数量：" + pageSize);
            T.usetStyle("访问：Page,分页查询用户信息");//存储操作记录
            IPage<User> ipage = userServiceImpl.selectPage(pageNum, pageSize);
            System.out.println("分页查询用户信息成功：" + ipage);
            return Return.ok();
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
    public Return postFile(String name, MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.print("-----------------postFile-----------------");
        if (T.isNull(file)) {
            System.out.println("上传文件file为空");
            return Return.error();
        } else {
            T.usetStyle("访问：postFile,上传文件");//存储操作记录
            System.out.println("访问:文件上传(postFile)");
            String s = T.saveFile(name, file, request);//保存文件
            System.out.println("上传文件文件保存成功：" + s);
            return Return.ok();
        }
    }
}