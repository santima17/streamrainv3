package com.tsi2.streamrain.page.janus.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.janus.JanusBackendTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateSessionDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;
import com.tsi2.streamrain.page.general.controller.AbstractController;
import com.tsi2.streamrain.services.janus.interfaces.IJanusService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.utils.Utils;

@RestController
@RequestMapping("/user/janus")
public class JanusController extends AbstractController {

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
		JanusCreateTokenDto tokenGenerated = janusService.createJanusToken(janusCreateTokenDto,
				sessionService.getCurrentTenant());
		ResponseEntity<JanusCreateTokenDto> response;
		if (tokenGenerated != null) {
			response = new ResponseEntity<>(tokenGenerated, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "/createServer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JanusServerDto> createServer(@RequestBody JanusServerDto janusServerDto,
			BindingResult result) {
		UUID token = UUID.randomUUID();
		janusServerDto.setStreamrainRestToken(token.toString());
		boolean ok = janusService.createJanusServer(janusServerDto, sessionService.getCurrentTenant());
		ResponseEntity<JanusServerDto> response;
		if (ok) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "/adminUrl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllJanusAdminUrl() {
		return janusService.getAllJanusAdminUrl(sessionService.getCurrentTenant());
	}

	@RequestMapping(value = "/janusUrl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllJanusUrl() {
		return janusService.getAllJanusUrl(sessionService.getCurrentTenant());
	}

	// CARGA 1 SEGUN DOCUMENTO
	@RequestMapping(value = "/sendBackendToken/{backendToken}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendBackendToken(@PathVariable String backendToken) {

		String urlToSend = janusService.getJanusAdminUrlByToken(backendToken, sessionService.getCurrentTenant());
		JanusBackendTokenDto jsonDto = new JanusBackendTokenDto();

		jsonDto.setJanus("add_token");
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setAdmin_secret("secret");
		jsonDto.setToken(backendToken);

		boolean ok = sentJSONByPOST(urlToSend, jsonDto);
		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	// CARGA 2 SEGUN DOCUMENTO
	@RequestMapping(value = "/sendJanusToken/{janusToken}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendJanusToken(@PathVariable String janusToken) {

		String urlToSend = janusService.getJanusUrlByToken(janusToken, sessionService.getCurrentTenant());
		// for por que cosa?
		JanusBackendTokenDto jsonDto = new JanusBackendTokenDto();
		jsonDto.setJanus("add_token");
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setAdmin_secret("secret");
		jsonDto.setToken(janusToken);

		boolean ok = sentJSONByPOST(urlToSend, jsonDto);
		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	// CARGA 3 SEGUN DOCUMENTO
	@RequestMapping(value = "/createSession/{janusToken}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createSession(@PathVariable String janusToken) {

		String urlToSend = janusService.getJanusUrlByToken(janusToken, sessionService.getCurrentTenant());
		// for por que cosa?
		JanusCreateSessionDto jsonDto = new JanusCreateSessionDto();
		jsonDto.setJanus("create");
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setToken(janusToken);

		String mySession = sentJSONByPOSTGetResponse(urlToSend, jsonDto);
		if (mySession != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
