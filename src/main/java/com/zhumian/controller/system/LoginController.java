package com.zhumian.controller.system;

import com.zhumian.entity.system.User;
import com.zhumian.service.system.UserService;
import com.zhumian.vo.req.system.LoginRequest;
import com.zhumian.vo.res.BaseResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/index")
    public ModelAndView index(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/home")
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/login")
    public BaseResponse login(LoginRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(request.getAccount(),request.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            token.setRememberMe(false);
            currentUser.login(token);
        }
        return new BaseResponse();
    }


}
