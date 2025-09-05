# Tilas 教育管理系统

## 2025-0904 
创建项目，构建 maven 项目，添加必要的 Spring Web、MyBatis Framework、MySQL Driver、Lombok 等依赖项；

Spring Boot 版本为 3.5.5

构建 MySQL 数据库 Tilas，存储项目中用到的数据，如部门表、员工表等；

构建后端项目骨架，在 `src\main\resources` 目录下的 `application.yml` 配置文件中添加数据库依赖，注意将原先的后缀名 `properties` 改成 `yml`，方便使用层级缩进的方式更简洁方便的添加配置。如数据库连接 `url`、`username`、`password`、`driver-class-name` 等，以及 `mybatis` 配置，如 `map-underscore-to-camel-case: true`，自动将数据库字段名的下划线命名映射到 java 实体类的驼峰命名属性上；

首先在 com.gzh.Tilas 下新建 pojo 包，然后在 pojo 包下创建实体类 Dept.java，用于模拟部门，有部门 ID、部门名称、创建时间、修改时间等属性。

```java
import lombok.Data;
import java.time.LocalDateTime;

@Data // Lombok注解，自动生成Getter, Setter, toString等
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

然后在 com.gzh.tilas 下新建 mapper 包，在 mapper 包下创建 DeptMapper.java 接口，一般在DeptMapper接口中定义SQL语句，执行SQL数据查询、管理等操作，负责存取和管理数据，并将数据返回给Service层，Service层来执行复杂的功能逻辑；

```java
@Mapper // 标记为Mybatis的Mapper接口
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> list();
}
```

在com.gzh.tilas下新建Service包，在Service包下新建DeptService.java接口和impl\DeptServiceImpl.java实现类，实现类负责实现同Service包下的接口，接口中只定义抽象方法名，具体功能在实现类下重写。

DeptService.java接口

```java
package com.gzh.tilas.Service;

import com.gzh.tilas.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();
}

```

impl\DeptServiceImpl,java实现类

```java
package com.gzh.tilas.Service.impl;


import com.gzh.tilas.Mapper.DeptMapper;
import com.gzh.tilas.Service.DeptService;
import com.gzh.tilas.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // 标记为Spring的Service类
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }
}
```



















EmployeeController

























































































































## 零碎知识点记录

驼峰命名规范，特点是单词之间不使用分隔符，通过单词首字母大写来区分；

**小驼峰命名法（Lower Camel Case）：**

-   第一个单词首字母小写，后续单词首字母大写；
-   示例：`userName`，`firstName`，`getUserId()`；
-   常用于**变量名、函数名**。

**大驼峰命名法（Upper Camel Case）：**

-   每个单词的首字母都大写；
-   示例：`UserController`, `OrderService`, `PersonInfo`；
-   常用于**类名、接口名**等。

蛇形命名法（Snake Case）:

-   单词之间用下划线 `_` 分隔，所有字母通常小写。
-   示例：`user_name`, `order_id`, `get_user_info()`。
-   常用于**数据库字段名、配置文件键名**等。

全大写命名法（Upper Case）：

-   所有字母大写，单词之间用下划线 `_` 分隔。
-   示例：`MAX_VALUE`, `USER_ROLE`, `API_KEY`。
-   常用于**常量名、枚举值**等。
