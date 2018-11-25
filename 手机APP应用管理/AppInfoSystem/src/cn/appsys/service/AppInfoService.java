package cn.appsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	List<AppInfo> getAppInFolist(String flatformName, Integer status,
			Integer flatformId, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer pageIndex);
	
	
	
	 List<AppInfo> getAppInFolistCount(String flatformName, Integer status,
				Integer flatformId, Integer categoryLevel1, Integer categoryLevel2,
				Integer categoryLevel3);
	
	 Integer addAppInfo(AppInfo appInfo);
	 
	 public Integer upStatus(String APKName);
	
	
}
