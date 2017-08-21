package MongoDB.dao;

import MongoDB.model.User;
import MongoDB.common.utils.MongoUtils.IBaseDAO;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by xiexw on 2017/8/17.
 */
@Transactional
public interface IUserDAO extends IBaseDAO<User, Serializable> {

}
