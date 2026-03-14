package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Tool.Return;
import com.example.SeetLeft.Tool.FileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/File")
@Slf4j
@Tag(name = "用户操作：文件上传", description = "用户执行文件上传")
public class FileController {

    @Resource
    private FileUtils T;

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
        log.info(LoginEnums.GO +"你正在执行上传文件操作{}", "\n条件:" + name + "\n结果：");
        if (file != null) {
            String s = T.saveFile(name, file, request);//保存文件
            log.info("上传文件成功{}", s);
            T.userStyle("访问：postFile,上传文件");//存储操作记录
            return Return.ok();
        }
        log.info("上传文件失败：条件，file为空");
        return Return.error();
    }
}
