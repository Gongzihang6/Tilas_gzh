package com.gzh.tilas.Controller;

import com.gzh.tilas.Service.EmployeeService;
import com.gzh.tilas.pojo.Employee;
import com.gzh.tilas.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emps") // 统一资源路径，使用复数形式 "employees"
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 分页条件查询员工
     * 只有当请求中包含 page 和 pageSize 参数时，此方法才会被调用
     * GET /employees
     * 可以通过 Query Parameters 进行筛选，例如: /employees?name=张&gender=1
     */
    @GetMapping(params = {"page", "pageSize"})
    public Result listByPage(Integer page, Integer pageSize, String name, Integer gender, String begin, String end){
        log.info("分页查询员工");
        return Result.success(employeeService.listByPage(page, pageSize, name, gender, begin, end));
    }

    // 查询所有员工
    @GetMapping
    public Result list() {
        log.info("查询所有员工");
        return Result.success(employeeService.list());
    }



    /**
     * 根据ID查询员工信息
     * GET /employees/1
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
     * POST /employees
     */
    @PostMapping
    public Result insert(@RequestBody Employee employee) {
        log.info("新增员工: {}", employee);
        employeeService.insert(employee);
        return Result.success();
    }

    /**
     * 根据ID删除员工信息
     * DELETE /employees/1
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
     * 更新员工全部信息 (Full Update)
     * PUT /employees
     * 要求客户端提供完整的员工对象
     */
    @PutMapping
    public Result update(@RequestBody Employee employee) {
        log.info("更新员工(全部字段): {}", employee);
        employeeService.update(employee);
        return Result.success();
    }

    /**
     * 根据员工id更新该员工部分信息 (Partial Update)
     * PATCH /employees/1
     * 客户端可以只提供需要修改的字段
     */
    @PatchMapping("/{id}")
    public Result partialUpdate(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        log.info("部分更新员工ID: {}, 更新内容: {}", id, updates);
        employeeService.partialUpdate(id, updates); // 需要在Service层实现此方法
        return Result.success();
    }
}