package com.gzh.tilas.Service;

import com.gzh.tilas.pojo.AvgSalaryDTO;
import com.gzh.tilas.pojo.GenderCountDTO;

import java.util.List;

// service/StatsService.java
public interface StatsService {
    List<GenderCountDTO> getGenderCount();
    List<AvgSalaryDTO> getAvgSalaryByJob();
    List<AvgSalaryDTO> getAvgSalaryByDept();
}
