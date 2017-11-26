package com.tsi2.streamrain.bussines.tenant.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.main.Tenants;

public interface IBLTenant {
	
	public boolean saveTenant(final Tenants tenant);

	public boolean existsUser(final String nickname, final String password);

	public Tenants getTenantByName(final String name);

	public List<Tenants> getAllTenant();

	public boolean blockUser(final String userNickName, final String tenantId);

	public boolean blockContent(final Integer contentId, final String tenantId);

	public boolean updateTenant(final Tenants tenant);

	public boolean unblockUser(final String userNickName, final String tenantId);

	public boolean unblockContent(final Integer contentId, final String tenantId);

}
