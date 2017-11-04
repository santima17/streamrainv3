package com.tsi2.streamrain.bussines.content.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.content.interfaces.IBLContent;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.model.generator.Contents;

public class BLContentImpl implements IBLContent {

	public boolean saveContent(final Contents contents, final String tenantID) {
		IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
		daoService.save(contents, tenantID);
		/*for(Iterator it = contents.getContentCastses().iterator(); it.hasNext();) {
		    ContentCasts x = (ContentCasts)it.next();
		    daoService.save(x, tenantID);
		}*/
		return true; 
	}

	public List<Contents> getAllContents(String tenantID) {
		IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
		return daoService.getAll(Contents.class, tenantID);
	}
	

}
