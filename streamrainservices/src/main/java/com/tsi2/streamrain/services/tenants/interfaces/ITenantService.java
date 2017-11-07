package com.tsi2.streamrain.services.tenants.interfaces;

import com.tsi2.streamrain.datatypes.tenant.TenantDto;

public interface ITenantService {

	boolean saveTenant(final TenantDto tenant);
	boolean existsUser(final String nickname, final String password);
}
