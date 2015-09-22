package com.webapp.mapper;

import java.util.Map;

public interface IdGeneratorMapper {
	Map<String, Object> selectByName(String name);
	
	int update(Map<String, Object> idGen);
}
