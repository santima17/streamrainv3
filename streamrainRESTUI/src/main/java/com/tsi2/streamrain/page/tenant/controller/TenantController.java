package com.tsi2.streamrain.page.tenant.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;

@Controller
public class TenantController {
	
	@Resource(name="tenantService")
	ITenantService tenantService;
		
	private static final String USER_PREFIX = "/tenant/";
	
	@RequestMapping(value = "/registerTenant", method = RequestMethod.GET)
	public String showRegisterTenant (Model model) {	
		TenantDto tenant = new TenantDto();
		model.addAttribute("tenantForm", tenant);
		return USER_PREFIX + "registerTenant";
	}
	
	@RequestMapping(value = "/registerTenant", method = RequestMethod.POST)
	public String saveTenant (@ModelAttribute("tenantForm") TenantDto tenant) {
		tenantService.saveTenant(tenant);
		return USER_PREFIX + "registerTenant";
	}

}
