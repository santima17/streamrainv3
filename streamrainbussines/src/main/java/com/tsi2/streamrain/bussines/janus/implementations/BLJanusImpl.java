package com.tsi2.streamrain.bussines.janus.implementations;

import com.tsi2.streamrain.bussines.janus.interfaces.IBLJanus;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLJanusDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOJanusService;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.JanusServers;

public class BLJanusImpl implements IBLJanus {

	IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
	IDAOJanusService daoJanusService = (StreamRainMySQLJanusDAO) StremRainDataContextLoader.contextLoader().getBean("daoJanusService");
	
	public Integer saveJanusToken(JanusCreationTokens janusToken, String tenantID) {
		return (Integer) daoService.save(janusToken, tenantID);
	}

	public boolean updateJanusToken(JanusCreationTokens janusToken, String tenantID) {
		return daoService.saveOrUpdate(janusToken, tenantID);	
	}

	public Integer saveJanusServer(JanusServers janusServers, String tokenJanusCreationTokens, String tenantID) {
		JanusCreationTokens janusCreationTokens = daoJanusService.getJanusByToken(tokenJanusCreationTokens, tenantID);
		if (janusCreationTokens == null) {
			return null;
		}
		janusServers.setJanusCreationTokens(janusCreationTokens);
		return (Integer) daoService.save(janusServers, tenantID);
	}

}
