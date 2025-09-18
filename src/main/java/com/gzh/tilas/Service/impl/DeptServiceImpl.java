package com.gzh.tilas.Service.impl;


import com.gzh.tilas.Mapper.DeptMapper;
import com.gzh.tilas.Mapper.EmployeeMapper;
import com.gzh.tilas.Service.DeptService;
import com.gzh.tilas.exception.BusinessException;
import com.gzh.tilas.pojo.Dept;
import com.gzh.tilas.pojo.DeptAddDTO;
import com.gzh.tilas.pojo.DeptUpdateDTO;
import com.gzh.tilas.pojo.PageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // 标记为Spring的Service类
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;  // 注入Mapper接口, 用于调用Mapper接口中的方法, 访问数据库
    @Autowired
    private EmployeeMapper employeeMapper;

    // 查询所有部门信息
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    /**
     * 分页查询部门信息
     * @param page          要查询的页码，也就是第几页数据
     * @param pageSize      每页的数据条数
     * @return List<Dept>
     */
    @Override
    public PageBean<Dept> listByPage(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        Long total = deptMapper.count();
        List<Dept> records = deptMapper.listByPage(offset, pageSize);
        return new PageBean<Dept>(total, records);
    }

    // 根据id查询部门信息
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    // 添加部门信息
    @Override
    public int insert(DeptAddDTO deptAddDTO) {
        Dept deptToAdd = new Dept();
        BeanUtils.copyProperties(deptAddDTO, deptToAdd);
        return deptMapper.insert(deptToAdd);
    }

    // 根据前端传过来的DeptUpdateDTO对象修改部门信息
    @Override
    public int update(DeptUpdateDTO deptUpdateDTO) {
        return deptMapper.update(deptUpdateDTO);
    }

    // 根据部门ID删除部门信息，但是前提是该部门下已经不存在员工，否则抛出异常
    @Override
    public int deleteById(Integer id) {
        // 检查部门下是否有员工
        if (!employeeMapper.listByDeptId(id).isEmpty()) {
            // 直接抛出业务异常，剩下的交给全局异常处理器
            throw new BusinessException("该部门下尚有员工，无法删除");
        }

        return deptMapper.deleteById(id);
    }
}
