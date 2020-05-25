package com.loststars.tmallboot.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.loststars.tmallboot.pojo.Role;
import com.loststars.tmallboot.pojo.User;
import com.loststars.tmallboot.service.UserService;

public class DatabaseRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String name = (String) principals.getPrimaryPrincipal();
        User user = userService.getUser(name);
        Set<String> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        s.setRoles(roles);
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String name = t.getUsername();
        User user = userService.getUser(name);
        if (user == null) throw new AuthenticationException();
        String passwordDB = user.getPassword();
        if (passwordDB == null) throw new AuthenticationException();
        return new SimpleAuthenticationInfo(name, passwordDB, ByteSource.Util.bytes(user.getSalt()), getName());
    }

}
