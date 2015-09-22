package com.webapp.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.command.MemberCommand;

public class MemberCommandValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MemberCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {//여기서 memberCommand에 대한 validation을 처리한다
		MemberCommand member = (MemberCommand) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "required");
		
		/*
		 * Email Length Check 64이하 . 필드에러 셋팅중
		 */
		if (member.getEmail().matches("^.{65,}$")) {
			errors.rejectValue("email", "length", new Object[] {64}, null);
		}
		/*
		 * Email Pattern Check (aaa@bbb.com 이메일 형식)
		 */
//		String pattern = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+\\.[A-Za-z]{2,3}$";
		String pattern = "^[A-Za-z0-9_\\-]+@([A-Za-z0-9]+\\.){1,2}[A-Za-z]{2,3}$";
		//([A-Za-z0-9]+\\.){1,2}  ([A-Za-z0-9]+\\.)이 그룹이 한번이나 두번 반복된다.
		if (!member.getEmail().matches(pattern)) {
			errors.rejectValue("email", "pattern");
		}
		/*
		 * Password Length Check 8자리 이상이 아니면 에러
		 */
		if (!member.getPassword().matches("^.{8,64}$")) {
			errors.rejectValue("password", "length", new Object[] {8,64}, null);
		}
		/*
		 * Password Pattern Check
		 * 
		 * qw12er34 ==> valid
		 * qwer		==> invalid
		 * qwer1234	==> invalid
		 * 1234		==> invalid
		 * 
		 * 1. 영문 & 숫자
		 * 2. 영문 연속3자리 이상 안됨
		 * 3. 숫자 연속3자리 이상 안됨 
		 * 
		 */
		if (!Pattern.compile("[A-Za-z]")
					.matcher(member.getPassword())
					.find())
			errors.rejectValue("password", "pattern");
		
		if (!Pattern.compile("[0-9]")
					.matcher(member.getPassword())
					.find())
		errors.rejectValue("password", "pattern");
		
		//영문 세자리 연속이냥
		if (Pattern.compile("[A-Za-z]{3,}")
					.matcher(member.getPassword())
					.find() && !errors.hasFieldErrors("password"))
		errors.rejectValue("password", "pattern");
		
		//숫자 세자리 연속이냥
		if (Pattern.compile("[0-9]{3,}")
					.matcher(member.getPassword())
					.find() && !errors.hasFieldErrors("password"))
		errors.rejectValue("password", "pattern");
		
		/*
		 * Name Length Check 12이하 
		 */
		if (member.getName().matches("^.{13,}$")) {
			errors.rejectValue("name", "length", new Object[] {12}, null);
		}
		
		/*
		 * Comment Length Check 255이하 
		 */
		if (member.getComment().length() > 255) {
			errors.rejectValue("comment", "length", new Object[] {255}, null);
		}
		
		
	}

}
