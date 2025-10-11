package example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.MapperPro.Impl.UserMapperProImpl;
import com.example.SeetLeft.MapperPro.UserMapperPro;
import com.example.SeetLeft.Service.UserService;
import com.example.SeetLeft.Tool.ToolController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService seetLeftService;

    @Resource
    private ToolController toolController;

    @Resource
    private UserMapperProImpl userMapperProImpl;
    
    @Resource
    private UserMapperPro userMapperPro;


    @GetMapping("/user/pro")
    public List<User> selectPro(User user) {
        return userMapperProImpl.selectPro(user);
    }
    
    /**
     * 使用MyBatis Plus查询所有用户
     *
     * @return 用户列表
     */
    @GetMapping("/user/mp/list")
    public List<User> selectByMyBatisPlus() {
        return userMapperPro.selectList(null);
    }
}