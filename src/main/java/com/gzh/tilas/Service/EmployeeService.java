package com.gzh.tilas.Service;

import com.gzh.tilas.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    // 增加员工
    public int insert(Employee employee);

    // 删除员工
    public int deleteById(Integer id);
    public int deleteByName(Employee employee);

    // 修改员工
    public int update(Employee employee);

    // 查询员工
    public Employee getById(Integer id);
    public Employee getByName(Employee employee);

    // 查询所有员工
    public List<Employee> list();
    // 根据部门id查询某个部门的所有员工
    public List<Employee> listByDeptId(Integer deptId);

}
