package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Tool.Return;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@CrossOrigin
@RestController
@Slf4j
@Tag(name = "用户操作：文件上传", description = "用户执行文件上传")
@RequestMapping("/SeetFile")
public class FileController {
    /**
     * 上传文件：文件和名字<br/>
     * 注解：@PostMapping: 用于设置传递过来的Post请求的访问路径
     *
     * @param name 文件名
     * @param file 文件
     * @return postFile
     */
    @Operation(summary = "上传文件", description = "提供文件名称，文件路径，文件本体")
    @PostMapping("/postFile")
    public Return<Void> postFile(String name, MultipartFile file, HttpServletRequest request) throws IOException {
        //获取当前项目路径
        String UPLOADED_FOLDER = System.getProperty("user.dir");

        log.info(LoginEnums.GO + "执行上传文件操作，文件名{}", name);
        //判断文件条件
        if (file == null) {
            return Return.error("没有提供文件参数");
        }
        //执行上传操作
        System.out.println("文件上传成功" + name);
        System.out.println("前端传输的文件参数名：" + file.getName());
        System.out.println("文件类型" + file.getContentType());
        System.out.println("文件大小：" + file.getSize());
        System.out.println("文件原型：" + file.getOriginalFilename());
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
        return Return.ok("上传文件成功" + i);
    }
}
