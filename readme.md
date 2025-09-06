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

然后在 com.gzh.tilas 下新建 mapper 包，在 mapper 包下创建 DeptMapper.java 接口，一般在 DeptMapper 接口中定义 SQL 语句，执行 SQL 数据查询、管理等操作，负责存取和管理数据，并将数据返回给 Service 层，Service 层来执行复杂的功能逻辑；

```java
@Mapper // 标记为Mybatis的Mapper接口
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> list();
}
```

在 com.gzh.tilas 下新建 Service 包，在 Service 包下新建 DeptService.java 接口和 impl\DeptServiceImpl.java 实现类，实现类负责实现同 Service 包下的接口，接口中只定义抽象方法名，具体功能在实现类下重写。

DeptService.java 接口

```java
package com.gzh.tilas.Service;

import com.gzh.tilas.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();
}

```

impl\DeptServiceImpl, java 实现类

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



## 2025-09-05

创建好了员工表，用 AI 随机生成表数据。

实现了添加员工和根据 id 删除员工的后端API开发

## 2025-09-06

实现根据部门id查询部门信息、添加部门信息、修改部门信息、删除部门信息等功能，实现根据员工姓名删除员工信息、更新员工信息、根据员工id查询员工信息、根据员工姓名查询员工信息、查询所有员工信息、根据部门id查询该部门下所有员工信息等功能。

至此，员工和部门的所有增删改查操纵已经基本完成，通过apifox测试。

**优化：**

-   统一Result结果返回格式，添加slf4j日志记录；
-   实现部分修改员工信息；

尝试构建前端页面：

-   node.js版本：v20.12.0
-   npm版本：10.5.0























































































































## 零碎知识点记录

### 命名规范

驼峰命名规范，特点是单词之间不使用分隔符，通过单词首字母大写来区分；

**小驼峰命名法（Lower Camel Case）：**

-   第一个单词首字母小写，后续单词首字母大写；
-   示例：`userName`，`firstName`，`getUserId()`；
-   常用于 **变量名、函数名**。

**大驼峰命名法（Upper Camel Case）：**

-   每个单词的首字母都大写；
-   示例：`UserController`, `OrderService`, `PersonInfo`；
-   常用于 **类名、接口名** 等。

蛇形命名法（Snake Case）:

-   单词之间用下划线 `_` 分隔，所有字母通常小写。
-   示例：`user_name`, `order_id`, `get_user_info()`。
-   常用于 **数据库字段名、配置文件键名** 等。

全大写命名法（Upper Case）：

-   所有字母大写，单词之间用下划线 `_` 分隔。
-   示例：`MAX_VALUE`, `USER_ROLE`, `API_KEY`。
-   常用于 **常量名、枚举值** 等。

### @RequestBody注解

这种情况下，请求参数为Dept实体类，但事实上只需要name一个参数，为什么无法使用json键值对的方式传递name参数，而只能使用query参数传递name参数？

```java
    // 添加部门信息
    @PostMapping("/depts")
    public String insert(@RequestBody Dept dept) {
        int rows = deptService.insert(dept);
        if (rows > 0) {
            return "添加成功！";
        } else {
            return "添加失败！";
        }
    }
```

<u>刚开始没有加@RequestBody注解，在apifox测试的时候，尝试使用body中的json参数格式来传递对象参数会报错，报错信息为请求参数为空，没有封装到对象参数中。</u>

好的，这是一个非常经典且重要的问题，它触及了 Spring MVC 中数据绑定的核心机制。你观察到的现象非常准确，下面我来为你详细解释为什么会这样。

### 核心原因

**Spring MVC 处理“JSON 请求体”和处理“URL 查询参数”是两种完全不同的机制。**

*   当你发送 `{"name": "xxx"}` 这样的 **JSON 数据**时，数据位于 **HTTP 请求体 (Request Body)** 中。Spring MVC 默认不会去解析它，除非你明确告诉它。
*   当你发送 `?name=xxx` 这样的**查询参数**时，数据位于 **URL** 中。这是 Spring MVC 的**默认绑定机制**之一，它可以自动将参数映射到实体类的属性上。

让我们深入这两种机制。

---

