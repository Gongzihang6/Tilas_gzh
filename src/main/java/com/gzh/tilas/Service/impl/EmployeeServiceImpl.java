package com.gzh.tilas.Service.impl;

import com.gzh.tilas.Mapper.EmployeeMapper;
import com.gzh.tilas.Service.EmployeeService;
import com.gzh.tilas.exception.BusinessException;
import com.gzh.tilas.pojo.*;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Value("${Tilas.Employee.defaultPassword}")
    private String defaultPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 根据insert方法名，mybatis自动执行xml中id="insert"对应的sql语句
    // 获取输入参数employee中的name、age、gender、deptId、jobId、entryDate等值
    // 添加员工
    @Override
    public int insert(Employee employee){
        // --- 由后端负责设置敏感或服务端生成的字段 ---
        // 1. 设置默认密码并加密

        employee.setPassword(passwordEncoder.encode(defaultPassword));   // 密码加密
        // employee.setPassword(defaultPassword);

        // 2. 设置创建和更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        return employeeMapper.insert(employee);
    }
    // 根据员工id删除员工
    @Override
    public int deleteById(Integer id){
        return employeeMapper.deleteById(id);
    }
    // 根据员工姓名删除员工
    @Override
    public int deleteByName(String  name){
        return employeeMapper.deleteByName(name);
    }
    // 根据id数组批量删除员工
    @Override
    public int deleteByIds(Integer [] ids){
        return employeeMapper.deleteByIds(ids);
    }
    // 修改员工信息
    @Override
    public int update(EmployeeUpdateDTO employeeUpdateDTO){
        // 1. 将 DTO 转换为一个不完整的 Employee 实体
        Employee employeeToUpdate = new Employee();
        BeanUtils.copyProperties(employeeUpdateDTO, employeeToUpdate);

        // 3. 调用 Mybatis-Plus 的 updateById 方法
        //    该方法会生成一个动态 SQL，只更新 employeeToUpdate 中非 null 的字段。
        //    由于 DTO 中没有 password 和 createTime，所以这两个字段在数据库中会保持原样。
        employeeMapper.update(employeeToUpdate);
        return 1;
    }

    // 根据员工id查询员工
    @Override
    public Employee getById(Integer id){
        return employeeMapper.getById(id);
    }
    // 根据员工姓名查询员工
    @Override
    public Employee getByName(String name){
        return employeeMapper.getByName(name);
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

    /**
     * 分页查询员工
     * @param page          页码
     * @param pageSize      每页记录数
     * @param name
     * @param gender
     * @param deptId
     * @param jobId
     * @param begin
     * @param end
     * @return
     */
    @Override
    public PageBean<Employee> listByPage(Integer page, Integer pageSize, String name, Integer gender, Integer deptId, Integer jobId, String begin, String end) {
        log.info("分页查询员工, 参数: 查询第{}页,每页{}条数据,匹配参数->姓名：{},性别：{},所属部门{},职位{}，最早入职日期：{},最晚入职日期：{}", page, pageSize, name, gender, deptId, jobId, begin, end);

        // 获取总记录数
        Long total = employeeMapper.count(name, gender, deptId, jobId, begin, end);

        // 计算起始索引 offset
        int offset = (page - 1) * pageSize;
        List<Employee> employeeList = employeeMapper.listByPage(name, gender, deptId, jobId, begin, end, offset, pageSize);
        return new PageBean<Employee>(total, employeeList);
    }

}
