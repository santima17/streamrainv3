package com.tsi2.streamrain.page.user.controller;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.janus.JanusAccessInformationDto;
import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.user.interfaces.IUserService;
import com.tsi2.streamrain.utils.Utils;

@RestController
@RequestMapping("/user")

public class UserController {
	
	private static final String BAD_REQUEST_MSG = "Los datos del usuario no fueron correctamente ingresados";

	@Resource(name="userService")
	IUserService userService;
	
	@Resource(name="sessionService")
	ISessionService sessionService;
		
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ValidadorUser());
    }
	
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers() {
        return userService.getAll(sessionService.getCurrentTenant());
    }
    
    @RequestMapping(value = "/{userNickname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable String userNickname) {
    	UserDto user = userService.getUserByNickname(userNickname, sessionService.getCurrentTenant());
        ResponseEntity<UserDto> response;
        if (user == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(user, HttpStatus.OK);
        }
        return response;
    }
    
    @RequestMapping(value = "/accesinformation/{userNickname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JanusAccessInformationDto> getAccessInformation(@PathVariable String userNickname) {
    	UserDto user = userService.getUserByNickname(userNickname, sessionService.getCurrentTenant());
    	JanusAccessInformationDto accessInfo = new JanusAccessInformationDto();
    	accessInfo.setBanned(user.getIsBanned()); 
    	accessInfo.setJanusPins(userService.getJanusPinForUser(userNickname,sessionService.getCurrentTenant()));
    	accessInfo.setJanusToken(userService.getJanusTokenForUser(userNickname,sessionService.getCurrentTenant()));
		ResponseEntity<JanusAccessInformationDto> response;
        if (user == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(accessInfo, HttpStatus.OK);
        }
        return response;
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertUser(@RequestBody @Valid UserDto user, BindingResult result, HttpServletRequest request) {
    	
    	String url = request.getRequestURL().toString();
		String tentantID = url.substring(7,url.indexOf("."));
		
    	ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.CREATED);
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(BAD_REQUEST_MSG, HttpStatus.BAD_REQUEST);
    	}
    	user.setBlocked(false);
        userService.saveUser(user, tentantID);
        return response;
    }
    
        
    @RequestMapping(value = "/{userNickname}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> updateUser(@PathVariable String userNickname, @RequestBody UserDto user, BindingResult result) {
    	ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.CREATED);
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(BAD_REQUEST_MSG, HttpStatus.BAD_REQUEST);
    	}
        UserDto userOld = userService.getUserByNickname(userNickname, sessionService.getCurrentTenant());
        if (userOld == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }else {
            userService.updateUser(user, userOld, sessionService.getCurrentTenant());
        }
        return response;
    }
    
    @RequestMapping(value = "/{userNickname}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userNickname) {
    	UserDto userOld = userService.getUserByNickname(userNickname, sessionService.getCurrentTenant());
        userService.deleteUser(userOld, sessionService.getCurrentTenant());
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
