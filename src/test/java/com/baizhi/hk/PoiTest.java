package com.baizhi.hk;

import com.alibaba.excel.EasyExcel;
import com.baizhi.hk.dao.BannerDao;
import com.baizhi.hk.dao.UserDao;
import com.baizhi.hk.entity.Banner;
import com.baizhi.hk.entity.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PoiTest {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    UserDao userDao;
    @Test
    public void t1() {
        List<UserDTO> list = userDao.queryUserBySex("男");
        for (UserDTO userDTO : list) {
            System.out.println(userDTO);
        }
        /*List<Banner> banners = bannerDao.selectAll();
        String fileName="E:/JAVA178班/JAVA178班/后期项目"+new Date().getTime()+".xlsx";
        EasyExcel.write(fileName,Banner.class).sheet("轮播图信息").doWrite(banners);*/
        //List<Banner> banners = bannerDao.selectAll();
      /*  HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hssfRow = sheet.createRow(0);
        String[] str = {"ID","标题","图片","创建时间","描述","状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            hssfRow.createCell(i).setCellValue(s);
        }
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        cellStyle.setDataFormat(format);
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(banner.getId());
            row.createCell(1).setCellValue(banner.getTitle());
            row.createCell(2).setCellValue(banner.getCover());
            HSSFCell cell = row.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreateDate());
            row.createCell(4).setCellValue(banner.getDescription());
            row.createCell(5).setCellValue(banner.getStatus());
            try {
                workbook.write(new File("E:\\JAVA178班\\JAVA178班\\后期项目"+new Date().getTime()+".xls"));
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


    }
}
