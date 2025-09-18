package com.gzh.tilas.Controller;

import com.gzh.tilas.Service.StatsService;
import com.gzh.tilas.pojo.AvgSalaryDTO;
import com.gzh.tilas.pojo.GenderCountDTO;
import com.gzh.tilas.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;

    @GetMapping("/gender")
    public Result getGenderCount() {
        List<GenderCountDTO> data = statsService.getGenderCount();
        return Result.success(data);
    }

    @GetMapping("/avgSalaryByjob")
    public Result getAvgSalaryByJob() {
        List<AvgSalaryDTO> data = statsService.getAvgSalaryByJob();
        return Result.success(data);
    }

    @GetMapping("/avgSalaryBydept")
    public Result getAvgSalaryByDept() {
        List<AvgSalaryDTO> data = statsService.getAvgSalaryByDept();
        return Result.success(data);
    }
}
