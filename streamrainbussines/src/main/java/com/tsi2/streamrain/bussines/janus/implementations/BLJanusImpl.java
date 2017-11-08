package com.tsi2.streamrain.bussines.janus.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		Date now = new Date();
		JanusCreationTokens janusCreationTokens = daoJanusService.getJanusByToken(tokenJanusCreationTokens, now, tenantID);
		if (janusCreationTokens == null) {
			return null;
		}
		janusServers.setJanusCreationTokens(janusCreationTokens);
		return (Integer) daoService.save(janusServers, tenantID);
	}

	public List<String> getAllJanusAdminUrl(String tenantID) {
		List<String> listResult = new ArrayList<String>();
		List<JanusServers> listJanus = daoJanusService.getAll(JanusServers.class, tenantID);
		for (JanusServers janus:listJanus) {
			listResult.add(janus.getAdminUrl());
		}
		return listResult;
	}
	
	public List<String> getAllJanusUrl(String tenantID){
		List<String> listResult = new ArrayList<String>();
		List<JanusServers> listJanus = daoJanusService.getAll(JanusServers.class, tenantID);
		for (JanusServers janus:listJanus) {
			listResult.add(janus.getJanusUrl());
		}
		return listResult;
	}

}
