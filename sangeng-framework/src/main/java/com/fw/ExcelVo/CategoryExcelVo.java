package com.fw.ExcelVo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExcelVo {

    @ExcelIgnore
    private Long id;

    @ExcelProperty("分类名称")
    private String name;

    @ExcelProperty("分类描述")
    private String description;

    @ExcelProperty("分类状态0:正常,1禁用")
    private String status;
}
