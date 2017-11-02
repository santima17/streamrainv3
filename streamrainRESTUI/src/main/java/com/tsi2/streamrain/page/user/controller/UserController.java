package com.tsi2.streamrain.page.user.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.services.user.interfaces.IUserService;
import com.tsi2.streamrain.utils.Utils;

@Controller
public class UserController {
	
	@Resource(name="userService")
	IUserService userService;
		
	private static final String USER_PREFIX = "/user/";
	
	@RequestMapping(value = "/{tenant}/registerUser", method = RequestMethod.GET)
	public String showRegisterUser () {		
		return USER_PREFIX + "registerUser";
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ValidadorUser());
    }
	
	@RequestMapping(value = "/{tenant}/registerProcess", method = RequestMethod.POST)
    public String registerUser(@PathVariable("tenant") String tenant, @Valid UserDto userDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return USER_PREFIX + "registerUser";
		}
		if (userDto.getNickname() != null && (!"".equals(userDto.getNickname()))) {
			if (userService.existsUserXNickName(userDto.getNickname(), tenant)) {
				model.addAttribute("error", "Already exists a users with that nickname");
				return USER_PREFIX + "registerUser";
			}
		}
		String passEncrptyed = Utils.encryptPassword(userDto.getPassword());
		userDto.setPassword(passEncrptyed);
		userService.saveUser(userDto, tenant);
		model.addAttribute("message", "The user was successfully registered");
		
		return USER_PREFIX + "registerUser";
	}
	
				
	@ModelAttribute("userDto")
	public UserDto populateForm() {
	     return new UserDto();
	}
	
	@ModelAttribute("countryList")
	public List<String> populateCountryList() { 
	  return Utils.getAllCountries();
	}
}
