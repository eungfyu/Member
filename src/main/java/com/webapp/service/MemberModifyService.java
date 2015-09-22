package com.webapp.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberModifyEmptyException;
import com.webapp.model.Member;

public class MemberModifyService {

	MemberDao memberDao;
	
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	@Transactional
	public void modify(Member member) {
		try {
			memberDao.update(member);
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) {
			throw new MemberModifyEmptyException(e);
		}
	}
}


