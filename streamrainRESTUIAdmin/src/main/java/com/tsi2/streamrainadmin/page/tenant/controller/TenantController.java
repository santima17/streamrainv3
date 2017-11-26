package com.tsi2.streamrainadmin.page.tenant.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;
import com.tsi2.streamrainadmin.utils.Utils;


@RestController
@RequestMapping("/administrator")
public class TenantController {
	
	@Resource(name="tenantService")
	ITenantService tenantService;
	
	@Value("${location.file.path.picture}")
	private String locationPicture;
	
		
    @RequestMapping(value = "/createGenerator", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BindingResult> insertTenant(@RequestParam("logo") MultipartFile logo, @RequestPart("datos") String datos, BindingResult result) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	TenantDto tenant;
		try {
			Utils.recordFile(locationPicture, logo);
			tenant = new ObjectMapper().readValue(datos, TenantDto.class);
	    	tenantService.saveTenant(tenant);
	        return response;
		} catch (JsonParseException e1) {
			e1.printStackTrace();
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (IOException e1) {
			e1.printStackTrace();    		
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
    }
    
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TenantDto>> getAllContent() {
		List<TenantDto> list = tenantService.getAllTenant();
        return new ResponseEntity<List<TenantDto>>(list, HttpStatus.OK);
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
    
    @RequestMapping(value = "/unblockUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> unblockUser(@RequestParam String userNickName, @RequestParam String tenantId) {
    	boolean ok = tenantService.unblockUser(userNickName, tenantId);
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
    
    @RequestMapping(value = "/unblockContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> unblockContent(@RequestParam Integer contentId, @RequestParam String tenantId) {
    	boolean ok = tenantService.unblockContent(contentId, tenantId);
        ResponseEntity<Boolean> response;
        if (ok) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
}
