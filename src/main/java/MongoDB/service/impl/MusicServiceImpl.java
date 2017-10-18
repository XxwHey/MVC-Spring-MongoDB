package MongoDB.service.impl;

import MongoDB.dao.IMusicDAO;
import MongoDB.model.Music;
import MongoDB.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiexw on 2017/8/9.
 */
@Service("MusicService")
public class MusicServiceImpl implements IMusicService {

    @Autowired
    IMusicDAO musicDAO;

    @Override
    public Music getById(Integer id) {
        return musicDAO.get(id);
    }

    @Override
    public int save(Music music) {
        musicDAO.save(music);
        return music.getId();
    }

    @Override
    public List<Music> getAll() {
        return musicDAO.listAll();
    }

    @Override
    public void sayHello() {
        System.out.println("hello world!");
    }
}
