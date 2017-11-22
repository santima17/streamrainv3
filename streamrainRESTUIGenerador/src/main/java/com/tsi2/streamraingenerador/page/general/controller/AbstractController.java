package com.tsi2.streamraingenerador.page.general.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tsi2.streamrain.datatypes.janus.AbstractJanusDto;
import com.tsi2.streamrain.datatypes.janus.JanusAttachedSessionDto;
import com.tsi2.streamrain.datatypes.janus.JanusBackendTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusChatRoomDto;
import com.tsi2.streamrain.datatypes.janus.JanusChatRoomInfoDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateSessionDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateSessionResponseDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyInfoDto;
import com.tsi2.streamrain.services.content.interfaces.IContentService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

public class AbstractController { 

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_TYPE_JSON = "application/json";
	private static final String POST = "POST";


	protected static final String ADD_TOKEN = "add_token";

	protected static final String CREATE = "create";

	protected static final String ATTACH = "attach";

	protected static final String JANUS_PLUGIN_STREAMING = "janus.plugin.streaming";

	protected static final String JANUS_PLUGIN_TEXTROOM = "janus.plugin.textroom";
	
	@Autowired
	ISessionService sessionService;
	
	@Autowired
	IContentService contentService;

	public boolean sentJSONByPOST(final String url, final AbstractJanusDto json) {
		URL postURL;
		try {
			postURL = new URL(url);
		} catch (MalformedURLException e) {
			System.out.print("URL invalid:" + url);
			return false;
		}
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) postURL.openConnection();
			connection.setConnectTimeout(5000);// 5 secs
			connection.setReadTimeout(5000);// 5 secs
			try {
				connection.setRequestMethod(POST);
			} catch (ProtocolException e) {
				System.out.print("Protocol POST invalid o not permited");
				return false;
			}
			connection.setDoOutput(true);
			connection.setRequestProperty(CONTENT_TYPE, APPLICATION_TYPE_JSON);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			Gson gson = new GsonBuilder().create();
			out.write(gson.toJson(json));
			out.flush();
			out.close();
			int res = connection.getResponseCode();
			System.out.println("Response code from server:" + res);
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			connection.disconnect();
		} catch (IOException e) {
			System.out.print("Open connection failed");
			return false;
		}
		return true;
	}
	
	public String sentJSONByPOSTGetResponse(final String url, final AbstractJanusDto json) {
		URL postURL;
		try {
			postURL = new URL(url);
		} catch (MalformedURLException e) {
			System.out.print("URL invalid:" + url);
			return "ERROR";
		}
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) postURL.openConnection();
			connection.setConnectTimeout(5000);// 5 secs
			connection.setReadTimeout(5000);// 5 secs
			try {
				connection.setRequestMethod(POST);
			} catch (ProtocolException e) {
				System.out.print("Protocol POST invalid o not permited");
				return "ERROR";
			}
			connection.setDoOutput(true);
			connection.setRequestProperty(CONTENT_TYPE, APPLICATION_TYPE_JSON);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			Gson gson = new GsonBuilder().create();
			out.write(gson.toJson(json));
			out.flush();
			out.close();
			int res = connection.getResponseCode();
			System.out.println("Response code from server:" + res);
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			String responseBody = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				responseBody += line;
			}
			connection.disconnect();
			JanusCreateSessionResponseDto response = gson.fromJson(responseBody, JanusCreateSessionResponseDto.class);
			return response.getData().getId();
		} catch (IOException e) {
			System.out.print("Open connection failed");
			return "ERROR";
		}
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
		public boolean liveOnly(final String backendToken, final String url, JanusLiveOnlyInfoDto liveOnlyInfo, final String admin_key) {

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
		public boolean chatRoom(final String backendToken, final String url, JanusChatRoomInfoDto chatRoomInfo, final String admin_key) {

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



}
