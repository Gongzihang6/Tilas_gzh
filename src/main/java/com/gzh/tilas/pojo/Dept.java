package com.gzh.tilas.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
