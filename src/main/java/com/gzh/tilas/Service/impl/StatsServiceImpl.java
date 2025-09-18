package com.gzh.tilas.Service.impl;


import com.gzh.tilas.Mapper.EmployeeMapper;
import com.gzh.tilas.Service.StatsService;
import com.gzh.tilas.pojo.AvgSalaryDTO;
import com.gzh.tilas.pojo.GenderCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<GenderCountDTO> getGenderCount() {
        return employeeMapper.countByGender();
    }
    @Override
    public List<AvgSalaryDTO> getAvgSalaryByJob() {
        return employeeMapper.countAvgSalaryByJob();
    }
    @Override
    public List<AvgSalaryDTO> getAvgSalaryByDept() {
        return employeeMapper.countAvgSalaryByDept();
    }
}
