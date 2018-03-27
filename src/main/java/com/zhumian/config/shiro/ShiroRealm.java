package com.zhumian.config.shiro;


import com.zhumian.entity.system.User;
import com.zhumian.service.system.RoleService;
import com.zhumian.service.system.UserService;
import com.zhumian.vo.req.system.user.QueryRolesOrRightsRequest;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.RoleResponse;
import com.zhumian.vo.res.system.right.RightResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        QueryRolesOrRightsRequest request = new QueryRolesOrRightsRequest();
        request.setAccount(user.getAccount());
        BaseResponse<List<RoleResponse>> roleResponse = userService.queryRolesByUser(request);
        List<RoleResponse> roles = roleResponse.getData();
        BaseResponse<List<RightResponse>> rightResponse = userService.queryRightsByUser(request);
        List<RightResponse> rights = rightResponse.getData();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        roles.stream().forEach(i -> simpleAuthorizationInfo.addRole(i.getRoleCode()));
        rights.stream().forEach(i -> simpleAuthorizationInfo.addStringPermission(i.getRightCode()));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String account = (String)token.getPrincipal();
        String password =  new String((char[])token.getCredentials());
        User userSearcher = new User();
        userSearcher.setAccount(account);
        User user = userService.getOne(userSearcher);
        if(user == null){
            throw new UnknownAccountException();
        }
        if(!Objects.equals(password,user.getPassword())){
            throw new IncorrectCredentialsException();
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user",user);

        return new SimpleAuthenticationInfo(account, password, getName());
    }


}
