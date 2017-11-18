package com.tsi2.streamrain.page.janus.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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

import com.tsi2.streamrain.datatypes.content.ChatMessageDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.janus.JanusAttachedSessionDto;
import com.tsi2.streamrain.datatypes.janus.JanusBackendTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusChatRoomDto;
import com.tsi2.streamrain.datatypes.janus.JanusChatRoomInfoDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateSessionDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyInfoDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;
import com.tsi2.streamrain.page.general.controller.AbstractController;
import com.tsi2.streamrain.services.content.interfaces.IContentService;
import com.tsi2.streamrain.services.janus.interfaces.IJanusService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.utils.Utils;

@RestController
@RequestMapping("/generator/janus")
public class JanusGeneratorController extends AbstractController {

	@Value("${janus.chatRoom.url}")
	private String JANUS_CHAT_ROOM_URL;

	private static final String ADD_TOKEN = "add_token";

	private static final String CREATE = "create";

	private static final String ATTACH = "attach";

	private static final String JANUS_PLUGIN_STREAMING = "janus.plugin.streaming";

	private static final String JANUS_PLUGIN_TEXTROOM = "janus.plugin.textroom";

	@Autowired
	IJanusService janusService;

	@Autowired
	ISessionService sessionService;
	
	@Autowired
	IContentService contentService;
	

	@RequestMapping(value = "/getUpdates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUpdates() {
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/janusServers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<JanusServerDto>> getAllJanusServer() {
		List<JanusServerDto> listResult = janusService.getAllJanusServer(sessionService.getCurrentTenant());
		if (listResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listResult, HttpStatus.OK);
        }
	}
	
	@RequestMapping(value = "/janusServers/{janusServerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JanusServerDto> getJanusServerForId(@PathVariable Integer janusServerId) {
		JanusServerDto result = janusService.getJanusServer(janusServerId, sessionService.getCurrentTenant());
		if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
	}
	
