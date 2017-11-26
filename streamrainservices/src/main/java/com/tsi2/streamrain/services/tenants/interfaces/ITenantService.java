package com.tsi2.streamrain.services.tenants.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.tenant.TenantDto;

public interface ITenantService {

	boolean saveTenant(final TenantDto tenant);
	boolean existsUser(final String nickname, final String password);
	TenantDto getTenantByName(final String name);
	List<TenantDto> getAllTenant();
	boolean blockUser(final String userNickName, final String tenantName);
	boolean blockContent(final Integer contentId, final String tenantId);
	boolean updateTenant(final TenantDto tenant);
	boolean unblockUser(final String userNickName, final String tenantId);
	boolean unblockContent(final Integer contentId, final String tenantId);
}
