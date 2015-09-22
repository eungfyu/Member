package com.webapp.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberUnRegisterEmptyException;
import com.webapp.model.Member;

public class MemberUnRegisterService {

	MemberDao memberDao;
	
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	@Transactional
	public void unregister(int id) {
		try {
			memberDao.deleteById(id);
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) {
			throw new MemberUnRegisterEmptyException(e);
		}
	}
	
	@Transactional
	public void unregister(String email) {
		try {
			memberDao.deleteByEmail(email);
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) {
			throw new MemberUnRegisterEmptyException(e);
		}
	}
	
	
	
}


