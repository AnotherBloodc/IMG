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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
@Slf4j
public class UserController {

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
        log.info("访问了默认页面{}", name);
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
        log.info("\n\n用户注册(post/register)：{}", "\n条件=" + user + "\n结果=");
        //判断注册条件是否合法
        if (T.isNull(user) && T.isNull(user.getUsername(), user.getPassword(), user.getEmail())) {

            //判断用户是否存在
            if (T.isNull(userServiceImpl.list(user))) {
                log.info("注册失败:用户已存在");
                return Return.error();
            }

            //判断注册是否成功
            if (T.isNull(userServiceImpl.insert(user))) {
                log.info("注册成功");
                return Return.ok();
            }

        }
        log.info("注册失败：条件\n");
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
        log.info("\n\n用户删除(Delete/){}", "\n条件：" + id + "\n结果：");
        //判断删除条件ID是否合法
        if (T.isNull(id)) {
            if (T.isNull(userServiceImpl.delete(id))) {
                log.info("删除成功");
                T.usetStyle("访问：DeleteMapping.Delete,根据ID删除记录,ID" + id);//存储操作记录
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
    public Return Update(@RequestBody(required = false) User user) {
        log.info("\n\n用户修改(put/){}", "\n条件" + user + "\n结果=");
        //非空验证
        if (T.isNull(user) && T.isNull(user.getUsername(), user.getPassword(), user.getEmail())) {
            if (T.isNull(userServiceImpl.update(user))) {
                log.info("修改成功");
                T.usetStyle("访问：PutMapping.user,更新用户信息,更新记录:" + user);//存储操作记录
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
    @PostMapping//或者是@RequestMapping(value = "/Server", method = RequestMethod.GET)注解，效果一样
    public Return select(@RequestBody(required = false) User user) {
        log.info("\n\n用户查询(post/){}", "\n条件" + user + "\n结果=");
        if (T.isNull(user)) {
            List<User> list = userServiceImpl.list(user);
            if (T.isNull(list)) {
                log.info("查询成功");
                T.usetStyle("访问：GetMapping.user,根据用户名获取用户列表,条件：" + user);//存储操作记录
                return Return.ok().data("list", list);
            }
            log.info("查询成功:无符合条件数据");
            return Return.ok().data("list", list);
        }
        log.info("查询失败:条件");
        return Return.error();
    }

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 登录操作的结果状态码
     */
    @PostMapping("/login")
    public Return login(@RequestBody User user) {
        log.info("\n\n用户登录(/login){}", "\n条件：" + user + "\n结果：");

        if (T.isNull(user) && T.isNull(user.getUsername(), user.getPassword())) {
            //根据条件查询此用户
            User users = userServiceImpl.getOne(user);

            //判断查询出来的用户是否合法
            if (T.isNull(users)) {

                //生成 token
                String token = JwtUtils.generateToken(users.getUsername());
                Return r = Return.ok().data("token", token).data("username", user.getUsername());//设置token的键值对

                log.info("根据Username生成token成功:{}", token);
                log.info("设置token键值对:{}", r);
                log.info("登录成功");

                //存储操作记录
                T.usetStyle("访问：GetMapping.login,用户查询,插入记录:" + user);
                return r;
            }
            log.info("查询失败：数据为空");
            return Return.error();
        }

        log.info("登录失败：条件");
        return Return.error();
    }

    /**
     * 获取token信息：JSON<br/>
     * 注解：@GetMapping: 用于设置传递过来的Get请求的访问路径
     *
     * @param request 用户token
     * @return 登录操作的结果状态码
     */
    @GetMapping("/Tokens")
    public Return Tokens(HttpServletRequest request) {
        String token = request.getHeader("token");
        log.info("\n\n验证token(get/token){}", "\n条件：" + token + "\n结果：");
        if (T.isNull(token)) {
            Claims claims = JwtUtils.getClaimsByToken(token);//解析token
            //非空验证
            if (T.isNull(claims)) {
                //noinspection ConstantConditions
                if (!claims.getExpiration().before(new Date())) {
                    log.info("解析成功：Token有效");
                    log.info("解析token值:{}", claims);
                    log.info("解析token过期时间:{}", claims.getExpiration());
                    User user = userServiceImpl.getTokenOne(claims);
                    if (T.isNull(user, user.getId())) {
                        return Return.ok().data("user", user);
                    }
                    log.info("查询失败：数据不存在");
                }
                log.info("解析失败：Token过期");
                return Return.error();
            }
            log.info("解析失败：Token不正确");
            return Return.error();

        }
        log.info("解析失败：条件");
        return Return.error();
    }

    /**
     * 查询多表用户信息 1对多（User→UserStyle） JSON
     *
     * @param user 查询条件（用@ModelAttribute绑定GET参数）
     * @return 用户列表（含关联的风格）
     */
    @PostMapping("/user_userStyle")
    public Return user_userStyle(@RequestBody User user) {
        log.info("\n\n查询多表用户信息(/user_userStyle){}", "\n条件：" + user + "\n结果：");

        if (T.isNull(user)) {
            List<User> list = userServiceImpl.selectStyle(user);
            log.info("查询成功：{}", list);
            System.out.println(list);
            T.usetStyle("访问：userStyle,一对多查询,条件：" + user);//存储操作记录
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
    public Return pro(@RequestBody User user) {
        log.info("\n\n查询用户信息 PRO(/pro){}", "\n条件：" + user + "\n结果：");
        if (T.isNull(user)) {

            T.usetStyle("访问：pro,查询用户信息PRO,条件：" + user);//存储操作记录
            List<User> userList = userServiceImpl.list(user);
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
    public Return Page(@RequestParam(defaultValue = "1") Integer pageNum, Integer pageSize) {
        log.info("\n\n分页查询信息(/Page){}", "\n条件：pageNum:" + pageNum + "pageSize:" + pageSize + "\n结果：");
        if (T.isNull(pageNum + pageSize)) {
            T.usetStyle("访问：Page,分页查询用户信息");//存储操作记录
            IPage<User> ipage = userServiceImpl.selectPage(pageNum, pageSize);
            log.info("分页查询成功{}", ipage);
            return Return.ok();
        }
        log.info("分页查询失败：条件");
        return Return.error();
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
        log.info("\n\n上传文件(post/postFile){}", "\n条件:" + name + "\n结果：");
        if (T.isNull(file)) {
            String s = T.saveFile(name, file, request);//保存文件
            log.info("上传文件成功{}", s);
            T.usetStyle("访问：postFile,上传文件");//存储操作记录
            return Return.ok();
        }
        log.info("上传文件失败：条件，file为空");
        return Return.error();
    }
}