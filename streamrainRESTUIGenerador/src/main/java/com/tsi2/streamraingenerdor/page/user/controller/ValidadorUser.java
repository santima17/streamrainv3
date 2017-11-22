package com.tsi2.streamraingenerdor.page.user.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamraingenerador.utils.Utils;

public class ValidadorUser implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDto user = (UserDto) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.email.required", "Field email is required");
		if (user.getEmail() != null && (!"".equals(user.getEmail()))) {
			if (!Utils.validateEmail(user.getEmail())) {
				errors.rejectValue("email", "field.email.invalid", "Not a valid email");
			}
		}
						
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "field.nickname.required", "Field nickname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.password.required", "Field password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPass", "field.confirmPass.required", "Field confirmPass is required");
		
		if ((user.getPassword() != null && (!"".equals(user.getPassword())))
				&& (user.getConfirmPass() != null && (!"".equals(user.getConfirmPass())))) {
			if (!user.getPassword().equals(user.getConfirmPass())) {
				errors.rejectValue("confirmPass", "field.confirmPass.invalid", "Passwords do not match");
			}
		}
				
		if ("NONE".equals(user.getCity())){
			errors.rejectValue("country", "field.country.invalid", "Field country is required");
		}
	}

}
