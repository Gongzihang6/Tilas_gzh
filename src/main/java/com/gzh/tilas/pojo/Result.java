package com.gzh.tilas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;   // 响应码, 1 代表成功; 0 代表失败
    private String msg;     // 响应信息
    private Object data;    // 返回的数据

    // 静态方法，用于成功响应（包含数据）
    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    // 静态方法，用于成功响应（不包含数据）
    public static Result success() {
        return new Result(1, "success", null);
    }

    // 静态方法，用于错误响应
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }
}