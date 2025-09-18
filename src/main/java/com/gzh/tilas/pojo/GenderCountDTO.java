package com.gzh.tilas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderCountDTO {
    private Integer gender;     // 1 表示男性，2 表示女性
    private Integer count;
}
