package com.webapp.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.model.Member;

public class MemberRegisterService {

	MemberDao memberDao;
	
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	@Transactional
	public void register(Member member) {
		try {
			memberDao.insert(member);
//			memberDao.insertGenerator(member);
		} catch (DuplicateKeyException e) {
			throw new AlreadyExistingMemberException(e);
		}
	}
}


