package cn.appsys.dao;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {

	public DevUser userLogin(@Param("userCode") String userCode);
}
