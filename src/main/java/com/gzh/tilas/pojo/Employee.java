package com.gzh.tilas.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * -- 职位ID说明（也可以创建单独的职位表）
 * -- 1: 开发工程师
 * -- 2: 班主任
 * -- 3: 讲师
 * -- 4: 辅导员
 * -- 5: 院长
 *
 * -- 部门ID说明
 * 1,教务处
 * 2,学生处
 * 3,计算机学院
 * 4,外语学院
 * 5,财务处
 * 6,人事处
 * 7,后勤部
 * 8,图书馆
 * 9,科研处
 * 10,招生办公室
 * 11,就业指导中心
 * 12,国际交流处
 * 13,体育部
 * 14,艺术学院
 * 15,工程学院
 * 16,医学院
 * 17,法学院
 * 18,心理咨询中心
 * 19,网络信息中心
 * 20,保卫处
 */


@Data
public class Employee {
    private Integer id;         // 员工ID，无符号整数，主键自增
    private String username;    // 员工用户名，最长20个字符，非空且唯一
    private String password;    // 员工密码，最长32个字符，非空
    private String name;        // 员工姓名，最长10个字符，非空
    private Integer gender;     // 1:男  2:女
    private String avatar;     // 头像url
    private Integer deptId;     // 部门ID，关联部门表
    private Integer jobId;      // 职位ID
    private Double salary;        // 薪资
    private LocalDate entryDate;    // 入职时间 年月日
    private LocalDateTime createTime;   // 创建时间 年月日时分秒
    private LocalDateTime updateTime;   // 修改时间 年月日时分秒
    private String phoneNumber;     // 手机号
}
