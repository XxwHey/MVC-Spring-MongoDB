package MongoDB.dao;

import MongoDB.model.Music;
import MongoDB.common.utils.MongoUtils.IBaseDAO;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by xiexw on 2017/8/9.
 */
@Transactional
public interface IMusicDAO extends IBaseDAO<Music, Serializable> {

}
