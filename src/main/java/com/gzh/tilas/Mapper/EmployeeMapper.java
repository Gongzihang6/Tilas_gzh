package com.gzh.tilas.Mapper;

import com.gzh.tilas.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    /**
     * 添加员工
     * @param employee 员工对象
     * @return 影响的行数
     */
    public int insert(Employee employee);

    /**
     * 根据员工ID删除员工
     * @param id 员工id
     * @return 影响的行数
     */
    public int deleteById(Integer id);

    /**
     * 根据员工姓名删除员工
     * @param employee 员工对象
     * @return 影响的行数
     */
    public int deleteByName(Employee employee);

    /**
     * 更新员工信息
     * @return 影响的行数
     */
    public int update(Employee employee);

    /**
     * 根据员工ID查询员工
     * @param id 员工id
     * @return 员工对象
     */
    public Employee getById(Integer id);

    /**
     * 根据员工姓名查询员工
     * @param employee 员工对象
     * @return 员工对象
     */
    public Employee getByName(Employee employee);

    /**
     * 查询所有员工
     * @return 员工列表
     */
    public List<Employee> list();

    /**
     * 根据部门ID查询某个部门的所有员工
     * @param deptId 部门id
     */
    public List<Employee> listByDeptId(Integer deptId);
}
