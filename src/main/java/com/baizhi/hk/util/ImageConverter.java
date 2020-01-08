package com.baizhi.hk.util;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String property = System.getProperty("user.dir");
        String[] split = value.split("/");
        value = split[split.length-1];
        String cover = property+"\\src\\main\\webapp\\banner\\image\\"+value;
        return new CellData(FileUtils.readFileToByteArray(new File(cover)));
    }
}
