package com.tsi2.streamrain.dao.interfaces;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public interface IDAOJanusService extends IDAOService {

	JanusCreationTokens getJanusByToken(final String token, final String tenantID);
}
