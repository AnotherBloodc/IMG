package com.example.SeetLeft.Tool;

import com.example.SeetLeft.Entity.UserStyle;
import com.example.SeetLeft.Service.Impl.UserServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ToolController {

    //获取当前项目路径
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");

    @Resource
    @Lazy
    private UserServiceImpl seetLeftService;

    /**
     * 保存文件
     *
     * @param file 文件
     * @throws IOException IOException
     */
    public String saveFile(String name, MultipartFile file, HttpServletRequest request) throws IOException {
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
        return "文件上传成功" + i;
    }

    /**
     * 新增操作记录
     *
     * @param name 被操作的记录
     */
    public void usetStyle(String name) {
        UserStyle us = new UserStyle();
        us.setName(name);
        us.setUserid(1);
    }

    public boolean isNull(Object... obj) {
        if (obj != null) {
            for (Object o : obj) {
                if (o != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNull(Integer i) {
        return i != null && i > 0;
    }
}
