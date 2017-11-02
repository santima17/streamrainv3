package com.tsi2.streamrain.dao.implementations;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class StreamRainTenantResolverImpl implements CurrentTenantIdentifierResolver {

	public String resolveCurrentTenantIdentifier() {
		return "generator";
	}

	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return false;
	}



}
