package MongoDB.service.impl;

import MongoDB.dao.IMusicDAO;
import MongoDB.model.Music;
import MongoDB.service.ICXFService;
import MongoDB.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * Created by xiexw on 2017/8/15.
 */
@Service("CXFService")
@WebService(endpointInterface = "MongoDB.service.ICXFService", serviceName = "ICXFService")
public class CXFServiceImpl implements ICXFService {

    @Autowired
    IMusicDAO musicDAO;

    @Override
    public String sayHi(String text) {
        return "CXF:" + text;
    }

    @Override
    public Music getMusic(Integer id) {
        return musicDAO.get(id);
    }
}
