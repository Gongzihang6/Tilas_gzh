package com.gzh.tilas.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.ArrayList; // 引入ArrayList

// 注意：这个类需要实现 UserDetails 接口
public class LoginUser implements UserDetails {

    // 持有我们的业务用户对象（Employee）
    private Employee employee;

    // 构造方法，传入业务用户对象
    public LoginUser(Employee employee) {
        this.employee = employee;
    }

    // 提供一个getter方法，方便在Controller中获取Employee对象
    public Employee getUser() {
        return employee;
    }


    // --- 下面是实现 UserDetails 接口必须重写的方法 ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回权限信息，这里我们暂时返回null或一个空列表
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        // 返回数据库中存储的、加密过的密码
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        // 返回用户名
        return employee.getUsername();
    }

    // --- 下面的方法可以根据需要实现，暂时返回true即可 ---

    @Override
    public boolean isAccountNonExpired() {
        // 账户是否未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账户是否未被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 凭证（密码）是否未过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 账户是否启用
        return true;
    }
}