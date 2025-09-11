package com.gzh.tilas.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 在新增员工时，不应该直接接收一个完整的员工对象，而是应该只接收员工部分信息，比如不应该包含员工的ID（因为ID是自增的，不能手动指定）
 * 不应该包含员工的密码、创建时间、更新时间等属性
 * 创建一个 EmployeeAddDTO 类，只包含员工必要信息，方便和前端交互
 */
@Data
public class EmployeeAddDTO {
    private String username;
    private String name;
    private Integer gender;
    private String avatar;
    private Integer deptId;
    private Integer jobId;
    private Double salary;
    private LocalDate entryDate;
    private String phoneNumber;
}
