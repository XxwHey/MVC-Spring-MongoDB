package MongoDB.controller;

import MongoDB.common.annotation.ModelDTO;
import MongoDB.dto.UserDTO;
import MongoDB.model.User;
import MongoDB.service.IUserService;
import MongoDB.common.utils.ServletUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiexw on 2017/8/10.
 */
@Controller
public class UserController {

    @Autowired
    public IUserService userService;

    @RequestMapping(value = "getUser")
    public void getUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("getUser");
    }

    @RequestMapping(value = "save")
    public void save(HttpServletRequest request, HttpServletResponse response, @ModelDTO UserDTO userDTO) {
        Integer result = userService.save(userDTO);
        JSONObject object = new JSONObject();
        object.put("save", "保存ID为：" + result);
        ServletUtils.setWrite(response, object);
    }

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login.html";
    }

    @RequestMapping(value = "submit")
    public String submit(HttpServletRequest request, HttpServletResponse response, @ModelDTO UserDTO userDTO) {
        System.out.println(new JSONObject(userDTO));
        return "helloworld.html";
    }
}
