package com.example.SeetLeft.Domain;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.SeetLeft.Entity.User;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//登录用户详情
@Data
public class LoginUserDetails implements UserDetails {

    @Resource
    private User user;//用户信息

    private String token;//登录凭证

    //获取权限
    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //获取密码
    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //获取用户名
    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //账号是否未过期
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号是否未锁定
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //密码是否未过期
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账号是否可用
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
