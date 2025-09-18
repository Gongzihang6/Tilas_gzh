package com.gzh.tilas.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeptUpdateDTO {
    @NotNull // ID不能为空，如果为空，无法判断要修改的是哪个部门的信息
    private Integer id;

    @NotBlank
    private String name;
}
