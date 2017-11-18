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
import com.tsi2.streamrain.model.generator.UserSubscriptions;

public class BLJanusImpl implements IBLJanus { 

	IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
	IDAOJanusService daoJanusService = (StreamRainMySQLJanusDAO) StremRainDataContextLoader.contextLoader().getBean("daoJanusService");
	
	public Integer saveJanusToken(final JanusCreationTokens janusToken, final String tenantID) {
		return (Integer) daoService.save(janusToken, tenantID);
	}

	public boolean updateJanusToken(final JanusCreationTokens janusToken, final String tenantID) {
		return daoService.saveOrUpdate(janusToken, tenantID);	
	}

	public Integer saveJanusServer(final JanusServers janusServers, final String tokenJanusCreationTokens, final String tenantID) {
		Date now = new Date();
		JanusCreationTokens janusCreationTokens = daoJanusService.getJanusByToken(tokenJanusCreationTokens, now, tenantID);
		if (janusCreationTokens == null) {
			return null;
		}
		janusServers.setJanusCreationTokens(janusCreationTokens);
		return (Integer) daoService.save(janusServers, tenantID);
	}

	public List<String> getAllJanusAdminUrl(final String tenantID) {
		List<String> listResult = new ArrayList<String>();
		List<JanusServers> listJanus = daoJanusService.getAll(JanusServers.class, tenantID);
		for (JanusServers janus:listJanus) {
			listResult.add(janus.getAdminUrl());
		}
		return listResult;
	}
	
	public List<String> getAllJanusUrl(final String tenantID){
		List<String> listResult = new ArrayList<String>();
		List<JanusServers> listJanus = daoJanusService.getAll(JanusServers.class, tenantID);
		for (JanusServers janus:listJanus) {
			listResult.add(janus.getJanusUrl());
		}
		return listResult;
	}

	public List<String> getAllAvailablesUserTokens(final String tenantID) {
		List<String> tokens = new ArrayList<String>();
		for(UserSubscriptions us : daoJanusService.getAllAvailablesUserSubscriptions(tenantID)) {
			tokens.add(us.getJanusUserToken());
		}
		return tokens;
	}

	public List<JanusServers> getAllJanusServerActive(final String tenantID) {
		JanusServers janusServers = new JanusServers();
		janusServers.setIsEnable(true);
		return daoJanusService.getAllByExample(JanusServers.class, janusServers, tenantID);
	}

	public List<JanusServers> getAllJanusServer(final String tenantID) {
		return daoJanusService.getAll(JanusServers.class, tenantID);
	}

	public JanusServers getJanusServer(final Integer janusServerId, final String tenantID) {
		return daoJanusService.get(JanusServers.class, janusServerId, tenantID);
	}

	public List<JanusCreationTokens> getAllJanusCreateToken(final String tenantID) {
		return daoJanusService.getAll(JanusCreationTokens.class, tenantID);
	}

	public Integer getJanusServerIdForJanusCreationToken(String tokenJanusCreationTokens, String tenantID) {
		JanusCreationTokens janusCreationTokensExample = new JanusCreationTokens();
		janusCreationTokensExample.setJanusToken(tokenJanusCreationTokens);
		List<JanusCreationTokens> list = daoJanusService.getAllByExample(JanusCreationTokens.class, janusCreationTokensExample, tenantID);
		if (!list.isEmpty()) {
			JanusCreationTokens janusCreationTokens = list.get(0);
			JanusServers janusServersExample = new JanusServers();
			janusServersExample.setJanusCreationTokens(janusCreationTokens);
			List<JanusServers> listJanusServer = daoJanusService.getAllByExample(JanusServers.class, janusServersExample, tenantID);
			if (!listJanusServer.isEmpty()) {
				return listJanusServer.get(0).getId();
			}
		}
		return null;
	}

}
