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

	private String currentTenant;
	
	public boolean saveTenant(final TenantDto tenant) {
		IBLTenant tenantBussines = (BLTenantImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("tenantBussines");
		IConverter tenantConverter = (TenantConverter)StremRainFacadesContextLoader.contextLoader().getBean("tenantConverter");
		tenantBussines.saveTenant((Tenants)tenantConverter.deConverter(tenant));
		return false;
	}
	
	public String getCurrentTenant() {
		return currentTenant;
	}

	public void setCurrentTenant(final String currentTenant) {
		this.currentTenant = currentTenant;
}

}
