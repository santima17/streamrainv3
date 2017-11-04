package com.tsi2.streamrain.bussines.tenant.implementations;

import com.tsi2.streamrain.bussines.tenant.interfaces.IBLTenant;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLMainDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLTenantDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.dao.interfaces.IDAOTenantService;
import com.tsi2.streamrain.model.main.Tenants;

public class BLTenantImpl implements IBLTenant{

	IDAOService daoService = (StreamRainMySQLMainDAO) StremRainDataContextLoader.contextLoader().getBean("daoMainService");
	IDAOTenantService daoTenant = (StreamRainMySQLTenantDAO) StremRainDataContextLoader.contextLoader().getBean("daoTenant");
	
	public boolean saveTenant(final Tenants tenant) {
		daoService.save(tenant, null);
		return true; 
	}

	public boolean existsUser(final String nickname, final String password) {
		return daoTenant.findByNicknamePassword(nickname, password); 
	}

}
