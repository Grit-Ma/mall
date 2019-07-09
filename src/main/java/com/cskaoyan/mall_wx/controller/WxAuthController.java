package com.cskaoyan.mall_wx.controller;

import com.cskaoyan.bean.User;
import com.cskaoyan.bean.UserInfo;
import com.cskaoyan.bean.UserToken;
import com.cskaoyan.bean.vo.ResponseVO;
import com.cskaoyan.mall_wx.service.cdy.WxUserService;
import com.cskaoyan.mall_wx.util.UserTokenManager;
import com.cskaoyan.tool.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

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
			return vo.errParm(vo);
		}

		List<User> userList = wxUserService.queryByUsername(username);
		User user = null;
		if (userList.size() > 1) {
			vo.setErrno(502);
			vo.setErrmsg("系统内部错误");
			return vo;
		} else if (userList.size() == 0) {
			vo.setErrno(700);
			vo.setErrmsg("账号不存在");
			return vo;
		} else {
			user = userList.get(0);
		}

		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (/*!encoder.matches(password, user.getPassword())*/
		!password.equals(user.getPassword())) {
			vo.setErrno(700);
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

		Map<Object, Object> result = getResult(user);
		vo.setData(result);
		return vo.ok(vo);
	}

	@PostMapping("auth/logout")
	public ResponseVO logout(HttpServletRequest request) {
		String tokenKey = request.getHeader("X-Litemall-Token");
		Integer userId = UserTokenManager.getUserId(tokenKey);
		ResponseVO vo = new ResponseVO();
		if (userId == null) {
			vo.unlogin(vo);
		}
		return vo.ok(vo);
	}

	@PostMapping("auth/register")
	public ResponseVO register(@RequestBody Map<String, Object> body, HttpServletRequest request) {
		ResponseVO vo = new ResponseVO();

		String username = (String) body.get("username");
		String password = (String) body.get("password");
		String mobile = (String) body.get("mobile");
		String code = (String) body.get("code");
		String wxCode = (String) body.get("wxCode");


		if (isEmpty(username) || isEmpty(password) || isEmpty(mobile)
				|| isEmpty(wxCode) || isEmpty(code)) {
			return vo.errParm(vo);
		}

		List<User> userList = wxUserService.queryByUsername(username);
		if (userList.size() > 0) {
			vo.setErrno(704);
			vo.setErrmsg("用户名已注册");
			return vo;
		}

		userList = wxUserService.queryByMobile(mobile);
		if (userList.size() > 0) {
			vo.setErrno(705);
			vo.setErrmsg("手机号已注册");
			return vo;
		}
		if (!RegexUtil.isMobileExact(mobile)) {
			vo.setErrno(707);
			vo.setErrmsg("手机号格式不正确");
			return vo;
		}

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setMobile(mobile);
		user.setWeixinOpenid("");
		user.setAvatar("");
		user.setNickname(username);
		user.setGender((byte) 0);
		user.setUserLevel((byte) 0);
		user.setStatus((byte) 0);
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(request.getLocalAddr());
		user.setDeleted(false);
		wxUserService.add(user);
		Map<Object, Object> result = getResult(user);

		vo.setData(result);
		return vo.ok(vo);
	}

	private Map<Object, Object> getResult(User user) {
		// userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(user.getUsername());
		userInfo.setAvatarUrl(user.getAvatar());

		UserToken userToken = UserTokenManager.generateToken(user.getId());

		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);
		return result;
	}


	@PostMapping("auth/reset")
	public ResponseVO reset(@RequestBody Map<String, Object> body, HttpServletRequest request) {
		ResponseVO vo = new ResponseVO();
		/*if(userId == null){
			return ResponseUtil.unlogin();
		}*/
		String password = (String) body.get("password");
		String mobile = (String) body.get("mobile");

		if (mobile == null || password == null) {
			return vo.errParm(vo);
		}

		List<User> userList = wxUserService.queryByMobile(mobile);
		User user = null;
		if (userList.size() > 1) {
			vo.setErrno(502);
			vo.setErrmsg("系统内部错误");
			return vo;
		} else if (userList.size() == 0) {
			vo.setErrno(706);
			vo.setErrmsg("手机号未注册");
			return vo;
		} else {
			user = userList.get(0);
		}

		user.setPassword(password);

		if (wxUserService.updateById(user) == 0) {
			vo.setErrno(505);
			vo.setErrmsg("更新数据失败");
			return vo;
		}

		return vo.ok(vo);

	}
}
