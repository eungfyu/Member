package com.webapp.idgernerator;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.webapp.mapper.IdGeneratorMapper;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class GeneratorTableTest {
	
	static Log log=LogFactory.getLog(GeneratorTableTest.class);
	
	static GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();//공장을 하나만 지어서 사용하도록
	
	public static void main(String[] args) throws SQLException {
		ctx = new GenericXmlApplicationContext();
//		ctx.getEnvironment().setActiveProfiles("oracle");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 가져오도록 설정
		ctx.getEnvironment().setActiveProfiles("mysql");//beans.xml에서 환경정보 가져오기. mysql 프로파일만 가져오도록 설정
		ctx.load("spring/beans_oracle.xml","spring/beans_mysql.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함
		
		for (int i=0;i<100;i++){
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					try {
//						jdbc();
//						jdbcTemplate();//트랜젝션 처리가 있어야 함
//						SqlSession();
						SqlSessionTemplate();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			};
			Thread t = new Thread(r);
			t.start();
		}
	}
	
	
	
	static void SqlSessionTemplate() throws InterruptedException{// 트랜잭션 따로 시작. 트랜잭션 시작부분에 데이터 엑세스가 항상 같이 붙어 있지는 않음. 분리했음
		PlatformTransactionManager tm = ctx.getBean(PlatformTransactionManager.class);
		DefaultTransactionDefinition td = new DefaultTransactionDefinition();//디폴트로 해도 무방
		td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);//정의 정보에 따라 트랜잭션 시작
		td.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		
		TransactionStatus status = tm.getTransaction(td);
		try {//익셉션 발생안했으면 커밋
			memberInsert();//DAO 따로 불러옴
//			memberUpdate();
			tm.commit(status);
		} catch (BadSqlGrammarException e) {
			log.info("BadSql error" + e.getMessage(), e);
		} catch (Exception e) {//익셉션 발생하면 롤백
			tm.rollback(status);
			e.printStackTrace();
		}
	}	
	
	static void memberUpdate() throws InterruptedException{
		Member member = new Member();
		member.setId(1040);
		member.setPassword("asdf1234");
		member.setName("플루토");
		MemberMapper memberMapper = ctx.getBean(MemberMapper.class);
		
		memberMapper.update(member);
	}
	
	static void memberInsert() throws InterruptedException{
		Member member = ctx.getBean(Member.class);
		//공장에서 직접 매퍼 가져옴
		IdGeneratorMapper idGenMapper = ctx.getBean(IdGeneratorMapper.class);
		MemberMapper memberMapper = ctx.getBean("memberMapper", MemberMapper.class);
		
		Map<String, Object> idGen = idGenMapper.selectByName("memberId");
		int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
		int incval = ((BigDecimal)idGen.get("incval")).intValue();
		int seqno = nextval+incval;
		idGen.put("nextval", seqno);
		
		Thread.sleep((int)(Math.random()*1000));//지연코드
		idGenMapper.update(idGen);
		
		member.setId(seqno);
		member.setEmail(member.getEmail()+seqno);
		memberMapper.insertGenerator(member);
		log.info("seqno = "+ seqno);
	}
	
	static void SqlSession() throws IOException, InterruptedException{
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory =  builder.build(inputStream, "mysql");//mysql을 oracle로 변경해도 작동한다. insert에 속성을 주었다
//		SqlSessionFactory factory =  builder.build(inputStream, "oracle");
		
		
		SqlSession session = factory.openSession(false);
		
		IdGeneratorMapper idGeneratorMapper =  session.getMapper(IdGeneratorMapper.class);
		MemberMapper memberMapper = session.getMapper(MemberMapper.class);
		
		Map<String, Object> idGen = idGeneratorMapper.selectByName("memberId");
		int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
		int incval = ((BigDecimal)idGen.get("incval")).intValue();
		int seqno = nextval+incval;
		
		
		Thread.sleep((int)(Math.random()*3000));
		
		
		idGen.put("nextval", seqno);
		idGeneratorMapper.update(idGen);
		
		Member member = new Member();
		member.setId(seqno);
		member.setEmail("xxx@gen.com"+seqno);
		member.setPassword("diedie");
		member.setName("죽먹자");
		member.setRegdate(new Date());
		
		memberMapper.insertGenerator(member);
		log.info("seqno="+seqno);
		
		session.commit();
		session.close();
	}
	
	
	static void jdbcTemplate() throws SQLException, InterruptedException{
//		아래 코딩은 위에 미리 넣었음
/*		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 가져오도록 설정
		ctx.load("classpath:spring/beans.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함		
*/		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		/*
		 * 1. sequence 상에서 채번하려면 employee.sql의 member_id_seq 불러와서 채번해야 함
		 * 	  select member_id_seq.nextval from dual;
		 */
//		String sql ="select member_id_seq.nextval from dual";//아래 코딩으로 바꿨음
//		int seq = template.queryForObject(sql, Integer.class);
		
		String sql ="select name, nextval, incval " + 
				"from id_generator " + 
				"where name = 'memberId' " +
				"for update ";
		
		//스프링에서 트랜젝션 처리하는 방법. 트랜젝션을 일원화 하기 위함. 선언적 트랜젝션이라고 함
//		DataSourceTransactionManager tm = new DataSourceTransactionManager();
//		tm.setDataSource(ctx.getBean(DataSource.class));
		PlatformTransactionManager tm = ctx.getBean(PlatformTransactionManager.class);//추가 코딩에서 빈파일에 위 두 코드를 포함시켰기 때문에 이 코딩이 가능
		
		
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();//디폴트값으로 정의된 트랜젝션을 가져온다
		//IsolationLevel 독립성 레벨을 어떻게 설정할 것인지 정하는 것 가장 강력한 건 serializable. 순서대로 실행시킴.하지만 속도는 느림.오라클은 디폴트가 ISOLATION_READ_COMMITTED임
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		//PropagationBehavior전파속성. 트랜젝션이 있어도 새로 만든다?
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		definition.setReadOnly(false);//업데이트가 필요없을때 사용. 속도가 빨라짐.사용하려면 true값으루.
//		definition.setTimeout(100);
		
		TransactionStatus status = tm.getTransaction(definition);//트랜젝션의 정의정보를 넣어줘야함.이걸 가져오는건 트랜젝션 시작
		
		//템플레이트는 바로 위에서 트랜젝션이 시작되었다는 걸 알고 있다. 그걸 가져옴. 만약 저게 없다면 스스로 만들고 닫음. 
		//이 경우 업데이트가 안된 상태에서 닫히는 경우도 있기 때문에 번호가 중복 부여되는 경우가 생긴다. 즉 rock이 제대로 안됨
		Map<String, Object> gen = template.queryForMap(sql);

		Thread.sleep((int)(Math.random()*3000));//일부러 지연시키는 코드
	
		String name = (String) gen.get("name");
		int nextval = ((BigDecimal) gen.get("nextval")).intValue();
		int incval  = ((BigDecimal) gen.get("incval")).intValue();
		
		String update = "update id_generator " + 
						"	set nextval = ? "+
						"where name='memberId'";
		
		template.update(update, nextval+incval );
		
		String insert = "insert into member2 " +  
						"(id, email, password, name, regdate) " + 
						"values " +
						"(?, ?, 'yyyy','xxx','2015/08/11')";
		
		template.update(insert, nextval+incval, "xxx@"+nextval);
		
		tm.commit(status);
		log.info("name = "+name + ", nextval = "+ (nextval+incval) + ", incval = "+incval);
		/*
		 * 2. 채번한 걸로 insert 해야 함
		 */
/*		String insert = "insert into member" +  
				"(id, email, password, name, regdate)" + 
				"values " +
				"(?, ?, 'yyyy','xxx','2015/08/11')";*/
//		template.update(insert, seq, "xxx@" + seq);
	}
	
	static void jdbc() throws SQLException{
		
		try {//시작시에 잠깐 슬립
			Thread.sleep((int)(Math.random()*3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * 공장을 하나만 지어서 사용하도록 아래 멘트처리
		 */
/*		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 가져오도록 설정
		ctx.load("classpath:spring/beans.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함		
*/		
//		DriverManagerDataSource ds = ctx.getBean(DriverManagerDataSource.class);//데이타소스 가져옴
		DataSource ds = ctx.getBean(DataSource.class);//의존도를 약화시키기 위해 BasicDataSource대신 DataSource사용
		
		log.info(((BasicDataSource)ds).getUrl());
		
		Connection con = ds.getConnection();// 커넥션 가져옴
		con.setAutoCommit(false);//commit을 자동안되도록.
		
		/*
		 * 1. sequence 상에서 채번하려면 employee.sql의 member_id_seq 불러와서 채번해야 함
		 * 	  select member_id_seq.nextval from dual;
		 */
		Statement stmt  = con.createStatement(); 
		String sql ="select name, nextval, incval " + 
					"from id_generator " + 
					"where name = 'memberId' " +
					"for update "; 
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		String name = rs.getString("name");
		int nextval = rs.getInt("nextval");
		int incval = rs.getInt("incval");
		int seq = nextval + incval;
		log.info("seq = " + seq);
		
		
		String insert = "insert into member2 " +  
						"(id, email, password, name, regdate) " + 
						"values " +
						"(?, ?, 'yyyy','xxx','2015/08/11')";
		
		PreparedStatement pstmt = con.prepareStatement(insert);
		pstmt.setInt(1, seq);//채번한 번호를 insert시에 사용. 
		pstmt.setString(2, "xxx@xxx.com"+seq);
		pstmt.executeUpdate();
		
		String update = "update id_generator " + 
						"	set nextval = ? "+
						"where name='memberId'";
		pstmt = con.prepareStatement(update);
		pstmt.setInt(1, seq);
		pstmt.executeUpdate();
				
		con.commit();
		con.close();
	}

}
