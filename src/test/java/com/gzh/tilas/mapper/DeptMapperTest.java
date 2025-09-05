// package com.gzh.tilas.mapper;
//
// import com.gzh.tilas.Mapper.DeptMapper;
// import com.itheima.tilas.pojo.Dept;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import java.util.List;
//
// import static org.junit.jupiter.api.Assertions.*;
//
// @SpringBootTest
// public class DeptMapperTest {
//     @Autowired
//     private DeptMapper deptMapper;
//
//     @Test // 标记这是一个JUnit 5的测试方法
//     public void testList() {
//         // 1. 调用要测试的方法
//         List<Dept> deptList = deptMapper.list();
//
//         // 2. 打印结果，方便肉眼观察
//         System.out.println("查询到的部门列表：");
//         deptList.forEach(System.out::println);
//
//         // 3. 编写断言 (Assertions)，让程序自动检查结果
//         // 断言：列表不应为null
//         assertNotNull(deptList, "查询结果不应为null");
//         // 断言：如果数据库中有数据，列表不应为空
//         // 你需要先在数据库中手动插入一条或多条包含所有字段的数据
//         assertFalse(deptList.isEmpty(), "数据库中应有测试数据，列表不应为空");
//
//         // 4. 核心！检查驼峰命名映射是否成功
//         // 取出第一条数据进行详细检查
//         Dept firstDept = deptList.get(0);
//         System.out.println("检查第一条数据：" + firstDept);
//
//         // 断言：基础字段 id 和 name 不为 null
//         assertNotNull(firstDept.getId(), "部门ID不应为null");
//         assertNotNull(firstDept.getName(), "部门名称不应为null");
//
//         // 断言：createTime 和 updateTime 字段是否成功从 create_time 和 update_time 映射过来
//         // 这是本次测试的关键！
//         assertNotNull(firstDept.getCreateTime(), "createTime 字段映射失败，值为null");
//         assertNotNull(firstDept.getUpdateTime(), "updateTime 字段映射失败，值为null");
//     }
// }
