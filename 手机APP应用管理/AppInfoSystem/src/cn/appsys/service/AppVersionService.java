package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	public List<AppVersion> getAppVersionList (Integer appId);
	
	public Integer addVersion(AppVersion appVersion);
}
