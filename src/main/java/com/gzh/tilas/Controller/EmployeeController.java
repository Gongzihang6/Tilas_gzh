package com.gzh.tilas.Controller;

import com.gzh.tilas.Service.EmployeeService;
import com.gzh.tilas.pojo.Employee;
import com.gzh.tilas.pojo.EmployeeAddDTO;
import com.gzh.tilas.pojo.EmployeeUpdateDTO;
import com.gzh.tilas.pojo.Result;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emps") // 统一资源路径，使用复数形式 "emps"
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // 添加密码编码器
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;


    /**
     * 分页条件查询员工
     * 只有当请求中包含 page 和 pageSize 参数时，此方法才会被调用
     * GET /emps
     * 可以通过 Query Parameters 进行筛选，例如: /emps?name=张&gender=1
     */
    @GetMapping(params = {"page", "pageSize"})
    public Result listByPage(Integer page, Integer pageSize, String name, Integer gender, Integer deptId, Integer jobId, String begin, String end){
        log.info("分页查询员工");
        return Result.success(employeeService.listByPage(page, pageSize, name, gender, deptId, jobId, begin, end));
    }

    /**
     * 查询所有员工
     */
    @GetMapping
    public Result list() {
        log.info("查询所有员工");
        return Result.success(employeeService.list());
    }



    /**
     * 根据ID查询员工信息
     * GET /emps/1
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询员工: {}", id);
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        }
        return Result.error("未找到该员工");
    }

    /**
     * 新增员工
     * POST /emps
     */
    @PostMapping
    public Result add(@RequestBody EmployeeAddDTO employeeAddDTO) { // <--- 参数类型改为DTO
        log.info("新增员工: {}", employeeAddDTO);

        // 手动或使用工具 (MapStruct, BeanUtils) 将 DTO 转换为实体
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeAddDTO, employee);     // 将DTO属性复制给实体

        employeeService.insert(employee);
        return Result.success();
    }

    /**
     * 根据ID删除员工信息
     * DELETE /emps/1
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        log.info("根据ID删除员工: {}", id);
        int result = employeeService.deleteById(id);
        if (result > 0) {
            return Result.success();
        }
        return Result.error("删除失败");
    }


    /**
     * 根据ID数组批量删除员工信息
     * DELETE /emps/1
     */
    @DeleteMapping()
    public Result deleteByIds(Integer[] ids) {
        log.info("批量删除员工: {}", ids);
        int result = employeeService.deleteByIds(ids);
        if (result > 0) {
            return Result.success();
        }
        return Result.error("删除失败");
    }

    /**
     * 更新员工全部信息 (Full Update)
     * PUT /empls
     * 要求客户端提供完整的员工对象
     */
    @PutMapping
    public Result update(@RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        log.info("更新员工信息: {}", employeeUpdateDTO);
        employeeService.update(employeeUpdateDTO);
        return Result.success();
    }
}