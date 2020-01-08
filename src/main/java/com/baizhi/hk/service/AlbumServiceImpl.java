package com.baizhi.hk.service;

import com.baizhi.hk.aspect.Log;
import com.baizhi.hk.dao.AlbumDao;
import com.baizhi.hk.entity.Album;
import com.baizhi.hk.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        int count = albumDao.selectCount(album);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",albums);
        map.put("total",count%rows == 0?count / rows : count / rows+1);
        map.put("record",count);
        return map;
    }

    @Override
    @Log(name = "专辑的添加")
    public String add(Album album) {
       album.setId(UUID.randomUUID().toString());
       album.setCreateDate(new Date());
        int i = albumDao.insert(album);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return album.getId();
    }

    @Override
    @Log(name = "专辑的修改")
    public void edit(Album album) {
        if ("".equals(album.getCover())) {
            album.setCover(null);
        }
        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("修改失败");
        }
    }

    @Override
    public Album findOne(String id) {
        return null;
    }

    @Override
    @Log(name = "专辑的删除")
    public void del(String id, HttpServletRequest request) {
        Album album = albumDao.selectByPrimaryKey(id);
        int i = albumDao.deleteByPrimaryKey(id);
        if(i == 0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = album.getCover();
            File file = new File(request.getServletContext().getRealPath("/album/image/"), cover);
            boolean b = file.delete();
            if(b == false){
                throw new RuntimeException("删除文件失败");
            }
        }
    }

}
