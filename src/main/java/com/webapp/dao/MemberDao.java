package com.webapp.dao;

import java.util.List;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public interface MemberDao extends MemberMapper {
	
	List<Member> select(int firstItem, int lastItem);
	
	Member selectByEmailWithPassword(String email, String password);
	void deleteByIdWithEmail(int id, String email);
	/*
	 * IdGenerator DML
	 */
	static final String SELECT_GEN_MEMBER_ID = 
					   " select name as \"name\",       " + 
					   " 	    nextval as \"nextval\", " +
					   " 	    incval as \"incval\"    " +
					   "   from id_generator            " +
					   "  where name = ?                " + // #{name}
					   "    for update	                ";
	static final String UPDATE_ID_GENERATOR_WITH_NEXTVAL = 
					   " update id_generator " + 
					   "    set nextval = ?  " +	// #{nextval}	
					   "  where name = ?     ";		// #{name}

	/*
	 * Member DML
	 */
	static final String SELECT_MEMBER =
						" select id,        " + 
						"        email,     " +
						"	     password,  " +
						"	     name,      " +
						"	     regdate    " +
						"   from member     ";
	static final String SELECT_ALL = SELECT_MEMBER;

	static final String SELECT_BY_ID = SELECT_MEMBER +
						"  where id = ?     ";
	static final String SELECT_BY_EMAIL = SELECT_MEMBER +
						"  where email = ?  ";
	
	static final String PAGING_ORACLE = 
						" select outer.rn,                     " +
						"        outer.id,                     " +
						"        outer.email,                  " +
						"        outer.password,               " +
						"        outer.name,                   " +
						"        outer.regdate                 " +
						"  from (select rownum rn, inner.*     " +
						"	       from (select m.*            " +
						"		           from member m       " +
						"		          order by m.id desc   " +
						"		   	    ) inner                " +
						"	    ) outer                        " +
						" where outer.rn >= ?                  " + // #{firstItem}
						"   and outer.rn <= ?                  ";  // #{lastItem}
	static final String PAGING_MYSQL =
						SELECT_MEMBER + 
						" order by id desc " +
						" limit ?, ?       ";  // #{offset}, #{count}

	
	
	
	static final String INSERT_BY_GENERATOR_TABLE =		// Oracle or MySQL
						"	insert into member  " +
						"	(                   " +
						"		id,             " +
						"		email,          " +
						"		password,       " +
						"		name,           " +
						"		regdate         " +
						"	)                   " +
						"	values              " +
						"	(                   " +
						"		?,              " +	// #{id}
						"		?,              " +	// #{email}
						"		?,              " +	// #{password}
						"		?,              " +	// #{name}
						"		?               " +	// #{regdate}
						"	)                   ";
	static final String INSERT_BY_SEQUENCE = INSERT_BY_GENERATOR_TABLE;	// Oracle
	static final String INSERT_BY_AUTOINCREMENT =						// MySQL
						"	insert into member  " +
						"	(                   " +
						"		email,          " +
						"		password,       " +
						"		name,           " +
						"		regdate         " +
						"	)                   " +
						"	values              " +
						"	(                   " +
						"		?,              " +	// #{email}
						"		?,              " +	// #{password}
						"		?,              " +	// #{name}
						"		?               " +	// #{regdate}
						"	)                   ";
	static final String DELETE =
						" delete from member ";
	static final String DELETE_BY_ID = 
						DELETE +
						"  where id = ?      ";	// #{id}
	static final String DELETE_BY_EMAIL =
						DELETE +
						"  where email = ?   ";	// #{email}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
