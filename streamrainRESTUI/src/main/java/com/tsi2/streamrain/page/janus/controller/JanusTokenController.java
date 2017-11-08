package com.tsi2.streamrain.page.janus.controller;

import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tsi.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.services.janus.interfaces.IJanusService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.utils.Utils;

@RestController
@RequestMapping("/user/janus")
public class JanusTokenController {
	
	@Autowired
	IJanusService janusService;
	
	@Autowired
	ISessionService sessionService;
	
	@RequestMapping(value = "/createToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JanusCreateTokenDto> createToken() {
		JanusCreateTokenDto janusCreateTokenDto = new JanusCreateTokenDto();
		Date now = new Date();
		UUID token = UUID.randomUUID();
		janusCreateTokenDto.setJanusToken(token.toString());
		janusCreateTokenDto.setStreamrainRestBackend("");
		janusCreateTokenDto.setDateCreation(now);
		janusCreateTokenDto.setDateExpiration(Utils.sumarRestarDiasFecha(now, 1));
		JanusCreateTokenDto tokenGenerated = janusService.createJanusToken(janusCreateTokenDto, sessionService.getCurrentTenant());
        ResponseEntity<JanusCreateTokenDto> response;
        if (tokenGenerated != null) {
            response = new ResponseEntity<>(tokenGenerated, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
	

}
