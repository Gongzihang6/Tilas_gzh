// exception/GlobalExceptionHandler.java
package com.gzh.tilas.exception;

import com.gzh.tilas.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理数据库唯一约束异常
     * 例如：新增员工时，用户名已存在
     */
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, DuplicateKeyException.class})
    public Result handleDbUniqueException(Exception e) {
        log.error("数据库唯一约束异常: {}", e.getMessage());

        // 从异常信息中提取有用的部分
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String entry = split[2]; // 提取 'username_value'
            String key = split[4];   // 提取 'for key 'emp.username_unique''

            String msg = entry + " 已存在, 请勿重复添加!";
            return Result.error(msg);
        }

        return Result.error("数据库操作失败, 违反唯一约束");
    }

    /**
     * 处理 @Validated 校验失败的异常 (用于 @RequestBody)
     * 例如：新增部门时，部门名称为空
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        log.error("数据校验异常: {}", e.getMessage());

        // 从异常中获取第一个校验失败的错误信息
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return Result.error(errorMessage);
    }

    /**
     * 创建一个自定义业务异常类
     * 用于在Service层主动抛出可预期的业务错误，自定义设置错误信息
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }


    /**
     * 兜底异常处理：捕获所有其他未处理的异常
     */
    // @ExceptionHandler(Exception.class)
    // public Result handleException(Exception e) {
    //     log.error("发生未知异常!", e);
    //     return Result.error("系统繁忙, 请稍后重试");
    // }
}