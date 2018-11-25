package cn.appsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.AppInfoService;

@Service
public class AppInfoServiceImpl implements AppInfoService {

	@Autowired
	private AppInfoMapper appInfoMapper;

	// 获取数据
	@Override
	public List<AppInfo> getAppInFolist(String flatformName, Integer status,
			Integer flatformId, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer pageIndex) {
		return appInfoMapper.getAppInfo(flatformName, status, flatformId,
				categoryLevel1, categoryLevel2, categoryLevel3, pageIndex);
	}

	// 获取查找后的数据数
	@Override
	public List<AppInfo> getAppInFolistCount(String flatformName,
			Integer status, Integer flatformId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3) {
		return appInfoMapper.getAppInfoCount(flatformName, status, flatformId,
				categoryLevel1, categoryLevel2, categoryLevel3);
	}

	// 添加App
	@Override
	public Integer addAppInfo(AppInfo appInfo) {
		return appInfoMapper.addAppInfo(appInfo);
	}

	// 上架商品
	@Override
	public Integer upStatus(String APKName) {
		return appInfoMapper.upStatus(APKName);

	}

	//查找用户
	@Override
	public AppInfo getAppInfoByIdOrAPKName(Integer id, String APKName) {
		return appInfoMapper.getAppInfoByIdOrAPKName(id, APKName);
	}

}
