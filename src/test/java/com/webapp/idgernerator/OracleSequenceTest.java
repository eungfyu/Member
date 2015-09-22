package com.webapp.idgernerator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class OracleSequenceTest {
	
	static Log log=LogFactory.getLog(OracleSequenceTest.class);
	public static void main(String[] args) throws SQLException, IOException {
//		jdbc();
//		jdbcTemplate();
//		SqlSession();//mybatis만 사용. 쿼리부분만 xml로 빼냄
		SqlSessionTemplate();//mybatis와 스프링연계?
	}
	
	static void SqlSessionTemplate() throws IOException, SQLException{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");//이 부분 설정파일로 뺄 수 있다.
		ctx.load("spring/beans_oracle.xml","spring/beans_mysql.xml");//매퍼만 가져와서 insert했다
		ctx.refresh();
		
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		
		Connection con = ds.getConnection();
		log.info("db = " +con.getMetaData().getDatabaseProductName());//메타데이타의 프로덕트네임 가져옴
		log.info("Version = " +con.getMetaData().getDatabaseProductVersion());//버전정보
		log.info("Major = " +con.getMetaData().getDatabaseMajorVersion());
		log.info("Minor = " +con.getMetaData().getDatabaseMinorVersion());
		
		MemberMapper mapper = ctx.getBean(MemberMapper.class); 
		
//		이부분 전부 빈파일루 설정
//		Member member = new Member();
//		member.setEmail("abc@cdb.com"+(int)(Math.random()*1000000));
//		member.setName("abc");
//		member.setPassword("1234");
//		member.setRegdate(new Date());
		
		Member member = ctx.getBean(Member.class);
		member.setEmail(member.getEmail()+(int)(Math.random()*1000000));
		log.info(member.getRegdate());
		
		mapper.insert(member);
		log.info("id = " + member.getId());
	}
	
	static void SqlSession() throws IOException{//Jdbc와의 차이점 살펴봄
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();//빌더를 먼저 만들고 팩토리 만듬
		
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
//		SqlSessionFactory factory = builder.build(inputStream, "oracle");
		SqlSessionFactory factory = builder.build(inputStream, "mysql");//db가 바뀌어도 작동한다. insert를 구분했던것을 속성을 주어서 insert문으로 만들었기때문
		
		//openSession으로 autocommit 설정. 커넥션 가져오는 것과 똑같다.mybatis의 차이점은 쿼리를 소스상에 작성안한다는거가 장점이자 특징임
		SqlSession session = factory.openSession(false);
		
		String statement=null;
		/*
		 * ibatis CRUD ==>매퍼파일(XML)
		 */
		Member member = session.selectOne("com.webapp.mapper.MemberMapper.selectById", 1007);//실행 전까지는 스트링의 오타를 알 수 없다.
		if (member!= null) 
			log.info("id = " + member.getId());
		
		List<Member> list = session.selectList("com.webapp.mapper.MemberMapper.selectAll");
		log.info("member size = " + list.size());
		
		member = new Member();
		member.setEmail("xxx@xxx.com"+ (int)(Math.random()*1000));
		member.setPassword("yyy");
		member.setName("죽먹자");
		member.setRegdate(new Date());
		session.insert("com.webapp.mapper.MemberMapper.insert", member);
//		session.delete("com.webapp.mapper.MemberMapper.delete", null);
//		session.update("com.webapp.mapper.MemberMapper.update", null);
		/*
		 * Mapper interface CRUD ==> mapper Interface + 매퍼파일. 오타날 여지가 없다. 매퍼가 다 해줌
		 */
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		list = mapper.selectAll();
		log.info("member size = " + list.size());
		member = mapper.selectById(1000);
		if (member != null)
			log.info("id = "+ member.getId());
		
		member = new Member();
		member.setEmail("zzz@zzz.com"+ (int)(Math.random()*1000));
		member.setPassword("www");
		member.setName("죽먹자_");
		member.setRegdate(new Date());
		mapper.insert(member);//매퍼를 통해서 insert. 시퀀스를 받아와서 삽입하는 효과
//		dao.deleteByIdWithEmail(0, null);
//		dao.update(null);
		
		session.commit();
		session.rollback();
	}
	
	static void jdbcTemplate() throws SQLException{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 가져오도록 설정
		ctx.load("classpath:spring/beans.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함		
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		/*
		 * 1. sequence 상에서 채번하려면 employee.sql의 member_id_seq 불러와서 채번해야 함
		 * 	  select member_id_seq.nextval from dual;
		 */
		String sql ="select member_id_seq.nextval from dual"; 
		int seq = template.queryForObject(sql, Integer.class);
		log.info("seq = "+seq);
		/*
		 * 2. 채번한 걸로 insert 해야 함
		 */
		String insert = "insert into member" +  
				"(id, email, password, name, regdate)" + 
				"values " +
				"(?, ?, 'yyyy','xxx','2015/08/11')";
		template.update(insert, seq, "xxx@" + seq);
	}
	
	static void jdbc() throws SQLException{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 가져오도록 설정
		ctx.load("classpath:spring/beans.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함		
		
//		DriverManagerDataSource ds = ctx.getBean(DriverManagerDataSource.class);//데이타소스 가져옴
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();// 커넥션 가져옴
		
		/*
		 * 1. sequence 상에서 채번하려면 employee.sql의 member_id_seq 불러와서 채번해야 함
		 * 	  select member_id_seq.nextval from dual;
		 */
		Statement stmt  = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("select member_id_seq.nextval from dual");
		rs.next();
		int seq = rs.getInt(1);
		log.info("seq = " + seq);
		/*
		 * 2. 채번한 걸로 insert 해야 함
		 * 	  select * from member; 
 				insert into member
 				(id, email, password, name, regdate)
 				values
 				(1002, 'xxxx', 'yyyy','zz''z','2015/08/11');
		 */
		String insert = "insert into member" +  
						"(id, email, password, name, regdate)" + 
						"values " +
						"(?, ?, 'yyyy','xxx','2015/08/11')";
		
		PreparedStatement pstmt = con.prepareStatement(insert);
		pstmt.setInt(1, seq);//채번한 번호를 insert시에 사용. 
		pstmt.setString(2, "xxx@xxx.com"+seq);
		pstmt.executeUpdate();
		con.close();
	}

}
