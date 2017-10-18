package MongoDB.service;

import MongoDB.model.Music;

import java.util.List;

/**
 * Created by xiexw on 2017/8/9.
 */
public interface IMusicService {

    Music getById(Integer id);

    int save(Music music);

    List<Music> getAll();

    void sayHello();

}
