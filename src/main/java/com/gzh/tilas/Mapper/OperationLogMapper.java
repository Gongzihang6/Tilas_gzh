package com.gzh.tilas.Mapper;

import com.gzh.tilas.pojo.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper {

    /**
     * 插入操作日志
     * @param operationLog
     */
    @Insert("insert into operation_log (" +
            "operator_id, operator_name, operation_time, class_name, method_name, method_params,operator_ip, " +
            "cost_time, return_value, error_msg, operation_desc) " +
            "values (#{operatorId}, #{operatorName}, #{operationTime}, #{className}, #{methodName}, #{methodParams}, #{operatorIp}," +
            " #{costTime}, #{returnValue}, #{errorMsg}, #{operationDesc})")
    void insert(OperationLog operationLog);
}

