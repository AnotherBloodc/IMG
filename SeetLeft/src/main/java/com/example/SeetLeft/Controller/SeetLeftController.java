package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.SeetLeftService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


//RestController：用于将返回值直接作为HTTP响应体返回给客户端
//Controller：用于将返回值作为视图名称，然后由视图解析器解析为实际的视图对象

/**
 * Spring Boot 默认的视图解析器是 Thymeleaf 或 InternalResourceViewResolver （用于 JSP）如下<br/>
 * (1) Thymeleaf（推荐）<br/>
 * 默认模板目录： src/main/resources/templates/<br/>
 * (2) JSP（传统方式）<br/>
 * 默认模板目录： src/main/webapp/WEB-INF/views/
 */

@RestController
public class SeetLeftController {

    //获取当前项目路径
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");


    @Resource
    private SeetLeftService seetLeftService;


    /**
     * 处理根路径的POST请求
     *
     * @return 固定返回1表示请求处理成功
     */
    //@RequestBody：用于将请求体中的JSON数据绑定到方法的参数上，required = false：允许请求体中的JSON数据可以为空
    //相似注解：@RequestParam：用于将请求参数绑定到方法的参数上，required = false：允许请求参数可以为空
    //相似注解：@PathVariable：用于将URL路径中的变量绑定到方法的参数上，required = false：允许URL路径中的变量可以为空
    //相似注解：@CookieValue：用于将Cookie中的值绑定到方法的参数上，required = false：允许Cookie中的值可以为空
    //相似注解：@RequestHeader：用于将请求头中的值绑定到方法的参数上，required = false：允许请求头中的值可以为空
    //相似注解：@ModelAttribute：用于将请求参数绑定到方法的参数上，required = false：允许请求参数可以为空
    //相似注解：@SessionAttribute：用于将Session中的值绑定到方法的参数上，required = false：允许Session中的值可以为空
    //相似注解：@RequestPart：用于将请求体中的文件绑定到方法的参数上，required = false：允许请求体中的文件可以为空
    //相似注解：@MatrixVariable：用于将URL路径中的矩阵变量绑定到方法的参数上，required = false：允许URL路径中的矩阵变量可以为空
    @GetMapping("/") //或者是@RequestMapping(value = "/*", method = RequestMethod.GET)注解，效果一样
    public int Default(@RequestBody(required = false) String name) {
        System.out.println("你访问了默认页面" + name);
        return 1;
    }

    /**
     * 插入用户信息
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/Insert")//或者是@RequestMapping(value = "/Insert", method = RequestMethod.POST)注解，效果一样
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
    @DeleteMapping("/Delete")//或者是@RequestMapping(value = "/Delete", method = RequestMethod.DELETE)注解，效果一样
    public int Delete(@RequestBody(required = false) int id) {
        System.out.printf("-----------------Delete-----------------" + id);
        //非空验证
        if (id < 1) {
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
    @PutMapping("/Update")//或者是@RequestMapping(value = "/Update", method = RequestMethod.PUT)注解，效果一样
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
    @RequestMapping(value = "/Server", method = RequestMethod.GET)//或者是GetMapping("/Server")注解，效果一样
    public List<User> Server(@RequestBody(required = false) User user) {
        System.out.printf("-----------------Server-----------------" + user);
        //非空验证
        if (user == null) {
            System.out.println("查询用户信息user对象为空");
            return null;
        }
        return seetLeftService.server(user);
    }

    /**
     * 上传文件
     *
     * @param name 文件名
     * @param file 文件
     * @return postFile
     */
    @RequestMapping(value = "/postFile", method = RequestMethod.POST)
    public String postFile(String name, MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("文件上传成功" + name);
        System.out.println("前端传输的文件参数名：" + file.getName());
        System.out.println("文件类型" + file.getContentType());
        System.out.println("文件大小：" + file.getSize());
        System.out.println("文件原型：" + file.getOriginalFilename());
        saveFile(file, request);//保存文件
        return "postFile";
    }

    /**
     * 保存文件
     *
     * @param file 文件
     * @throws IOException IOException
     */
    public void saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String src = UPLOADED_FOLDER + "/SeetLeft/src/main/resources/img/";//创建：本地保存的路径
        String srcWeb = request.getServletContext().getRealPath("/static/images/");//创建：服务器保存的路径
        File upDir = new File(srcWeb);//创建：本地文件夹的对象，用于判断文件夹是否存在
        boolean i;//赋值：文件是否保存成功的状态码
        System.out.println("保存文件的文件夹的路径" + src);
        System.out.println("服务器保存的路径：" + srcWeb);
        //逻辑：判断文件夹是否存在，不存在则创建
        if (!upDir.exists()) {
            System.out.print("文件夹：不存在，开始创建文件夹");
            System.out.println(upDir.mkdirs());//执行：创建文件夹
        } else {
            System.out.println("文件夹：存在，无需创建");
        }
        File f = new File(srcWeb + file.getOriginalFilename());//创建：需要保存的文件的对象
        file.transferTo(f);//执行：保存文件
        i = f.exists();//赋值：文件是否保存成功的状态码
        System.out.println("文件保存成功:" + i);
    }
}
