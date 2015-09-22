package com.webapp.mapper;

import java.util.List;
import java.util.Map;

import com.webapp.model.Member;

public interface MemberMapper {

	List<Member> selectAll();
	List<Member> select(Map<String, Object> index);
	Member selectById(int id);
	Member selectByEmail(String email);
	int selectTotalCount();
	
	int insert(Member member);
	int insertGenerator(Member member);
	
	int update(Member member);
	
	int deleteById(int id);
	int deleteByEmail(String email);
}
