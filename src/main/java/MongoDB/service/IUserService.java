package MongoDB.service;

import MongoDB.dto.UserDTO;

/**
 * Created by xiexw on 2017/8/17.
 */
public interface IUserService {

    int save(UserDTO userDTO);

}
