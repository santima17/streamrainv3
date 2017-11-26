package com.tsi2.streamraingenerador.page.tenant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;


@RestController
@RequestMapping("/generator")
public class TenantController {
	
	@Autowired
	ITenantService tenantService;
	
	@Autowired
	ISessionService sessionService;
	
		
    @RequestMapping(value = "/createGenerator", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BindingResult> insertTenant(@RequestBody TenantDto tenant) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	tenantService.saveTenant(tenant);
        return response;
    }
    
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TenantDto> getAllContent() {
        return tenantService.getAllTenant();
    }
	
    @RequestMapping(value = "/{tenantName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenantDto> getContent(@PathVariable String tenantName) {
    	TenantDto tenant = tenantService.getTenantByName(tenantName);
        ResponseEntity<TenantDto> response;
        if (tenant == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<TenantDto>(tenant, HttpStatus.OK);
        }
        return response;
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTenant(@RequestParam String newPassword) {
    	ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
    	TenantDto tenant = tenantService.getTenantByName(sessionService.getCurrentTenant());
    	if (tenant == null) {
    		 new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	tenant.setGeneratorPassword(newPassword);
    	boolean ok = tenantService.updateTenant(tenant);
        if (!ok) {
        	return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return response;
    }
    
    @RequestMapping(value = "/blockUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> blockUser(@RequestParam String userNickName, @RequestParam String tenantId) {
    	boolean ok = tenantService.blockUser(userNickName, tenantId);
        ResponseEntity<Boolean> response;
        if (ok) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
    @RequestMapping(value = "/blockContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> blockContent(@RequestParam Integer contentId, @RequestParam String tenantId) {
    	boolean ok = tenantService.blockContent(contentId, tenantId);
        ResponseEntity<Boolean> response;
        if (ok) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
}
