package com.tsi2.streamrain.bussines.janus.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.JanusServers;

public interface IBLJanus {

	Integer saveJanusToken(final JanusCreationTokens janusToken, final String tenantID);

	boolean updateJanusToken(final JanusCreationTokens janusToken, final String tenantID);

	Integer saveJanusServer(final JanusServers janusServers, final String tokenJanusCreationTokens, final String tenantID);

	List<String> getAllJanusAdminUrl(final String tenantID);

	List<String> getAllJanusUrl(final String tenantID);

	List<String> getAllAvailablesUserTokens(final String tenantID);

}
