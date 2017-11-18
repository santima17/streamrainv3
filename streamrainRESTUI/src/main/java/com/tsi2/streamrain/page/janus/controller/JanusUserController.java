package com.tsi2.streamrain.page.janus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.services.janus.interfaces.IJanusService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/user/janus")
public class JanusUserController {
	
	@Autowired
	IJanusService janusService;
	
	@Autowired
	ISessionService sessionService;
	
	@RequestMapping(value = "/servers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllJanusUrl() {
		return janusService.getAllJanusUrl(sessionService.getCurrentTenant());
	}

}
