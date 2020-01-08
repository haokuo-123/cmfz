package com.baizhi.hk.controller;
import com.baizhi.hk.dao.ChapterDao;
import com.baizhi.hk.entity.Chapter;
import com.baizhi.hk.service.ChapterService;
import com.baizhi.hk.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterDao chapterDao;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer rows,Integer page,String albumId){
        Map<String,Object> hashMap = new HashMap<>();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        int i = chapterDao.selectCount(chapter);
        Integer total = i%rows==0?i/rows:i/rows+1;
        List<Chapter> chapterDaos = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",i);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",chapterDaos);
        return hashMap;

    }
    @RequestMapping("editChapter")
    public Map<String,Object> editChapter(String oper, Chapter chapter,String albumId,String[] id){
        Map<String,Object> map = new HashMap<>();
        // 添加逻辑
        if (oper.equals("add")) {
            chapter.setAlbumId(albumId);
            String ids = chapterService.add(chapter);
            map.put("chapterId", ids);
            // 修改逻辑
        } else if (oper.equals("edit")) {
         chapterService.edit(chapter);
            map.put("chapterId", chapter.getId());
            // 删除
        } else {
            chapterDao.deleteByIdList(Arrays.asList(id));
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile url, String chapterId, HttpServletRequest request, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = session.getServletContext().getRealPath("/chapter/music");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/chapter/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        //计算音频时长
        String[] split=http.split("/");
        String name = split[split.length - 1];
        AudioFile read = AudioFileIO.read(new File(realPath,name));
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60+"秒";
        chapter.setTime(time);
        chapterService.edit(chapter);
        Map<String,Object> map = new HashMap<>();
        map.put("status",200);
        return map;
    }
    @RequestMapping("download")
    public void download(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/chapter/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
        // FileUtils.copyFile("服务器文件",outputStream)
        //FileUtils.copyFile();
    }

}
