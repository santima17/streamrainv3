package com.tsi2.streamrain.bussines.tenant.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.tenant.interfaces.IBLTenant;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLMainDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLTenantDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.dao.interfaces.IDAOTenantService;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.Users;
import com.tsi2.streamrain.model.main.Tenants;

public class BLTenantImpl implements IBLTenant{

	IDAOService daoMainService = (StreamRainMySQLMainDAO) StremRainDataContextLoader.contextLoader().getBean("daoMainService");
	IDAOTenantService daoTenant = (StreamRainMySQLTenantDAO) StremRainDataContextLoader.contextLoader().getBean("daoTenant");
	IDAOUserService daoUserService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
	IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
	
	public boolean saveTenant(final Tenants tenant) {
		daoMainService.save(tenant, null);
		return true; 
	}

	public boolean existsUser(final String nickname, final String password) {
		return daoTenant.findByNicknamePassword(nickname, password); 
	}

	public Tenants getTenantByName(final String name) {
		Tenants example = new Tenants();
		example.setName(name);
		return daoTenant.getAllByExample(Tenants.class, example, null).get(0);
	}

	public List<Tenants> getAllTenant() {
		return daoTenant.getAll(Tenants.class, null);
	}

	public boolean blockUser(final String userNickName, final String tenantId) {
		Users users = daoUserService.getUserByNickname(userNickName, tenantId);
		users.setBlocked(true);
		return daoUserService.saveOrUpdate(users, tenantId);
	}

	public boolean blockContent(Integer contentId, String tenantId) {
		Contents content = daoService.get(Contents.class, contentId, tenantId);
		content.setBlocked(true);
		return daoService.saveOrUpdate(content, tenantId);
	}

	public boolean updateTenant(final Tenants tenant) {
		return daoMainService.saveOrUpdate(tenant, null);
	}

}
