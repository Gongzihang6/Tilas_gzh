package com.gzh.tilas.Service.impl;

import com.gzh.tilas.Mapper.EmployeeMapper;
import com.gzh.tilas.Service.EmployeeService;
import com.gzh.tilas.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
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
    public int deleteByName(String  name){
        return employeeMapper.deleteByName(name);
    }
    // 修改员工信息
    @Override
    public int update(Employee employee){
        return employeeMapper.update(employee);
    }
    @Override
    public void partialUpdate(Integer id, Map<String, Object> updates) {
        // 1. 先根据ID查询出原始的员工对象
        Employee existingEmployee = employeeMapper.getById(id);
        if (existingEmployee == null) {
            throw new RuntimeException("员工不存在, ID: " + id);
        }

        // 2. 遍历 updates Map，根据 key 更新对象的属性
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingEmployee.setName((String) value);
                    break;
                case "gender":
                    existingEmployee.setGender((Integer) value);
                    break;
                case "jobId":
                    existingEmployee.setJobId((Integer) value);
                    break;
                case "salary":
                    // 注意JSON中的数字可能是Integer或Double，需要做类型转换
                    if (value instanceof Double) {
                        existingEmployee.setSalary((Double) value);
                    } else if (value instanceof Integer) {
                        existingEmployee.setSalary(((Integer) value).doubleValue());
                    }
                    break;
                case "deptId":
                    existingEmployee.setDeptId((Integer) value);
                    break;
                // ... 可以添加更多可修改的字段
            }
        });

        // 别忘了设置更新时间
        existingEmployee.setUpdateTime(LocalDateTime.now());

        // 3. 调用Mapper的update方法，将修改后的完整对象更新到数据库
        // SQL语句不变，依旧是接收一个Employee对象，执行update，该Employee对象部分属性的更新逻辑由EmployeeServiceImpl完成
        employeeMapper.update(existingEmployee);
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
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List<Employee> listByPage(Integer page, Integer pageSize, String name, Integer gender, String begin, String end) {
        log.info("分页查询员工, 参数: 查询第{}页,每页{}条数据,匹配姓名：{},性别：{},最早入职日期：{},最晚入职日期：{}", page, pageSize, name, gender, begin, end);
        // 计算起始索引 offset
        int offset = (page - 1) * pageSize;
        return employeeMapper.listByPage(name, gender, begin, end, offset, pageSize);
    }
}
