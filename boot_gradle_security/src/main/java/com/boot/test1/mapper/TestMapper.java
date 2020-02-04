package com.boot.test1.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
	String getCurrentTime();
}
