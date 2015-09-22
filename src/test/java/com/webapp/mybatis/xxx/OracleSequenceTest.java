package com.webapp.mybatis.xxx;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.lf5.util.ResourceUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class OracleSequenceTest {
	
	static Log log = LogFactory.getLog(OracleSequenceTest.class);

	public static void main(String[] args) throws Exception {
//		jdbc();
//		jdbcTemplate();
		SqlSession();
//		SqlSessionTemplate();
	}
	
	static void SqlSession() throws Exception {
//		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//		
//		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
////		SqlSessionFactory factory = builder.build(inputStream, "oracle");
//		SqlSessionFactory factory = builder.build(inputStream, "mysql");
		
//		SqlSession session = factory.openSession(false);
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("classpath:spring/beans.xml");
		ctx.refresh();
		
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(ctx.getBean(DataSource.class));
		Resource[] mapperLocations = {new ClassPathResource("com/webapp/mapper/MemberMapper.xml") };
		bean.setMapperLocations(mapperLocations);
		DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
		Properties p = new Properties();
		p.setProperty("Oracle", "oracle");
		p.setProperty("MySQL", "mysql");
		databaseIdProvider.setProperties(p);
		bean.setDatabaseIdProvider(databaseIdProvider);
		
		SqlSessionFactory factory = bean.getObject();
		SqlSession session = factory.openSession(false);
		
		String statement=null;
		
		/*
		 * ibatis CRUD ==> 매퍼파일(xml)
		 */
		Member member = session.selectOne("com.webapp.mapper.MemberMapper.selectById", 1007);
		if (member != null)
			log.info("id = " + member.getId());
		
		List<Member> list = session.selectList("com.webapp.mapper.MemberMapper.selectAll");
		log.info("member size = " + list.size());
		
		member = new Member();
		member.setEmail("xxx@xxx.com" + (int)(Math.random()*1000));
		member.setPassword("yyy");
		member.setName("홍길동");
		member.setRegdate(new Date());
		session.insert("com.webapp.mapper.MemberMapper.insertOracle", member);
//		session.delete("com.webapp.mapper.MemberMapper.delete", null);
//		session.update("com.webapp.mapper.MemberMapper.update", null);
		/*
		 * Mapper interface CRUD ==> Mapper Interface + 매퍼파일
		 */
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		list = mapper.selectAll();
		log.info("member size = " + list.size());
		member = mapper.selectById(1000);
		if (member != null)
			log.info(member.getId());
		
		member = new Member();
		member.setEmail("yyy@yyy.com" + (int)(Math.random()*1000));
		member.setPassword("yyy");
		member.setName("홍길동");
		member.setRegdate(new Date());

		mapper.insert(member);
//		dao.deleteByIdWithEmail(100, null);
//		dao.update(null);
		
		session.commit();
//		session.rollback();
		
	}
	
	static void jdbcTemplate() throws SQLException {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");
		ctx.load("classpath:spring/beans.xml");
		ctx.refresh();
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		
		String sql = "select member_id_seq.nextval from dual";
		int seq = template.queryForObject(sql, Integer.class);
		log.info("seq =" + seq);
		
		String insert = "insert into member " +
				 		"(id, email, password, name, regdate) " +
				 		"values " +
				 		"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		
		template.update(insert, seq, "xxx@" + seq);
	}
	
	static void jdbc() throws SQLException {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");
		ctx.load("classpath:spring/beans.xml");
		ctx.refresh();
		
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();
		
		// 1. select member_id_seq.nextval from dual;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select member_id_seq.nextval from dual");
		rs.next();
		int seq = rs.getInt(1);
		log.info("seq = " + seq);
		
		String insert = "insert into member " +
				 		"(id, email, password, name, regdate) " +
				 		"values " +
				 		"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		
		PreparedStatement pstmt = con.prepareStatement(insert);
		pstmt.setInt(1, seq);
		pstmt.setString(2, "xxx@xxx.com" + seq);
		pstmt.executeUpdate();
		
		con.close();
		
	}

}
