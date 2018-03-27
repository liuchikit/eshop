package com.zhumian.controller.system;

import com.zhumian.entity.system.User;
import com.zhumian.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/toUserList")
    public ModelAndView toUserList(){
        return new ModelAndView("/static/jsp/user/userList");
    }

    @RequestMapping(value = "/getById")
    public User getById(Long id){
        return userService.getById(id);
    }
}
