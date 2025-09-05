package com.gzh.tilas.Mapper;

import com.gzh.tilas.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper // 标记为Mybatis的Mapper接口
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> list();
}
