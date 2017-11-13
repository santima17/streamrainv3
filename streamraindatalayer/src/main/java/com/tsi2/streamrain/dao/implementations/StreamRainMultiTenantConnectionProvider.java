package com.tsi2.streamrain.dao.implementations;

import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.model.main.Tenants;

public class StreamRainMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

	private HashMap<String, StreamRainConnectionProvider> connectionsMap = new HashMap<String, StreamRainConnectionProvider>();

	public StreamRainMultiTenantConnectionProvider() {

		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		Criteria search = dbSession.createCriteria(Tenants.class);
		for (Tenants tenant : (List<Tenants>)search.list()) {
			connectionsMap.put(String.valueOf(tenant.getName()), new StreamRainConnectionProvider(tenant));
		}

	}

	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return null;
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(final String tenant) {
		return connectionsMap.get(tenant);
	}

}
