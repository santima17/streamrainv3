package com.tsi2.streamrain.bussines.janus.implementations;

import com.tsi2.streamrain.bussines.janus.interfaces.IBLJanus;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public class BLJanusImpl implements IBLJanus {

	IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
	
	public Integer saveJanusToken(JanusCreationTokens janusToken, String tenantID) {
		return (Integer) daoService.save(janusToken, tenantID);
	}

	public boolean updateJanusToken(JanusCreationTokens janusToken, String tenantID) {
		return daoService.saveOrUpdate(janusToken, tenantID);	
	}

}
