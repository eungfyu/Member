package com.webapp.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.util.ResourceUtils;

import com.webapp.dao.MemberDao;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class MapperTest {

	public static void main(String[] args) throws IOException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory = builder.build(inputStream);
		SqlSession session =  factory.openSession();
		
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		List<Member> list = mapper.selectAll();
		
		for (Member m : list)
			System.out.println(m.getId() + " " + m.getEmail());
		
		Member m = mapper.selectById(1001);
		System.out.println(m.getId() + " " + m.getEmail());
		
		
	}

}
