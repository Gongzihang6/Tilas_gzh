package com.gzh.tilas.Service.impl;

import com.gzh.tilas.Mapper.EmployeeMapper;
import com.gzh.tilas.Service.EmployeeService;
import com.gzh.tilas.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    // 根据insert方法名，mybatis自动执行xml中id="insert"对应的sql语句
    // 获取输入参数employee中的name、age、gender、deptId、jobId、entryDate等值
    // 添加员工
    @Override
    public int insert(Employee employee){
        return employeeMapper.insert(employee);
    }
    // 根据员工id删除员工
    @Override
    public int deleteById(Integer id){
        return employeeMapper.deleteById(id);
    }
    // 根据员工姓名删除员工
    @Override
    public int deleteByName(Employee employee){
        return employeeMapper.deleteByName(employee);
    }
    // 修改员工信息
    @Override
    public int update(Employee employee){
        return employeeMapper.update(employee);
    }
    // 根据员工id查询员工
    @Override
    public Employee getById(Integer id){
        return employeeMapper.getById(id);
    }
    // 根据员工姓名查询员工
    @Override
    public Employee getByName(Employee employee){
        return employeeMapper.getByName(employee);
    }
    // 查询所有员工
    @Override
    public List<Employee> list(){
        return employeeMapper.list();
    }
    // 根据部门id查询员工
    @Override
    public List<Employee> listByDeptId(Integer deptId){
        return employeeMapper.listByDeptId(deptId);
    }

}
