package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	public List<AppCategory> getAppCategoryList(@Param("parentId") Integer parentId);
}
