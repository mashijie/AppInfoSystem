package cn.appsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.AppVersionService;

@Service
public class AppVersionServiceImpl implements AppVersionService{
	@Autowired
	private AppVersionMapper appVersionMapper;
	
	@Override
	public List<AppVersion> getAppVersionList(Integer appId) {
		return appVersionMapper.getAppVersionList(appId);
	}

	@Override
	public Integer addVersion(AppVersion appVersion) {
		return appVersionMapper.addVersion(appVersion);
	}

}
