package com.gzh.tilas.Controller;

import com.gzh.tilas.Service.EmployeeService;
import com.gzh.tilas.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    // 添加员工
    @PostMapping("/emps")
    public String insert(@RequestBody Employee employee) {
        int result = employeeService.insert(employee);
        if (result > 0) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    // 根据员工ID查询员工信息
    @GetMapping("/emps")
    public Employee getById(Integer id) {
        Employee employee = employeeService.getById(id);
        return employee;
    }

    // 根据员工id删除员工信息
    // @PathVariable 用于从 URL 路径中提取值，并将其绑定到方法的参数上
    @DeleteMapping("/emps")
    public String deleteById(Integer id) {
        int result = employeeService.deleteById(id);
        if (result > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

}
