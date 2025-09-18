package com.gzh.tilas.pojo;

import lombok.Data;

@Data
public class DeptAddDTO {
    // 添加新的部门，只需要提供部门名称，部门id、创建时间、修改时间不需要提供
    private String name;
}
