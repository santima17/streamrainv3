package com.tsi2.streamrain.services.tenants.implementations;

import com.tsi2.streamrain.bussines.tenant.interfaces.IBLTenant;
import com.tsi2.streamrain.bussines.tenant.implementations.BLTenantImpl;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.tenant.TenantConverter;
import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.model.main.Tenants;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;

public class TenantServiceImpl implements ITenantService{

	
	IBLTenant tenantBussines = (BLTenantImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("tenantBussines");
	IConverter<TenantDto, Tenants> tenantConverter = (TenantConverter)StremRainFacadesContextLoader.contextLoader().getBean("tenantConverter");
	
	public boolean saveTenant(final TenantDto tenant) {
		tenantBussines.saveTenant((Tenants)tenantConverter.deConverter(tenant));
		return false;
	}
	@Override
	public boolean existsUser(final String nickname, final String password) {
		return tenantBussines.existsUser(nickname, password);
	}


}
