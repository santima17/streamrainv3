package com.tsi2.streamrain.bussines.janus.interfaces;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public interface IBLJanus {

	Integer saveJanusToken(JanusCreationTokens janusToken, String tenantID);

	boolean updateJanusToken(JanusCreationTokens janusToken, String tenantID);

}
