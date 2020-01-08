package com.baizhi.hk.controller;

import com.baizhi.hk.dao.*;
import com.baizhi.hk.entity.*;
import com.baizhi.hk.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("web")
public class InterfaceController {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    CounterDao counterDao;
    @Autowired
    UserDao userDao;
    @Autowired
    GuruDao guruDao;
    @Autowired
    UgDao ugDao;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        HashMap hashMap = new HashMap();
        try {
            String s = UUID.randomUUID().toString();
            String code = s.substring(0, 4);
            SmsUtil.send(phone,code);
            // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
            stringRedisTemplate.opsForValue().set("code",code,60, TimeUnit.SECONDS);
            hashMap.put("status","200");
            hashMap.put("message","短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status",-200);
            hashMap.put("message",e.getMessage());
        }
        return hashMap;
    }
    @RequestMapping("addCode")
    public Map addCode(String code){
        HashMap map = new HashMap();
        String code1 = stringRedisTemplate.opsForValue().get("code");
        if(code.equals(code1)){
            map.put("status",200);
            map.put("message","验证码正确");
        }else {
            map.put("status",-200);
            map.put("message","验证码已过期，或验证码错误");
        }
        return map;
    }
    @RequestMapping("addUser")
    public Map addUser(String password,String phone,String name,String nickName,String sex,String sign,String location ){
        HashMap map = new HashMap();
        try {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setPhone(phone);
            user.setPassword(password);
            user.setName(name);
            user.setNickName(nickName);
            user.setSex(sex);
            user.setLocation(location);
            userDao.insertSelective(user);
            User user2 = userDao.selectByPrimaryKey(user.getId());
            map.put("status",200);
            map.put("user",user2);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("selectOneArticle")
    public Map selectOneArticle(String uid,String id){
        HashMap map = new HashMap();
        try {
            Article article = articleDao.selectByPrimaryKey(id);
            map.put("status",200);
            map.put("article",article);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;

    }
    @RequestMapping("selectOneAlbum")
    public Map selectOneAlbum(String albumId,String id){
        HashMap map = new HashMap();
        try {
            Album album = albumDao.selectByPrimaryKey(id);
            Chapter chapter = new Chapter();
            chapter.setAlbumId(albumId);
            Chapter chapter1 = chapterDao.selectOne(chapter);
            map.put("status",200);
            map.put("album",album);
            map.put("cover",chapter1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("selectAllCourse")
    public Map selectAllCourse(String uid){
        HashMap map = new HashMap();
        try {
            Course course = new Course();
            course.setUserId(uid);
            List<Course> courses = courseDao.select(course);
            map.put("option",courses);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("addCourse")
    public Map addCourse(String uid,String title){
        HashMap map = new HashMap();
        try {
            Course course = new Course();
            course.setId(UUID.randomUUID().toString());
            course.setTitle(title);
            course.setUserId(uid);
            courseDao.insertSelective(course);
            Course c = new Course();
            c.setUserId(uid);
            List<Course> courses = courseDao.select(c);
            map.put("option",courses);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;

    }
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String uid,String id){
        HashMap map = new HashMap();
        try {
            courseDao.deleteByPrimaryKey(id);
            Course course = new Course();
            course.setUserId(uid);
            List<Course> courses = courseDao.select(course);
            map.put("option",courses);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;

    }
    @RequestMapping("selectAllCounter")
    public Map selectAllCounter(String uid,String id){
        HashMap map = new HashMap();
        try {
            Counter counter = new Counter();
            counter.setUserId(uid);
            counter.setCourseId(id);
            List<Counter> counters = counterDao.select(counter);
            map.put("counters",counters);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("addCounter")
    public Map addCounter(String uid,String title){
        HashMap map = new HashMap();
        try {
            Counter counter = new Counter();
            counter.setId(UUID.randomUUID().toString());
            counter.setUserId(uid);            counter.setTitle(title);
            counterDao.insertSelective(counter);
            Counter counter1 = new Counter();
            counter1.setUserId(uid);
            List<Counter> counters = counterDao.select(counter1);
            map.put("counters",counters);
            map.put("status",200);
        } catch (Exception e) {
            map.put("status",-200);
            map.put("message","error");
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("deleteCounter")
    public Map deleteCounter(String uid,String id){
        HashMap map = new HashMap();
        try {
            counterDao.deleteByPrimaryKey(id);
            Counter counter = new Counter();
            counter.setUserId(uid);
            List<Counter> counters = counterDao.select(counter);
            map.put("counters",counters);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("updateCounter")
    public Map updateCounter(String uid,String id,Integer count){
        HashMap map = new HashMap();
        try {
            Counter counter = new Counter();
            counter.setCount(count);
            counter.setId(id);
            counterDao.updateByPrimaryKeySelective(counter);
            Counter counter1 = new Counter();
            counter1.setUserId(uid);
            List<Counter> counters = counterDao.select(counter1);
            map.put("counters",counters);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("updateUser")
    public Map updateUser(String uid,String sex,String photo,String location,String sign,String nickName,String password){
        HashMap map = new HashMap();
        try {
            User user = new User();
            user.setPassword(password);
            user.setId(uid);
            userDao.updateByPrimaryKeySelective(user);
            User user1 = new User();
            user1.setId(uid);
            List<User> users = userDao.select(user1);
            map.put("user",users);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    @RequestMapping("selectUser")
    public Map selectUser(){
        HashMap map = new HashMap();
        try {
            List<User> users = userDao.queryUserRand();
            map.put("user",users);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    @RequestMapping("selectAllGuru")
    public Map selectAllGuru(){
        HashMap map = new HashMap();
        try {
            List<Guru> list = guruDao.selectAll();
            map.put("list",list);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    @RequestMapping("addGuru")
    public Map addGuru(String uid,String id){
        HashMap map = new HashMap();
        try {
            Ug ug = new Ug();
            ug.setId(UUID.randomUUID().toString());
            ug.setUserId(uid);
            ug.setGuruId(id);
            ugDao.insert(ug);

            Ug ug1 = new Ug();
            ug1.setUserId(uid);
            List<Guru> gurus = new ArrayList<>();
            List<Ug> list = ugDao.select(ug1);
            for (Ug ug2 : list) {
                Guru guru = guruDao.selectByPrimaryKey(ug2.getGuruId());
                gurus.add(guru);
            }
            map.put("list",gurus);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
            return map;
    }


}
