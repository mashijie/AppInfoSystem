package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	//根据类型获取状态信息
	public List<DataDictionary> getDataDictionary(@Param("type") String type);
	
}
