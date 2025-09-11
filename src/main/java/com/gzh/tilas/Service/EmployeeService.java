package com.gzh.tilas.Service;

import com.gzh.tilas.pojo.Employee;
import com.gzh.tilas.pojo.EmployeeUpdateDTO;
import com.gzh.tilas.pojo.PageBean;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    // 增加员工
    public int insert(Employee employee);

    // 删除员工
    public int deleteById(Integer id);
    public int deleteByName(String  name);
    public int deleteByIds(Integer [] ids);
    // 修改员工
    public int update(EmployeeUpdateDTO employeeUpdateDTO);
    void partialUpdate(Integer id, Map<String, Object> updates);

    // 查询员工
    public Employee getById(Integer id);
    public Employee getByName(String name);

    // 查询所有员工
    public List<Employee> list();

    /**
     * 分页查询员工
     * @param page          查询的页码，比如查询第1页的员工信息，则page=1
     * @param pageSize      每页记录数
     * @param name          姓名，模糊查询
     * @param gender        性别
     * @param begin         入职日期的开始时间
     * @param end           入职日期的结束时间
     * @return  List<Employee>
     */
    public PageBean listByPage(Integer page, Integer pageSize, String name, Integer gender, Integer deptId, Integer jobId, String begin, String end);
    // 根据部门id查询某个部门的所有员工
    public List<Employee> listByDeptId(Integer deptId);

}
