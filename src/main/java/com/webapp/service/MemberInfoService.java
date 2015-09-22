package com.webapp.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.MemberNotFoundException;
import com.webapp.model.Member;

public class MemberInfoService {
	MemberDao memberDao;
	
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	@Transactional(readOnly=true)
	public Member getMember(int id) {
		
		Member m=null;
		try {
			m = memberDao.selectById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new MemberNotFoundException(e);
		}
		
		return m;
	}
	
	@Transactional(readOnly=true)
	public Member getMember(String email) {
		Member m=null;
		try {
			m = memberDao.selectByEmail(email);
		} catch (EmptyResultDataAccessException e) {
			throw new MemberNotFoundException(e);
		}
		
		return m;
	}

}
