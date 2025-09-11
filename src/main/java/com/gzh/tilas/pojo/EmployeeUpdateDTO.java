package com.gzh.tilas.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @description: 员工更新数据传输对象，用于接收员工更新数据，手动设置了哪些属性允许进行修改
 * 不包含password、createTime、updateTime，这些属性由后端代码自己生成
 */
@Data
public class EmployeeUpdateDTO {
    // 必须包含 id
    private Integer id;

    // 包含所有允许被编辑的字段
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
