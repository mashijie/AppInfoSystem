package cn.appsys.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import cn.appsys.service.impl.DevUserServiceImpl;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("dev")
public class DevController {

	@Resource
	DevUserService devUserService;
	

	@RequestMapping("login")
	public String devLogin() {
		System.out.println("---进入APP开发者平台登录页面---");
		return "devlogin";
	}

	/**
	 * 登录功能
	 * 
	 * @param devCode
	 * @param devPassword
	 * @param model
	 * @return
	 */
	@RequestMapping("dologin")
	public String dologin(@RequestParam(value = "devCode") String devCode,
			@RequestParam(value = "devPassword") String devPassword,
			HttpServletRequest request) {
		DevUser devUser = devUserService.userLogin(devCode);
		if (devUser != null && devUser.getDevPassword().equals(devPassword)) {
			request.getSession().setAttribute(Constants.DEV_USER_SESSION,
					devUser);
			return "developer/main";
		}
		return "devlogin";
	}

	/**
	 * 注销功能
	 * 
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.DEV_USER_SESSION);
		return "devlogin";
	}

	

}