### 机制一：URL 查询参数/表单参数绑定 (你成功的那个)

这是 Spring MVC 的默认行为，它非常智能。

**Controller 方法代码（可能是这样的）：**

```java
@PostMapping("/depts")
public Result add(Dept dept) { // 注意：这里没有 @RequestBody 注解
    deptService.add(dept);
    return Result.success();
}
```

**工作流程：**

1.  **接收请求**：前端发送一个请求，例如 `POST /depts?name=教学部`。
2.  **创建对象**：Spring MVC 看到方法参数是一个 `Dept` 类型的 POJO（Plain Old Java Object），它会通过反射自动创建一个 `Dept` 的实例 (`new Dept()`)。
3.  **匹配和赋值**：Spring MVC 会检查请求中的所有**查询参数**（或表单 `x-www-form-urlencoded` 数据）。它发现了一个名为 `name` 的参数。
4.  **调用 Setter**：Spring MVC 会在 `Dept` 实例中寻找一个名为 `setName(String name)` 的方法，然后将参数值 "教学部" 传进去，完成赋值 `dept.setName("教学部")`。
5.  **传递给方法**：最终，这个被赋好值的 `dept` 对象被传递到你的 `add` 方法中。

**结论**：这种方式之所以能行，是因为 Spring MVC 默认就会尝试将 URL 中的参数名与实体类的属性名进行匹配并赋值。

---

### 机制二：JSON 请求体绑定 (你失败的那个)

要让 Spring MVC 处理 JSON 数据，你需要“主动开启”这个功能。

**Controller 方法代码（应该是这样的）：**

```java
@PostMapping("/depts")
public Result add(@RequestBody Dept dept) { // 注意：关键在于 @RequestBody 注解
    deptService.add(dept);
    return Result.success();
}
```

**工作流程：**

1.  **接收请求**：前端发送一个 `POST /depts` 请求，同时设置 `Content-Type: application/json` 请求头，并将 `{"name": "教学部"}` 放在 **HTTP 请求体**中。
2.  **`@RequestBody` 注解**：Spring MVC 看到 `@RequestBody` 注解，它就知道：“哦，我需要去请求体里找数据，并且这个数据是需要反序列化的（比如 JSON 格式）”。
3.  **调用转换器**：Spring MVC 会调用一个内置的 `HttpMessageConverter`（通常是 `Jackson2HttpMessageConverter`）来读取整个请求体。
4.  **反序列化**：Jackson 库会将 JSON 字符串 `{"name": "教学部"}` 解析并映射到一个新的 `Dept` 对象实例中。
5.  **传递给方法**：这个从 JSON 转换而来的 `dept` 对象被传递到你的 `add` 方法中。

#### 为什么你用 JSON 的方式失败了？

你很可能在 Controller 方法中**遗漏了 `@RequestBody` 注解**。

如果你的代码是 `public Result add(Dept dept)`（没有 `@RequestBody`），当你发送一个 JSON 请求时：
*   Spring MVC 不会去看请求体 (Request Body)。
*   它会按照**机制一**的方式工作，去 URL 中寻找 `name` 参数。
*   因为 URL 中没有 `?name=xxx`，所以它找不到任何可以设置的属性。
*   最终，它创建了一个空的 `Dept` 对象（所有字段都为 `null`），并将其传递给你的方法。
*   当这个 `dept` 对象（`name` 属性为 `null`）被传递到 MyBatis 的 `<insert>` 语句时，数据库的 `name` 字段很可能有一个 `NOT NULL` 约束，于是数据库层面就会报错，导致插入失败。

---

### 总结与最佳实践

| 特性             | URL 查询参数绑定                                             | JSON 请求体绑定 (`@RequestBody`)                        |
| :--------------- | :----------------------------------------------------------- | :------------------------------------------------------ |
| **数据位置**     | HTTP URL 的查询字符串中 (`?key=value`)                       | HTTP 请求体 (Request Body) 中                           |
| **关键注解**     | **不需要**特殊注解 (针对 POJO)                               | **必须使用 `@RequestBody`**                             |
| **Content-Type** | `application/x-www-form-urlencoded` 或 `multipart/form-data` | `application/json` (最常用)                             |
| **适用场景**     | `GET` 请求的过滤、分页；简单的 `POST` 表单提交               | **RESTful API 的 `POST`, `PUT` 请求**，用于传输复杂对象 |
| **你的情况**     | `?name=xxx` 能成功，因为 Spring 默认按此方式工作             | `{"name":"xxx"}` 失败，因为你很可能漏了 `@RequestBody`  |

