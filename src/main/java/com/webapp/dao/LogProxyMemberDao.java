package com.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webapp.model.Member;

public class LogProxyMemberDao implements MemberDao {

	static Log log;
	MemberDao real;
	
	public LogProxyMemberDao(MemberDao real) {
		this.real = real;
		log = LogFactory.getLog(real.getClass());
	}
	
	@Override
	public List<Member> selectAll() {
		log.info("#####################"); // 횡단적 기능(관심)(관점) Aspect
		log.info("selectAll() start... "); // 횡단적 관심사의 분리 ==> AOP
		log.info("#####################"); // cross-cutting concern
		List<Member> list = real.selectAll();
		log.info("#####################");
		log.info("selectAll() end... ");
		log.info("#####################");
		return list;
	}

	@Override
	public List<Member> select(Map<String, Object> index) {
		log.info("###########################################"); 
		log.info("select(Map<String, Object> index) start... "); 
		log.info("###########################################");
		List<Member> list = real.select(index);
		log.info("###########################################"); 
		log.info("select(Map<String, Object> index) end... "); 
		log.info("###########################################");
		return list;
	}

	@Override
	public Member selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member selectByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectTotalCount() {
		int rtn=0;
	try {	
		log.info("###########################################"); 
		log.info("selectTotalCount start... BeforeAdvice"); 
		log.info("###########################################");
		// BeforeAdvice
		rtn = real.selectTotalCount(); // JoinPoint(조인포인트)
		log.info("###########################################"); 
		log.info("selectTotalCount AfterReturnig "); 
		log.info("###########################################");
		// AfterReturing
		
	} catch(Throwable t) {
		log.info("###########################################"); 
		log.info("selectTotalCount AfterThrowing "); 
		log.info("###########################################");
		// AfterThrowing
	} finally {
		log.info("###########################################"); 
		log.info("selectTotalCount end... AfterAdvice"); 
		log.info("###########################################");
		// AfterAdvice
	}
		// AroundAdvice (Before + After)
		// Pointcut (Weaving 되는 JoinPoint들)
		// Aspect Class = Pointcut + Advice
	return rtn;
	}

	@Override
	public int insert(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertGenerator(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByEmail(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Member> select(int firstItem, int lastItem) {
		
		return real.select(firstItem, lastItem);
	}

	@Override
	public Member selectByEmailWithPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByIdWithEmail(int id, String email) {
		// TODO Auto-generated method stub
		
	}

}
