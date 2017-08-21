package MongoDB.controller;

import MongoDB.model.Music;
import MongoDB.service.ICXFService;
import MongoDB.service.IMusicService;
import MongoDB.common.utils.ServletUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xiexw on 2017/8/9.
 * 测试controller
 */
@Controller
public class MusicController {

//    @AutowireService
    @Autowired
    private IMusicService musicService;

    @Autowired
    private ICXFService cxfService;

    @RequestMapping(value = "getMusic")
    public void getMusic(HttpServletRequest request, HttpServletResponse response, Integer id) {
        Music music = musicService.getById(id);
        if (music == null) {
            ServletUtils.setWrite(response, "该歌曲不存在");
        } else {
            ServletUtils.setWrite(response, new JSONObject(music));
        }
    }

    @RequestMapping(value = "save")
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Music music = new Music(null, "你还要我怎样", "", "意外", "", 2);
        Integer result = musicService.save(music);
        ServletUtils.setWrite(response, result);
    }

    @RequestMapping(value = "list")
    public void listAll(HttpServletRequest request, HttpServletResponse response, Integer id, String name) {
        List<Music> musics = musicService.getAll();
        ServletUtils.setWrite(response, musics);
    }

    @RequestMapping(value = "hello")
    public String hello(HttpServletRequest request, HttpServletResponse response) {
        return "helloworld.html";
    }

    @RequestMapping(value = "cxftest")
    public void cxftest(HttpServletRequest request, HttpServletResponse response, Integer id) {
        //cxf
//        JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
//        svr.setServiceClass(ICXFService.class);
//        svr.setAddress("http://localhost:8080/cxf");
//        ICXFService cxf = (ICXFService) svr.create();
        ServletUtils.setWrite(response, new JSONObject(cxfService.getMusic(id)));
    }

}