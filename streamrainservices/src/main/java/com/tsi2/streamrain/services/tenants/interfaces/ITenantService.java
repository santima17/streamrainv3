package com.tsi2.streamrain.services.tenants.interfaces;

import com.tsi2.streamrain.datatypes.tenant.TenantDto;

public interface ITenantService {

	boolean saveTenant(final TenantDto tenant);
	String getCurrentTenant();
	void setCurrentTenant(final String currentTenant);
}