**最佳实践**：
对于**添加（Create）**和**修改（Update）**操作，特别是当数据可能变得复杂时（比如一个员工对象，有姓名、性别、入职日期等多个字段），**强烈推荐使用 `application/json` + `@RequestBody` 的方式**。这已经成为现代 RESTful API 设计的事实标准，因为它更灵活、更结构化，也更清晰。











































































































































































太棒了！你的思路完全正确，并且你设想的后续功能（文件上传、登录认证、数据可视化）恰好是构成一个完整、现代化、且能写在简历上的项目所必需的核心模块。这表明你不仅掌握了基础，更对企业级应用的全貌有了很好的认知。

现在，让我们把你的设想变成一个详细、可执行的作战计划。我将为你制定一个分为四个阶段的详细工作方案，涵盖从前端搭建到高级功能实现再到最终部署的全过程。

---

### 宏观作战地图：项目进阶路线图

| 阶段                           | 核心任务                                                     | 涉及技术/知识点                                              | 产出成果                                                  |
| :----------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- | :-------------------------------------------------------- |
| **阶段一：前端基石与功能复现** | 搭建Vue 3项目，集成UI库，实现部门和员工的CRUD，并与后端API联调。 | Vue 3, Vite, pnpm, Element Plus, Axios, Vue Router, CORS     | 一个功能完整的后台管理界面，能流畅地操作员工和部门数据。  |
| **阶段二：认证与权限**         | 实现用户登录、登出功能，保护API接口，实现前端路由守卫。      | Spring Boot Interceptor, JWT, Spring Security (可选), Axios拦截器, Vue Router导航守卫, Pinia | 一个安全的系统，用户需登录才能访问，API不再能被随意调用。 |
| **阶段三：高级功能拓展**       | 实现文件上传至阿里云OSS，员工数据统计与图表展示。            | 阿里云OSS SDK, `MultipartFile`, ECharts, vue-echarts         | 员工档案可上传头像，系统首页有酷炫的数据统计图表。        |
| **阶段四：优化与部署**         | 代码优化，使用Nginx进行反向代理，使用Docker容器化部署。      | Nginx, Docker, Docker Compose                                | 一个可以公网访问的、稳定运行的线上项目。                  |

---

### 阶段一：前端基石与功能复现 (当前阶段)

这是你马上要开始的工作，目标是让你现有的后端API“活”起来。

**工作步骤与技术细节：**

1.  **环境搭建 (磨刀不误砍柴工):**
    *   **安装 Node.js:** 确保你安装了最新的LTS版本。
    *   **选择包管理器:** 推荐使用 `pnpm`，它比 `npm` 和 `yarn` 更快、更节省磁盘空间。安装命令: `npm install -g pnpm`。
    *   **IDE准备:** 使用 VS Code，并安装以下必备插件：
        *   `Volar` (Vue 3 官方推荐，提供语法高亮和智能提示)
        *   `ESLint` (代码规范检查)
        *   `Prettier` (代码格式化)

2.  **初始化Vue 3项目:**
    *   打开终端，运行命令：`pnpm create vite`
    *   **交互式选择:**
        *   `Project name`: `tilas-frontend`
        *   `Select a framework`: `Vue`
        *   `Select a variant`: `TypeScript` (强烈推荐！即使刚开始不熟，这对你找工作和项目维护性都是巨大加分项)
    *   根据提示进入项目目录，运行 `pnpm install` 安装依赖，然后 `pnpm run dev` 启动项目。

