package com.gzh.tilas.Mapper;

import com.gzh.tilas.pojo.AvgSalaryDTO;
import com.gzh.tilas.pojo.Employee;
import com.gzh.tilas.pojo.GenderCountDTO;
import com.gzh.tilas.pojo.LoginDTO;
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
    int insert(Employee employee);

    /**
     * 根据员工ID删除员工
     * @param id 员工id
     * @return 影响的行数
     */
    int deleteById(Integer id);

    /**
     * 根据员工姓名删除员工
     * @param employee 员工对象
     * @return 影响的行数
     */
    int deleteByName(String employee);

    /**
     * 根据员工ID批量删除员工
     * @param ids 员工id数组
     * @return 影响的行数
     */
    int deleteByIds(Integer[] ids);
    /**
     * 更新员工信息
     * @return 影响的行数
     */
    int update(Employee employee);

    /**
     * 根据员工ID查询员工
     * @param id 员工id
     * @return 员工对象
     */
    Employee getById(Integer id);

    /**
     * 根据员工姓名查询员工
     * @param name 员工姓名
     * @return 员工对象
     */
    Employee getByName(String name);

    /**
     * 查询所有员工
     * @return 员工列表
     */
    List<Employee> list();

    /**
     * 分页条件查询员工
     * @param name 姓名, 模糊查询
     * @param gender 性别
     * @param deptId 部门ID
     * @param jobId 职位ID
     * @param begin 开始时间
     * @param end 结束时间
     * @param offset 起始索引, 用于分页
     * @param pageSize 每页条数, 用于分页
     * @return 员工列表
     */
    List<Employee> listByPage(
            @Param("name") String name,
            @Param("gender") Integer gender,
            @Param("deptId") Integer deptId,
            @Param("jobId") Integer jobId,
            @Param("begin") String begin,
            @Param("end") String end,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );
    /**
     * 根据部门ID查询某个部门的所有员工
     * @param deptId 部门id
     */
    List<Employee> listByDeptId(Integer deptId);

    /**
     * 计算分页查询到的员工的总数
     * @param name      员工姓名，模糊查询
     * @param gender    员工性别，1-男，2-女
     * @param deptId    员工部门ID
     * @param jobId     员工职位ID
     * @param begin     员工入职时间段开始
     * @param end       员工入职时间段结束
     * @return 员工总数
     */
    Long count(String name, Integer gender, Integer deptId, Integer jobId, String begin, String end);

    /**
     * 根据登录信息查询员工
     * @param loginDTO 登录信息
     * @return 员工对象
     */
    Employee getEmployeeByLoginDTO(LoginDTO loginDTO);

    /**
     * 根据性别统计员工数量
     * @return 员工性别统计对象列表，每个对象包含性别和员工数量
     */
    List<GenderCountDTO> countByGender();

    /**
     * 根据部门ID统计部门员工平均工资
     * @return 部门员工平均工资对象列表，每个对象包含部门ID和平均工资
     */
    List<AvgSalaryDTO> countAvgSalaryByDept();

    /**
     * 根据职位ID统计职位员工平均工资
     * @return 部门员工平均工资对象列表，每个对象包含职位ID和平均工资
     */
    List<AvgSalaryDTO> countAvgSalaryByJob();
}
