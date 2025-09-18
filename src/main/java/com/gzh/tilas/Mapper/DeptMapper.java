package com.gzh.tilas.Mapper;

import com.gzh.tilas.pojo.Dept;
import com.gzh.tilas.pojo.DeptUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper // 标记为Mybatis的Mapper接口
public interface DeptMapper {
    // 查询所有部门信息（采用@Select注解的方式）
    // @Select("select * from dept")
    // List<Dept> list();

    /**
     * 分页查询部门信息
     * @param offset        页表的起始索引
     * @param pageSize      每页数据条数
     * @return List<Dept> 部门表
     */
    List<Dept> listByPage(@Param("offset") Integer offset,
                                 @Param("pageSize") Integer pageSize);

    // 查询分页查询部门总数
    Long count();

    // 查询所有部门信息（采用mybatis的xml配置方式）
    List<Dept> list();

    // 根据id查询部门信息
    Dept getById(Integer id);

    // 添加部门信息
    int insert(Dept dept);

    // 修改部门信息（怎么样只修改部门信息的部分信息？）
    int update(DeptUpdateDTO deptUpdateDTO);

    // 删除部门信息
    int deleteById(Integer id);
}