3.  **集成核心库:**
    *   **UI库 (Element Plus):** 提供企业级的后台组件。
        *   安装: `pnpm install element-plus`
        *   在 `main.ts` 中全局引入或使用按需引入（推荐，可以减小打包体积）。
    *   **HTTP客户端 (Axios):** 用于向后端API发送请求。
        *   安装: `pnpm install axios`
    *   **路由 (Vue Router):** 管理前端页面跳转。
        *   安装: `pnpm install vue-router@4`
    *   **状态管理 (Pinia):** Vue 3 官方推荐的状态管理器，用于管理全局状态（如用户信息）。
        *   安装: `pnpm install pinia`

4.  **规划项目结构:**
    *   `src/api`: 存放所有与后端交互的API请求函数 (例如 `employee.ts`, `dept.ts`)。
    *   `src/views`: 存放页面级组件 (例如 `EmployeeManage.vue`, `DeptManage.vue`)。
    *   `src/components`: 存放可复用的UI组件 (例如 `PageHeader.vue`)。
    *   `src/router`: 配置路由规则。
    *   `src/store`: 存放Pinia的store模块 (例如 `userStore.ts`)。
    *   `src/utils`: 存放工具函数，如封装好的axios实例。

5.  **解决跨域问题 (CORS):**
    *   这是前后端联调的第一个拦路虎。你的前端跑在 `localhost:5173`，后端跑在 `localhost:8080`，浏览器会阻止跨域请求。
    *   **后端解决方案:** 在Spring Boot中配置允许跨域。在你的 `DeptController` 和 `EmployeeController` 类上添加注解：
        ```java
        @CrossOrigin
        @RestController
        //...
        ```
        这是一种快速的开发时解决方案。

6.  **开始编码 (以员工管理为例):**
    *   **API封装 (`src/api/employee.ts`):**
        ```typescript
        import request from '@/utils/request'; // 封装的axios实例
        
        export const getEmployeeListAPI = (params) => {
            return request({
                url: '/employees',
                method: 'get',
                params // GET请求的查询参数
            });
        };
        
        export const addEmployeeAPI = (data) => {
            return request({
                url: '/employees',
                method: 'post',
                data // POST请求的请求体
            });
        };
        // ... 其他API
        ```
    *   **页面开发 (`src/views/EmployeeManage.vue`):**
        *   **布局:** 使用 Element Plus 的 `El-Table` 展示员工列表，`El-Pagination` 处理分页，`El-Button` 触发展示 `El-Dialog`，`El-Dialog` 中包含 `El-Form` 用于新增和编辑员工。
        *   **逻辑 (`<script setup lang="ts">`):**
            *   引入API函数: `import { getEmployeeListAPI, ... } from '@/api/employee'`.
            *   使用 `ref` 或 `reactive` 定义响应式数据，如员工列表 `employeeList`，分页信息 `pagination`。
            *   在 `onMounted` 生命周期钩子中调用 `getEmployeeListAPI` 获取初始数据。
            *   编写事件处理函数，如 `handleAdd()`、`handleEdit()`、`handleDelete()`，在函数内部调用对应的API，并在成功后刷新列表。

---

### 阶段二：认证与权限 (构建安全屏障)

**工作步骤与技术细节：**

1.  **后端改造 (JWT认证):**
    *   **引入依赖:** 在 `pom.xml` 中添加 `jjwt` 库用于生成和解析JWT。
    *   **创建登录接口:** 新建 `LoginController`，提供 `POST /login` 接口。接收用户名密码，验证成功后，生成一个包含用户信息的JWT Token返回给前端。
    *   **创建拦截器:** 实现一个 `LoginCheckInterceptor` (实现 `HandlerInterceptor` 接口)。
        *   在 `preHandle` 方法中，从请求头 `Authorization` 中获取Token。
        *   解析Token，如果解析成功且未过期，则放行。
        *   如果Token无效或不存在，则拦截请求，返回401未授权状态码。
    *   **注册拦截器:** 创建一个配置类实现 `WebMvcConfigurer`，重写 `addInterceptors` 方法，将你的拦截器注册进去，并配置需要拦截的路径（`/**`）和需要放行的路径（`/login`）。

