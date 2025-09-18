package com.gzh.tilas.Controller;

import com.gzh.tilas.Service.DeptService;
import com.gzh.tilas.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 建议添加日志注解，方便调试
@RestController
@RequestMapping("/depts") // 统一的资源路径前缀
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门信息
     * GET /depts
     */
    @GetMapping
    public Result list() {
        log.info("查询所有部门数据");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    /**
     * 分页查询部门信息
     * GET /depts?page=1&pageSize=10
     */
    @GetMapping(params = {"page", "pageSize"})
    public Result listByPage(Integer page, Integer pageSize){
        log.info("分页查询部门数据: page={}, pageSize={}", page, pageSize);
        PageBean<Dept> deptListByPage = deptService.listByPage(page, pageSize);
        return Result.success(deptListByPage);
    }

    /**
     * 根据ID删除部门信息
     * DELETE /depts/1
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        log.info("根据id删除部门: {}", id);
        int rows = deptService.deleteById(id);
        // 你可以根据业务需求判断rows，如果为0可以返回错误信息
        if (rows > 0) {
            return Result.success(); // 删除成功，不需要返回数据
        } else {
            return Result.error("删除失败，部门不存在或无法删除");
        }
    }

    /**
     * 添加部门信息
     * POST /depts
     */
    @PostMapping
    public Result insert(@RequestBody DeptAddDTO deptAddDTO) {
        log.info("新增部门: {}", deptAddDTO);
        deptService.insert(deptAddDTO); // 假设service层处理了异常
        return Result.success();
    }

    /**
     * 根据ID查询部门信息
     * GET /depts/1
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查询部门: {}", id);
        Dept dept = deptService.getById(id);
        if (dept != null) {
            return Result.success(dept);
        } else {
            return Result.error("查询失败，未找到该部门");
        }
    }

    /**
     * 修改部门信息
     * Body中要给定要修改的部门信息，最重要要有部门id，根据这个部门id来识别要修改的部门
     * PUT /depts
     */
    @PutMapping
    public Result update(@RequestBody DeptUpdateDTO deptUpdateDTO) {
        log.info("修改部门信息");
        deptService.update(deptUpdateDTO);
        return Result.success();
    }
}