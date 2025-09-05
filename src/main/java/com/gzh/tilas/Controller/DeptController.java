package com.gzh.tilas.Controller;


import com.gzh.tilas.Service.DeptService;
import com.gzh.tilas.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    // 映射HTTP GET请求到/depts路径
    @GetMapping("/depts")
    public List<Dept> list() {
        return deptService.list();  // 调用DeptService接口的实现类的list()方法
    }
}
