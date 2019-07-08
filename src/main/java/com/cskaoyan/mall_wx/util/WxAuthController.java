package com.cskaoyan.mall_wx.util;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.cdy.WxUserService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cskaoyan.mall_wx.util.WxResponseCode.AUTH_INVALID_ACCOUNT;

/**
 * Created by little Stone
 * Date 2019/7/8 Time 20:55
 */
@RestController
@RequestMapping("wx")
public class WxAuthController {

	@Autowired
	WxUserService wxUserService;

	@RequestMapping("auth/login")
	@ResponseBody
	public ResponseVO login(@RequestBody Map<String, Object> data, HttpServletRequest request) {
		ResponseVO vo = new ResponseVO();
		String username = (String) data.get("username");
		String password = (String) data.get("password");

		if (username == null || password == null) {
			vo.setErrno(401);
			vo.setErrmsg("参数不对");
			return vo;
		}

		List<User> userList = wxUserService.queryByUsername(username);
		User user = null;
		if (userList.size() > 1) {
			vo.setErrno(502);
			vo.setErrmsg("系统内部错误");
			return vo;
		} else if (userList.size() == 0) {
			vo.setErrno(AUTH_INVALID_ACCOUNT);
			vo.setErrmsg("账号不存在");
			return vo;
		} else {
			user = userList.get(0);
		}

		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (/*!encoder.matches(password, user.getPassword())*/
		!password.equals(user.getPassword())) {
			vo.setErrno(AUTH_INVALID_ACCOUNT);
			vo.setErrmsg("账号密码不对");
			return vo;
		}

		// 更新登录情况
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(request.getLocalAddr());
		if (wxUserService.updateById(user) == 0) {
			vo.setErrno(505);
			vo.setErrmsg("更新数据失败");
			return vo;
		}

		// userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(username);
		userInfo.setAvatarUrl(user.getAvatar());

		UserToken userToken = UserTokenManager.generateToken(user.getId());

		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);
		vo.setErrno(0);
		vo.setErrmsg("成功");
		vo.setData(result);
		return vo;
	}

	@GetMapping("user/index")
	public Object list(HttpServletRequest request) {
		ResponseVO vo = new ResponseVO();
		//前端写了一个token放在请求头中
		//*************************
		//获得请求头
		String tokenKey = request.getHeader("X-Litemall-Token");
		Integer userId = UserTokenManager.getUserId(tokenKey);
		//通过请求头获得userId，进而可以获得一切关于user的信息
		//**************************
		if (userId == null) {
			vo.setErrno(-1);
			vo.setErrmsg("错误");
			return vo;
		}

		Map<Object, Object> data = new HashMap<Object, Object>();
		//***********************************
		//根据userId查询订单信息
		data.put("order", null);
		//***********************************

		vo.setErrno(0);
		vo.setErrmsg("成功");
		vo.setData(data);
		return vo;
	}
}