	@RequestMapping(value = "/janusCreationTokens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<JanusCreateTokenDto>> getAllJanusCreationTokens() {
		List<JanusCreateTokenDto> listResult = janusService.getAllJanusCreationTokens(sessionService.getCurrentTenant());
		if (listResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listResult, HttpStatus.OK);
        }
	}
	
	@RequestMapping(value = "/createToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JanusCreateTokenDto> createToken(HttpServletRequest request) {

		String url = request.getRequestURL().toString();
		String tentantID = url.substring(7,url.indexOf("."));
		
		JanusCreateTokenDto janusCreateTokenDto = new JanusCreateTokenDto();
		Date now = new Date();
		UUID token = UUID.randomUUID();
		janusCreateTokenDto.setJanusToken(token.toString());
		janusCreateTokenDto.setStreamrainRestBackend("");
		janusCreateTokenDto.setDateCreation(now);
		janusCreateTokenDto.setDateExpiration(Utils.sumarRestarDiasFecha(now, 1));
		JanusCreateTokenDto tokenGenerated = janusService.createJanusToken(janusCreateTokenDto,
				tentantID);

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
			BindingResult result, HttpServletRequest request) {
		
		String siteURL = request.getRequestURL().toString();
		siteURL += JANUS_CHAT_ROOM_URL; 
		
		String urlTenant = request.getRequestURL().toString();
		String tentantID = urlTenant.substring(7,urlTenant.indexOf("."));
		
		UUID token = UUID.randomUUID();
		janusServerDto.setStreamrainRestToken(token.toString());
		boolean ok = janusService.createJanusServer(janusServerDto, tentantID);

		// JANUS PROCESS
		ok = sendBackendToken(janusServerDto.getStreamrainRestToken(), janusServerDto.getAdminUrl(), janusServerDto.getAdminSecret());

		List<String> tokenAvailablesList = janusService.getAllAvailablesUserTokens(tentantID);

		for (String janusUserToken : tokenAvailablesList) {
			ok = sendUserTokens(janusUserToken, janusServerDto.getAdminUrl(), janusServerDto.getAdminSecret());
		}

		ok = createSession(janusServerDto.getStreamrainRestToken(), janusServerDto.getJanusUrl());
		if (ok) {
			ok = attachSessionStreaming(janusServerDto.getStreamrainRestToken(), janusServerDto.getJanusUrl());
		}
		if (ok) {
			ok = attachedSessionTextroom(janusServerDto.getStreamrainRestToken(), janusServerDto.getJanusUrl());
		}
		
		List<JanusLiveOnlyInfoDto> liveOnlyContents = contentService.getAllLiveOnlyContents(tentantID);
		for (JanusLiveOnlyInfoDto liveOnlyInfo : liveOnlyContents) {
			ok = liveOnly(janusServerDto.getStreamrainRestToken(), janusServerDto.getJanusUrl(), liveOnlyInfo, janusServerDto.getAdminKey());
			
			ok = chatRoom(janusServerDto.getStreamrainRestToken(), janusServerDto.getJanusUrl(), fillChatRoomInformation(liveOnlyInfo), janusServerDto.getAdminKey());
		}
		//END JANUS PROCESS
		
		ResponseEntity<JanusServerDto> response;
		if (ok) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/adminUrl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllJanusAdminUrl() {
		return janusService.getAllJanusAdminUrl(sessionService.getCurrentTenant());
	}

		
	@RequestMapping(value = "/keepChatMessage/{tokenJanusCreationTokens}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentCommentDto> addChatMessageToContent(@PathVariable String tokenJanusCreationTokens, @RequestBody ChatMessageDto chatMessageDto) {
    	Integer idJanusServer = janusService.getJanusServerIdForJanusCreationToken(tokenJanusCreationTokens, sessionService.getCurrentTenant());
    	if (idJanusServer == null) {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
		boolean voteOk = contentService.addChatMessageToContent(idJanusServer, chatMessageDto.toString(), sessionService.getCurrentTenant());
        ResponseEntity<UserContentCommentDto> response;
        if (voteOk) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }

	// CARGA 1 SEGUN DOCUMENTO
	public boolean sendBackendToken(final String backendToken, final String url, final String secret) {

		// String urlToSend = janusService.getJanusAdminUrlByToken(backendToken,
		// sessionService.getCurrentTenant());
		JanusBackendTokenDto jsonDto = new JanusBackendTokenDto();

		jsonDto.setJanus(ADD_TOKEN);
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setAdmin_secret(secret);
		jsonDto.setToken(backendToken);

		return sentJSONByPOST(url, jsonDto);

	}

	// CARGA 2 SEGUN DOCUMENTO
	public boolean sendUserTokens(String janusToken, final String url, final String secret) {

		JanusBackendTokenDto jsonDto = new JanusBackendTokenDto();
		jsonDto.setJanus(ADD_TOKEN);
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setAdmin_secret(secret);
		jsonDto.setToken(janusToken);

		return sentJSONByPOST(url, jsonDto);
	}

	// CARGA 3 SEGUN DOCUMENTO
	public boolean createSession(final String backendToken, final String url) {

		JanusCreateSessionDto jsonDto = new JanusCreateSessionDto();
		jsonDto.setJanus(CREATE);
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setToken(backendToken);

		String mySession = sentJSONByPOSTGetResponse(url, jsonDto);

		if (mySession != null) {
			sessionService.setMySession(mySession);
			return true;
		} else {
			return false;
		}
	}

	// CARGA 4 SEGUN DOCUMENTO
	public boolean attachSessionStreaming(final String backendToken, final String url) {

		JanusAttachedSessionDto jsonDto = new JanusAttachedSessionDto();
		jsonDto.setJanus(ATTACH);
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setToken(backendToken);
		jsonDto.setPlugin(JANUS_PLUGIN_STREAMING);

		String mySessionHandler = sentJSONByPOSTGetResponse(url + "/" + sessionService.getMySession(), jsonDto);
		if (mySessionHandler != null) {
			sessionService.setMySessionHandler(mySessionHandler);
			return true;
		} else {
			return false;
		}

	}

	// CARGA 5 SEGUN DOCUMENTO
	public boolean attachedSessionTextroom(final String backendToken, final String url) {

		JanusAttachedSessionDto jsonDto = new JanusAttachedSessionDto();
		jsonDto.setJanus(ATTACH);
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setToken(backendToken);
		jsonDto.setPlugin(JANUS_PLUGIN_TEXTROOM);

		String myTextroomHandle = sentJSONByPOSTGetResponse(url + "/" + sessionService.getMySession(), jsonDto);
		if (myTextroomHandle != null) {
			sessionService.setMyTextroomHandle(myTextroomHandle);
			return true;
		} else {
			return false;
		}
	}

	// CARGA 6 SEGUN DOCUMENTO
	public boolean liveOnly(final String backendToken, final String url, final JanusLiveOnlyInfoDto liveOnlyInfo, final String admin_key) {

		JanusLiveOnlyDto jsonDto = new JanusLiveOnlyDto();
		jsonDto.setJanus("message");
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setToken(backendToken);
		liveOnlyInfo.setRequest(CREATE);
		liveOnlyInfo.setType("rtp");
		liveOnlyInfo.setAdminkey(admin_key);
		jsonDto.setBody(liveOnlyInfo);

		sentJSONByPOST(url + "/" + sessionService.getMySession() + "/" + sessionService.getMySessionHandler(),
				jsonDto);
		return true;
	}

	// CARGA 6 SEGUN DOCUMENTO
	public boolean chatRoom(final String backendToken, final String url, final JanusChatRoomInfoDto chatRoomInfo, final String admin_key) {

		JanusChatRoomDto jsonDto = new JanusChatRoomDto();
		jsonDto.setJanus("message");
		jsonDto.setTransaction(String.valueOf(Math.random()));
		jsonDto.setToken(backendToken);
		chatRoomInfo.setRequest(CREATE);
		chatRoomInfo.setAdmin_key(admin_key);
		jsonDto.setBody(chatRoomInfo);

		sentJSONByPOST(url + "/" + sessionService.getMySession() + "/" + sessionService.getMyTextroomHandle(),
				jsonDto);
		return true;
	}

	private JanusChatRoomInfoDto fillChatRoomInformation(final JanusLiveOnlyInfoDto liveOnlyContents) {
		JanusChatRoomInfoDto janusChatRoomInfoDto = new JanusChatRoomInfoDto();
		janusChatRoomInfoDto.setAdmin_key(liveOnlyContents.getAdminkey());
		janusChatRoomInfoDto.setDescription(liveOnlyContents.getDescription());
		janusChatRoomInfoDto.setPermanent(true);
		if(liveOnlyContents.isPpv()) {
			janusChatRoomInfoDto.setPin(liveOnlyContents.getPin());
		}
		janusChatRoomInfoDto.setPost(JANUS_CHAT_ROOM_URL);
		janusChatRoomInfoDto.setRequest(CREATE);
		janusChatRoomInfoDto.setRoom(liveOnlyContents.getId());
		return janusChatRoomInfoDto;
	}

}
