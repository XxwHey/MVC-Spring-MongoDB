package MongoDB.dao.impl;

import MongoDB.dao.IMusicDAO;
import MongoDB.model.Music;
import MongoDB.common.utils.MongoUtils.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by xiexw on 2017/8/9.
 */
@Repository
public class MusicDAOImpl extends BaseDAOImpl<Music, Serializable> implements IMusicDAO {

}
