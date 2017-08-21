package MongoDB.service.impl;

import MongoDB.common.utils.CommonUtils;
import MongoDB.dao.IUserDAO;
import MongoDB.dto.UserDTO;
import MongoDB.model.User;
import MongoDB.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiexw on 2017/8/17.
 */
@Service("UserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public int save(UserDTO userDTO) {
        User user = new User();
        CommonUtils.copyValue(userDTO, user);
        userDAO.save(user);
        return user.getId();
    }
}
