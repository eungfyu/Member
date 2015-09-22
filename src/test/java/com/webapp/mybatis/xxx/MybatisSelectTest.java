package com.webapp.mybatis.xxx;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.webapp.dao.MemberDao;
import com.webapp.model.Member;

public class MybatisSelectTest {
	static Log log = LogFactory.getLog(MybatisSelectTest.class);

	public static void main(String[] args) throws IOException, SQLException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		InputStream inputStream = Resources.getResourceAsStream("com/webapp/mybatis/xxx/mybatis_config.xml");
		SqlSessionFactory factory = builder.build(inputStream, "oracle");
		SqlSession session =  factory.openSession(false);
		
		MemberDao mapper = session.getMapper(MemberDao.class);
		
		List<Member> list = mapper.selectAll();
		
		for (Member m : list)
			System.out.println(m.getId() + " " + m.getEmail() + " " + m.getPassword());
		
		log.info("selectById");
		Member m =  mapper.selectById(1016);
		System.out.println(m.getId() + " " + m.getEmail() + " " + m.getPassword());
		
		log.info("selectByEmail");
		m =  mapper.selectByEmail("xxx@1013");
		System.out.println(m.getId() + " " + m.getEmail() + " " + m.getPassword());
		
		log.info("selectByEmailWithPassword");
		m =  mapper.selectByEmailWithPassword("xxx@1013", "yyy");
		System.out.println(m.getId() + " " + m.getEmail() + " " + m.getPassword());

		log.info("insert");
		m.setId(20000);
		m.setEmail("xyxyxy");
		m.setName("xxx");
		m.setRegdate(new Date());
		mapper.insert(m);
		
//		mapper.deleteByIdWithEmail(10502, "766");
		session.commit();
		session.close();
		
	}

}
