package com.webapp.model;

import java.util.List;

import com.webapp.util.Pagination;

public class MemberList extends Pagination {
	List<Member> members;
	
	public MemberList() {
		super();
	}
	
	public MemberList(int itemsPerPage, int pagesPerGroup) {
		super(itemsPerPage, pagesPerGroup);
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

}
