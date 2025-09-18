package com.gzh.tilas.Service.impl;

import com.gzh.tilas.Mapper.EmployeeMapper;
import com.gzh.tilas.pojo.Employee;
import com.gzh.tilas.pojo.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 创建一个LoginDTO来复用现有的查询方法
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        Employee employee = employeeMapper.getEmployeeByLoginDTO(loginDTO);

        if (employee == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 如果用户存在，将其信息封装成 Spring Security 需要的 UserDetails 对象
        //    - 第一个参数：用户名
        //    - 第二个参数：数据库中存储的、已经加密过的密码
        //    - 第三个参数：权限列表（这里暂时为空）
        return new User(employee.getUsername(), employee.getPassword(), new ArrayList<>());
    }

}
