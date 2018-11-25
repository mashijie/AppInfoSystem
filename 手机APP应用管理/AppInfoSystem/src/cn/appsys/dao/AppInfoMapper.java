package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	public List<AppInfo> getAppInfo(@Param("flatformName") String flatformName,
						@Param("status") Integer status,
						@Param("flatformId") Integer flatformId,
						@Param("categoryLevel1") Integer queryCategoryLevel1,
						@Param("categoryLevel2") Integer queryCategoryLevel2,
						@Param("categoryLevel3") Integer queryCategoryLevel3,
						@Param("index") Integer pageIndex);
	public List<AppInfo> getAppInfoCount(@Param("flatformName") String flatformName,
			@Param("status") Integer status,
			@Param("flatformId") Integer flatformId,
			@Param("categoryLevel1") Integer queryCategoryLevel1,
			@Param("categoryLevel2") Integer queryCategoryLevel2,
			@Param("categoryLevel3") Integer queryCategoryLevel3);
	
	public Integer addAppInfo(AppInfo appInfo);
	
	//上架App
	public Integer upStatus(@Param("APKName")String APKName);
	
	
	
	
	
}
