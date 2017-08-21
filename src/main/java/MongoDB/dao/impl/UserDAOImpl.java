package MongoDB.dao.impl;

import MongoDB.dao.IUserDAO;
import MongoDB.model.User;
import MongoDB.common.utils.MongoUtils.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by xiexw on 2017/8/17.
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Serializable> implements IUserDAO {

}
