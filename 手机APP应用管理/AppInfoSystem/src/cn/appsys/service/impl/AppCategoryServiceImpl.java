package cn.appsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.AppCategoryService;

@Service
public class AppCategoryServiceImpl implements AppCategoryService{

	@Autowired
	private AppCategoryMapper appCategoryMapper;

	@Override
	public List<AppCategory> getAppCategoryList(Integer parentId){
		return appCategoryMapper.getAppCategoryList(parentId);
	}
	
	
	
	
}
