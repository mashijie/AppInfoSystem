package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {

	List<AppCategory> getAppCategoryList(Integer parentId);
	
	
}
