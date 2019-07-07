package com.cskaoyan.shiro;

import com.cskaoyan.bean.sys.Admin;
import com.cskaoyan.service.sys.AdminService;
import com.cskaoyan.service.sys.PermissionService;
import com.cskaoyan.service.sys.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component("mallrealm")
public class MallRealm extends AuthorizingRealm {
	@Autowired
	AdminService adminService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	RoleService roleService;

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//关注凭证（密码）
		//执行验证的用户名
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());

		//去查询数据库中的密码
		if (StringUtils.isEmpty(username)) {
			throw new AccountException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountException("密码不能为空");
		}
		Admin admin = adminService.selectByName(username);
		if (admin==null) {
			throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
		}
//		String passwordFromDb = admin.getPassword();

		//解密，判断两个密码是否匹配
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(password, passwordFromDb)) {
//			throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
//		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, password, "mallrealm");
		return info;
	}

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		if(principalCollection.isEmpty()||principalCollection==null){
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		Admin admin = (Admin) principalCollection.getPrimaryPrincipal();


		//从数据库中取出 当前用户的授权信息（可以查询role，也可以查询permission）
		int[] roleIds = admin.getRoleIds();
		List<String> roles=roleService.queryByIds(roleIds); //注意得到的是role"名字"的集合

		//获得所有详细权限的list
		List<String> permissions = new ArrayList<>();
		for (int i = 0; i <roleIds.length ; i++) {
			List<String> selectPermissions = permissionService.selectPermissions(roleIds[i]);
			permissions.addAll(selectPermissions);
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
		info.addStringPermissions(permissions);
		return info;
	}

}
