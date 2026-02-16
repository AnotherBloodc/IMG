package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.Impl.UserServiceImpl;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Return;
import com.example.SeetLeft.Tool.ToolController;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Account")
@Slf4j
public class AccountController {

    @Resource
    private ToolController T;

    @Resource
    private UserServiceImpl userServiceImpl;

    /**
     * 处理根路径的Get请求<br/>
     * 注解：@GetMapping: 用于设置传递过来的GET请求的访问路径
     * 注解：@RequestBody：用于将请求体中的JSON数据绑定到方法的参数上，required = false：允许请求体中的JSON数据可以为空
     *
     * @return 固定返回1表示请求处理成功
     */
    @GetMapping //可用@RequestMapping(value = "/*", method = RequestMethod.GET)注解代替，效果一样
    public Return<Integer> Default(@RequestParam(required = false) String name) {
        log.info("访问了默认页面{}", name);
        return Return.ok(1);
    }

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 登录操作的结果状态码
     */
    @PostMapping("/Login")
    public Return<Object> login(@RequestBody User user) {
        log.info("\n\n用户登录(/login){}", "\n条件:");
        log.info("登录详细user:{}", user);
        User users = userServiceImpl.getOne(user);
        if (users != null) {
            String tk = JwtUtils.generateToken(users.getUsername());
            return Return.ok(new Object() {
                public final User user = users;
                public final String token = tk;
            });
        }
        return Return.error();
    }

    /**
     * 注册用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/Register")
    public Return<Void> register(@RequestBody User user) {
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
}
