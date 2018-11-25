package cn.appsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;

@Service
public class DevUserServiceImpl implements DevUserService{

	@Autowired
	private DevUserMapper devUserMapper;
	
	@Override
	public DevUser userLogin(String userCode){
		DevUser devUser=devUserMapper.userLogin(userCode);
		return devUser;
	}
}







