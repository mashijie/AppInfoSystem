package cn.appsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService{

	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	
	@Override
	public List<DataDictionary> getdataDictionary(String type){
		return dataDictionaryMapper.getDataDictionary(type);
	}
	
}




