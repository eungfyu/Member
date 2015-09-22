package com.webapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.mysql.fabric.xmlrpc.base.Array;
import com.webapp.command.Hobby;
import com.webapp.command.MemberCommand;
import com.webapp.exception.AleadyExistingMemberException;
import com.webapp.service.MemberRegisterService;
import com.webapp.validator.MemberCommandValidator;

@Controller
@RequestMapping("/member")
public class MemberRegisterController {
	
	static Log log=LogFactory.getLog(MemberCommand.class);
	
	@Autowired
	MessageSource messageSource;
		
	@Autowired
	MemberRegisterService service;
	
	@Autowired
	MemberCommandValidator validator;
	
	
	@ModelAttribute("gender")
	public Map<String, String> getGender(HttpSession session) {
		Locale locale = (Locale) session.getAttribute(SessionLocaleResolver.class.getName()+".LOCALE");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("male", messageSource.getMessage("member.regist.gender.male", null, locale));
		map.put("female", messageSource.getMessage("member.regist.gender.female", null, locale));
		return map;
	}
	
	@ModelAttribute("hobby")
	public List<Hobby> getHobby(HttpSession session) {
		Locale locale = (Locale) session.getAttribute(SessionLocaleResolver.class.getName()+".LOCALE");
		
		List<Hobby> hobbies = new ArrayList<Hobby>();
		hobbies.add(new Hobby("music", messageSource.getMessage("member.regist.hobby.music", null, locale)));
		hobbies.add(new Hobby("movie", messageSource.getMessage("member.regist.hobby.movie", null, locale)));
		hobbies.add(new Hobby("reading", messageSource.getMessage("member.regist.hobby.reading", null, locale)));
		hobbies.add(new Hobby("walking", messageSource.getMessage("member.regist.hobby.walking", null, locale)));
		
		return hobbies;
	}
		
	@ModelAttribute("member")
	public MemberCommand getMemberCommand() {
		MemberCommand member =  new MemberCommand();
//		member.setGender("female");//gender디폴트값
		return member;
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.GET)
	public String registForm(@ModelAttribute("member") MemberCommand command) {//form을 제출하는 메소드 역할을 함
		return "member/registForm";//뷰이름을 이걸로 한다.
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String regist(@ModelAttribute("member") MemberCommand command, Errors errors) {//성공시에는 member/registSuccess이리 간다
		//@ModelAttribute("member") MemberCommand이름이 길어서 member로 이름을 줄인다는 의미
		
		/*
		 * Validation: 바인딩 통과했으면 Validation 이 필요하다.
		 */
		
		log.info("command = " + command);

		validator.validate(command, errors);
		
//		errors.reject("xxx");
		
		if (errors.hasErrors()) {
			command.setPassword("");//비번 입력시 에러가 나서 돌아갔을 때 초기화시켜라
			return "member/registForm";
		}
				
		/*
		 * DB등록
		 */
		try {
			service.register(command.getMember());
		} catch (AleadyExistingMemberException e) {
			log.error("Member Exiting...", e);
			errors.reject("duplicate");
			return "member/registForm";//익셉션이 발생되면 바로 리턴하도록
		}
		
		return "member/registSuccess";
	}
	
}
