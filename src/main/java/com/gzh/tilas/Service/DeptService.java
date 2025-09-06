package com.gzh.tilas.Service;

import com.gzh.tilas.pojo.Dept;

import java.util.List;

public interface DeptService {
    // 查询所有部门信息
    List<Dept> list();

    /**
     * 分页查询部门信息
     * @param page          要查询的页码，也就是第几页数据
     * @param pageSize      每页的数据条数
     * @return List<Dept>
     */
    List<Dept> listByPage(Integer page, Integer pageSize);

    // 根据id查询部门信息
    Dept getById(Integer id);

    // 插入部门信息
    int insert(Dept dept);

    // 更新部门信息
    int update(Dept dept);

    // 删除部门信息
    int deleteById(Integer id);

}
