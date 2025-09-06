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

    // 查询所有部门信息
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    /**
     * 分页查询部门信息
     * @param page          要查询的页码，也就是第几页数据
     * @param pageSize      每页的数据条数
     * @return List<Dept>
     */
    @Override
    public List<Dept> listByPage(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        return deptMapper.listByPage(offset, pageSize);
    }

    // 根据id查询部门信息
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    // 添加部门信息
    @Override
    public int insert(Dept dept) {
        return deptMapper.insert(dept);
    }

    // 修改部门信息
    @Override
    public int update(Dept dept) {
        return deptMapper.update(dept);
    }

    // 删除部门信息
    @Override
    public int deleteById(Integer id) {
        return deptMapper.deleteById(id);
    }
}
