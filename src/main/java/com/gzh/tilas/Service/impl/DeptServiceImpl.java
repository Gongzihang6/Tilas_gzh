package com.gzh.tilas.Service.impl;


import com.gzh.tilas.Mapper.DeptMapper;
import com.gzh.tilas.Service.DeptService;
import com.gzh.tilas.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // 标记为Spring的Service类
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;  // 注入Mapper接口, 用于调用Mapper接口中的方法, 访问数据库

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }
}
