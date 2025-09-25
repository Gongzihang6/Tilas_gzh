// aspect/LogAspect.java
package com.gzh.tilas.aspect;

import com.alibaba.fastjson.JSONObject;
import com.gzh.tilas.Mapper.OperationLogMapper;
import com.gzh.tilas.pojo.OperationLog;
import com.gzh.tilas.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect // 声明为切面类
@Component
public class LogAspect {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 定义切点，这里我们切入所有Controller层的方法
     * 你也可以切入自定义的 @Log 注解
     * @Around("execution(* com.gzh.tilas.controller.*.*(..))")
     * @Around("@annotation(com.gzh.tilas.annotation.Log)") // 切入有@Log注解的方法
     */
    @Around("@annotation(com.gzh.tilas.annotation.OperationLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. 获取基本信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 解析JWT获取操作人信息
        String jwt = request.getHeader("Authorization");
        Integer operatorId = null;
        String operatorName = null;
        if (jwt != null && jwt.startsWith("Bearer ")) {
            Claims claims = jwtUtil.parseToken(jwt.substring(7));
            operatorId = claims.get("id", Integer.class);
            operatorName = claims.get("username", String.class);
        }

        LocalDateTime operationTime = LocalDateTime.now();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String operatorIp = request.getRemoteAddr();

        // 2. 记录方法开始执行时间
        long startTime = System.currentTimeMillis();

        Object result = null;
        String errorMsg = null;
        log.info("AOP开始记录操作日志: Id为{}的用户{}执行了操作{}", operatorId, operatorName, className + '.' + methodName);
        try {
            // 3. 执行原始方法
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // 捕获异常
            errorMsg = throwable.getMessage();
            throw throwable; // 必须重新抛出，否则全局异常处理器捕获不到
        } finally {
            // 4. 计算方法耗时
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;

            // 5. 封装日志对象
            OperationLog operationLog = new OperationLog();
            operationLog.setOperatorId(operatorId);
            operationLog.setOperatorName(operatorName);
            operationLog.setOperationTime(operationTime);
            operationLog.setClassName(className);
            operationLog.setMethodName(methodName);
            operationLog.setOperatorIp(operatorIp);
            operationLog.setCostTime(costTime);

            // 获取参数并转为JSON
            Object[] args = joinPoint.getArgs();
            String methodParams = Arrays.toString(args); // 简单的toString，更复杂的需要过滤掉HttpServletRequest等对象
            operationLog.setMethodParams(JSONObject.toJSONString(methodParams));

            if (errorMsg != null) {
                operationLog.setErrorMsg(errorMsg);
            } else if (result != null) {
                // 获取返回值并转为JSON
                operationLog.setReturnValue(JSONObject.toJSONString(result));
            }

            // 6. 插入数据库
            operationLogMapper.insert(operationLog);
            log.info("AOP记录操作日志: {}", operationLog);
        }

        return result;
    }
}