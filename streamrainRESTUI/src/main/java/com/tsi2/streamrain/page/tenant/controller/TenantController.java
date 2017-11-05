package com.tsi2.streamrain.page.tenant.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;


@RestController
@RequestMapping("/administrador")
public class TenantController {
	
	@Resource(name="tenantService")
	ITenantService tenantService;
	
		
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BindingResult> insertUser(@RequestBody TenantDto tenant) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	tenantService.saveTenant(tenant);
        return response;
    }
	
}
