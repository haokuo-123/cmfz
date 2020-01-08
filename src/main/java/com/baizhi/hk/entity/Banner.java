package com.baizhi.hk.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baizhi.hk.util.ImageConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {
    @Id
    @ExcelProperty(value = "编号")
    private String id;
    @ExcelProperty(value = "标题")
    private String title;
    @ExcelProperty(value = "图片",converter = ImageConverter.class)
    private String cover;
    @ExcelProperty(value = "创建日期")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @ExcelProperty(value = "描述")
    private String description;
    @ExcelProperty(value = "状态")
    private String status;
}
