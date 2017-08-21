package MongoDB.service;

import MongoDB.model.Music;

import javax.jws.WebService;

/**
 * Created by xiexw on 2017/8/15.
 */
@WebService
public interface ICXFService {

    String sayHi(String text);

    Music getMusic(Integer id);

}
