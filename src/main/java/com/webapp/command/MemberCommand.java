package com.webapp.command;

import java.util.Arrays;
import java.util.Date;

import com.webapp.model.Member;


public class MemberCommand {
 	private String email;		// size=64
 	private String password;	// size=64
 	private String name;		// size=12
 	private String gender;
 	private String[] hobby;
 	private String comment;
 	private boolean reception;
 	
 	@Override
 	public String toString() {
 		StringBuilder b = new StringBuilder();
 		
 		//JSON(Javascript Object Notation 객체를 스트링으로 표현하는 것)
 		b.append("{ ");
 		b.append("email : " + email);			b.append(", ");
 		b.append("password : " + password);		b.append(", ");
 		b.append("name : " + name);				b.append(", ");
 		b.append("gender : " + gender);			b.append(", ");
 		b.append("hobby : " + Arrays.toString(hobby)); b.append(", ");
 		b.append("comment : " + comment);		b.append(", ");
 		b.append("reception : " + reception);
 		b.append(" }");
 		return b.toString();
 	}
 	
 	
 	public Member getMember() {
 		Member m = new Member();
 		m.setEmail(this.email);
 		m.setPassword(this.password);
 		m.setName(this.name);
 		m.setRegdate(new Date());
 		
 		return m;
 	}

 	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String[] getHobby() {
		return hobby;
	}

	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isReception() {
		return reception;
	}

	public void setReception(boolean reception) {
		this.reception = reception;
	}

	
}
