package com.tsi2.streamrain.bussines.tenant.implementations;

import com.tsi2.streamrain.bussines.tenant.interfaces.IBLTenant;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLMainDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.model.main.Tenants;

public class BLTenantImpl implements IBLTenant{

	public boolean saveTenant(Tenants tenant) {
		IDAOService daoService = (StreamRainMySQLMainDAO) StremRainDataContextLoader.contextLoader().getBean("daoMainService");
		daoService.save(tenant, null);
		return true; 
	}

}
