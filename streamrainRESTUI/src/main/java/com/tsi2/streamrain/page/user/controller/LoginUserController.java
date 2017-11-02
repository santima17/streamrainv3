package com.tsi2.streamrain.page.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;
import com.tsi2.streamrain.services.user.interfaces.IUserService;
import com.tsi2.streamrain.springmvc.model.Login;
import com.tsi2.streamrain.utils.Utils;

@Controller
public class LoginUserController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ITenantService tenantService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		String tenant = request.getParameter("param");
		System.out.println("TenantID="+tenant);
		tenantService.setCurrentTenant(tenant);
		ModelAndView mav = new ModelAndView("login");
	    mav.addObject("login", new Login());
	    return mav;
	}
	
		
	@RequestMapping(value = "/{tenant}/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(@PathVariable("tenant") String tenant, @ModelAttribute("login") Login login) {
	    ModelAndView mav = null;
	    
	    String passEncrptyed = Utils.encryptPassword(login.getPassword());
	    //String passEncrptyed = login.getPassword();
	    boolean existUser = userService.existsUser(login.getUsername(), passEncrptyed, tenant);
	    if (existUser) {
	    	mav = new ModelAndView("welcome");
	        mav.addObject("firstname", login.getUsername());
	    }else {
	    	mav = new ModelAndView("login");
		    mav.addObject("message", "Username or Password is wrong");
	    }
	    return mav;
	}

}
