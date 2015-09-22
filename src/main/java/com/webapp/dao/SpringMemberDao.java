package com.webapp.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.webapp.model.Member;
import com.webapp.util.Password;

public class SpringMemberDao implements MemberDao {

	static Log log = LogFactory.getLog(SpringMemberDao.class);
	
	final static String oracle="Oracle";
	final static String mysql="MySQL";
	
	boolean useGeneratorTable;
	String DB_VENDER;
	JdbcTemplate template;
	
	public void setUseGeneratorTable(boolean use) {
		this.useGeneratorTable = use;
		log.info("useGeneratorTable = " + use);
	}
	
	public void setTemplate(JdbcTemplate template) throws SQLException  {
		this.template = template;
		Connection con=null;
		try {
			con = template.getDataSource().getConnection();
			DB_VENDER = con.getMetaData().getDatabaseProductName();
			log.info("DB_VENDER = " + DB_VENDER);
		} catch (SQLException e) {
			log.error("DB_VENDER error", e);
			throw e;
		} finally {
				try {
					if (con !=null)
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
	RowMapper<Member> memberMapper = new RowMapper<Member>() {

		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member m = new Member();
			m.setId(rs.getInt("id"));
			m.setEmail(rs.getString("email"));
			m.setPassword(rs.getString("password"));
			m.setName(rs.getString("name"));
			m.setRegdate(rs.getDate("regdate"));
			return m;
		}
	};
	
	@Override
	public List<Member> selectAll() {
		// Weaving(위빙)
		return template.query(SELECT_ALL, memberMapper);
		
	}

	@Override
	public List<Member> select(Map<String, Object> index) {
		List<Member> list = new ArrayList<Member>();
		
		switch (DB_VENDER) {
		case oracle:
			int firstItem = (int)index.get("firstItem");
			int lastItem = (int)index.get("lastItem");
			list = template.query(PAGING_ORACLE, memberMapper, firstItem, lastItem);
			break;
		case mysql:
			int offset = (int)index.get("offset");
			int count = (int)index.get("count");
			list = template.query(PAGING_MYSQL, memberMapper, offset, count);
			break;	
		}
		return list;
	}

	@Override
	public List<Member> select(int firstItem, int lastItem) {
		Map<String, Object> index = new HashMap<String, Object>();
		index.put("firstItem", firstItem);
		index.put("lastItem", lastItem);
		int offset = firstItem - 1;
		int count = lastItem - firstItem + 1;
		index.put("offset", offset);
		index.put("count", count);
		return this.select(index);
	}
	
	
	@Override
	public Member selectById(int id) {
		Member m = template.queryForObject(SELECT_BY_ID, memberMapper, id);
		return m;
	}

	@Override
	public Member selectByEmail(String email) {
		Member m = template.queryForObject(SELECT_BY_EMAIL, memberMapper, email);
		return m;
	}
	
	@Override
	public int selectTotalCount() {
		return template.queryForObject("select count(*) from member", Integer.class);
	}

	@Override
	public int insert(Member member) {
		
		int rtn=0;
		if (useGeneratorTable) {
			
			log.info("insert by GeneratorTable QUERY");
			Map<String, Object> idGen = template.queryForMap(SELECT_GEN_MEMBER_ID, "memberId");
			int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
			int incval = ((BigDecimal)idGen.get("incval")).intValue();
			int seq = nextval + incval;
			template.update(UPDATE_ID_GENERATOR_WITH_NEXTVAL, seq, "memberId");
			member.setId(seq);
			
			rtn = template.update(INSERT_BY_GENERATOR_TABLE, 
								member.getId(),
								member.getEmail(),
								Password.encode(member.getPassword()),
								member.getName(),
								member.getRegdate());
		} else {
			
			switch (DB_VENDER) {
			case oracle:
				log.info("insert by Sequence QUERY");
				
				int seq = template.queryForObject("select member_id_seq.nextval from dual", Integer.class);
				member.setId(seq);
				
				rtn = template.update(INSERT_BY_SEQUENCE, 
									member.getId(),
									member.getEmail(),
									Password.encode(member.getPassword()),
									member.getName(),
									member.getRegdate());
				break;
			case mysql:
				log.info("insert by AutoIncrement QUERY");
				
				KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
				
				final Member m = member;
				rtn = template.update(new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						PreparedStatement pstmt = con.prepareStatement(INSERT_BY_AUTOINCREMENT, Statement.RETURN_GENERATED_KEYS);
						pstmt.setString(1, m.getEmail());
						pstmt.setString(2, Password.encode(m.getPassword()));
						pstmt.setString(3, m.getName());
						pstmt.setTimestamp(4, new Timestamp(m.getRegdate().getTime()));
						
						return pstmt;
					}
				}, generatedKeyHolder);
				member.setId(generatedKeyHolder.getKey().intValue());
				break;	
			}
		}
		
		return rtn;
	}

	@Override
	public int insertGenerator(Member member) {
		Assert.isTrue(false, "insert() 을 대신 사용하세요.");
		return 0;
	}

	@Override
	public int update(Member member) {
		BEGIN();
		UPDATE("member");
		if (member.getPassword() != null)
		   SET("password = ?");
		if (member.getName() != null)
		   SET("name = ?");
		 WHERE("id = ?");
		 WHERE("email = ?");
		 
		String sql = SQL();
		log.info("\n" + sql);
		final Member m = member;
		int rtn = template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int index=0;
				if (m.getPassword() != null)
					ps.setString(++index, Password.encode(m.getPassword()));
				if (m.getName() != null)
					ps.setString(++index, m.getName());
				ps.setInt(++index, m.getId());
				ps.setString(++index, m.getEmail());
			}
		});

		if (rtn != 1) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException("invalid update", 1, rtn);
		}
		
		return rtn;
	}

	@Override
	public int deleteById(int id) {
		int rtn = template.update(DELETE_BY_ID, id);
		if (rtn != 1) 
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(id + "", 1, rtn);
		return rtn;
	}

	@Override
	public int deleteByEmail(String email) {
		int rtn = template.update(DELETE_BY_EMAIL, email);
		if (rtn != 1) 
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(email, 1, rtn);
		return rtn;
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
