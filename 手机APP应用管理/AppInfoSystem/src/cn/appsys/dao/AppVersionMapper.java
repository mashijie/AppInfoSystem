package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	//根据AppId查看版本信息
	public List<AppVersion> getAppVersionList(@Param("AppId") Integer AppId);
	//添加版本信息
	public Integer addVersion(AppVersion appVersion);
	
	
}
