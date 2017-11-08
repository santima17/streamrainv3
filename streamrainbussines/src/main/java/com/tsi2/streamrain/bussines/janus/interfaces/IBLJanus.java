package com.tsi2.streamrain.bussines.janus.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.JanusServers;

public interface IBLJanus {

	Integer saveJanusToken(JanusCreationTokens janusToken, String tenantID);

	boolean updateJanusToken(JanusCreationTokens janusToken, String tenantID);

	Integer saveJanusServer(JanusServers janusServers, String tokenJanusCreationTokens, String tenantID);

	List<String> getAllJanusAdminUrl(String tenantID);

	List<String> getAllJanusUrl(String tenantID);

}
