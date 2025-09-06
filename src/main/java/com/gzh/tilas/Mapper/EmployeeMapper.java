package com.gzh.tilas.Mapper;

import com.gzh.tilas.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    public int deleteByName(String employee);

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
     * @param name 员工姓名
     * @return 员工对象
     */
    public Employee getByName(String name);

    /**
     * 查询所有员工
     * @return 员工列表
     */
    public List<Employee> list();

    /**
     * 分页条件查询员工
     * @param name 姓名, 模糊查询
     * @param gender 性别
     * @param begin 开始时间
     * @param end 结束时间
     * @param offset 起始索引, 用于分页
     * @param pageSize 每页条数, 用于分页
     * @return 员工列表
     */
    List<Employee> listByPage(
            @Param("name") String name,
            @Param("gender") Integer gender,
            @Param("begin") String begin,
            @Param("end") String end,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );
    /**
     * 根据部门ID查询某个部门的所有员工
     * @param deptId 部门id
     */
    public List<Employee> listByDeptId(Integer deptId);
}
