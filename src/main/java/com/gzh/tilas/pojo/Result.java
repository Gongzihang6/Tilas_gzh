package com.gzh.tilas.pojo;

import lombok.Data;

@Data
public class Result {
    private Integer code;   // 1-成功 0-失败
    private String msg;     // 提示信息
    private Object data;    // 数据

    public static Result success(Object data) {
        Result r = new Result();
        r.setCode(1);
        r.setMsg("恭喜你！操作成功");
        r.setData(data);
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setCode(0);
        r.setMsg(msg);
        return r;
    }
}
