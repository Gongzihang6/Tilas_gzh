package com.gzh.tilas.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgSalaryDTO {
    private String name;    // 部门名称或者职位名称
    private Double avgSalary;       // 平均工资
}