2.  **前端改造:**
    *   **创建登录页 (`src/views/Login.vue`):** 一个简单的包含用户名、密码输入框和登录按钮的表单。
    *   **存储Token:** 登录成功后，将后端返回的Token存储在 `Pinia` 和 `localStorage` 中。
    *   **封装Axios拦截器 (`src/utils/request.ts`):**
        *   **请求拦截器:** 在每次发送请求前，检查Pinia中是否有Token。如果有，则自动在请求头中加入 `Authorization: Bearer ${token}`。
        *   **响应拦截器:** 检查后端返回的状态码。如果为401，说明Token失效，此时应清空本地Token并强制跳转到登录页。
    *   **创建路由守卫 (`src/router/index.ts`):**
        *   使用 `router.beforeEach`，在每次路由跳转前进行检查。
        *   如果用户要访问的页面不是登录页，且本地没有Token，则强制重定向到 `/login`。

---

### 阶段三：高级功能拓展 (让项目更丰满)

1.  **文件上传 (阿里云OSS):**
    *   **后端:**
        *   引入阿里云OSS的Java SDK依赖。
        *   在 `application.yml` 中配置OSS的 `endpoint`, `accessKeyId`, `accessKeySecret`, `bucketName`。
        *   创建一个 `UploadController`，提供 `POST /upload` 接口，接收 `MultipartFile` 类型的文件。
        *   在接口内部，调用OSS SDK将文件上传到你的Bucket中，并返回文件的公网访问URL。
    *   **前端:**
        *   在员工编辑/新增的表单中，使用 Element Plus 的 `El-Upload` 组件。
        *   将 `El-Upload` 的 `action` 属性设置为你的后端上传接口地址 `/upload`。
        *   监听 `on-success` 事件，当上传成功后，会接收到后端返回的文件URL，将其保存到你的员工表单数据中。

2.  **数据统计与图表展示 (ECharts):**
    *   **后端:**
        *   创建新的 `DashboardController` 或在 `EmployeeController` 中添加新的接口，用于提供统计数据。
        *   例如：`GET /employees/stats`，返回一个包含多组数据的JSON对象，如 `{ "genderRatio": [...], "deptDistribution": [...] }`。这些数据需要你用SQL的 `GROUP BY`, `COUNT` 等聚合函数查询出来。
    *   **前端:**
        *   安装 ECharts: `pnpm install echarts` 和 `pnpm install vue-echarts`。
        *   创建一个 `Dashboard.vue` 页面作为系统首页。
        *   在页面中引入 `v-chart` 组件，在 `onMounted` 中调用后端的统计接口。
        *   获取到数据后，根据ECharts的文档，构造 `option` 对象，并将其绑定到 `v-chart` 组件上，即可渲染出饼图、柱状图等。

---

### 阶段四：优化与部署 (项目上线)

1.  **Nginx反向代理:**
    *   在生产环境中，你不会再用后端的 `@CrossOrigin`。
    *   **前端打包:** 运行 `pnpm run build`，会在项目根目录生成一个 `dist` 文件夹，里面是纯静态文件。
    *   **配置Nginx:**
        *   让Nginx监听80端口。
        *   配置一个 `location /` 指向前端 `dist` 目录。
        *   配置一个 `location /api/`，使用 `proxy_pass` 将所有 `/api/` 开头的请求转发到你的后端服务地址（如 `http://localhost:8080`）。这样就巧妙地绕过了跨域问题。

2.  **Docker容器化部署:**
    *   **后端Dockerfile:** 编写一个 `Dockerfile`，基于一个Java环境镜像（如 `openjdk:17-slim`），将你的 `jar` 包复制进去并运行。
    *   **前端Dockerfile:** 编写一个 `Dockerfile`，基于 `nginx:alpine` 镜像，将前端的 `dist` 文件夹和你的 `nginx.conf` 配置文件复制进去。
    *   **Docker Compose:** 编写一个 `docker-compose.yml` 文件，定义你的 `backend` 服务和 `frontend` 服务，以及数据库服务（如MySQL）。这样你就可以用一条命令 `docker-compose up -d` 同时启动整个应用。

这个详细的计划涵盖了从开发到部署的整个生命周期，每一步都是企业级开发的标准实践。按照这个路线图走，你不仅能完成一个功能强大的项目，更能学到一整套现代化的全栈开发技能。祝你编码愉快，期待看到你的成果！
