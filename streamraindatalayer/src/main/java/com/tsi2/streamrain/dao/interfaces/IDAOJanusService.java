package com.tsi2.streamrain.dao.interfaces;

import java.util.Date;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public interface IDAOJanusService extends IDAOService {

	JanusCreationTokens getJanusByToken(final String token, final Date dateExpiration, final String tenantID);
}
