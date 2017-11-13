package com.tsi2.streamrain.page.general.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.boot.json.GsonJsonParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tsi2.streamrain.datatypes.janus.JanusBackendTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateSessionDto;
import com.tsi2.streamrain.datatypes.janus.JanusCreateSessionResponseDto;

public class AbstractController {

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_TYPE_JSON = "application/json";
	private static final String POST = "POST";

	public boolean sentJSONByPOST(final String url, final JanusBackendTokenDto json) {
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
	
	public String sentJSONByPOSTGetResponse(final String url, final JanusCreateSessionDto json) {
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
			return response.getId();
		} catch (IOException e) {
			System.out.print("Open connection failed");
			return "ERROR";
		}
	}

}
