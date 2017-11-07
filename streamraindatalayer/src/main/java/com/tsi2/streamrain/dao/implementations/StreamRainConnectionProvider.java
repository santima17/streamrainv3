package com.tsi2.streamrain.dao.implementations;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import com.tsi2.streamrain.model.main.Tenants;

public class StreamRainConnectionProvider implements ConnectionProvider {

	private final BasicDataSource basicDataSource = new BasicDataSource();

	public StreamRainConnectionProvider(final Tenants connInfo) {
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://"+connInfo.getDbIp()+":"+connInfo.getDbPort()+"/"+connInfo.getName()+"?zeroDateTimeBehavior=convertToNull");
		basicDataSource.setUsername(connInfo.getDbUser());
		basicDataSource.setPassword(connInfo.getDbPassword());
		basicDataSource.setInitialSize(2);
		basicDataSource.setDefaultAutoCommit(true);
		basicDataSource.setMaxActive(100);
	}

	public boolean isUnwrappableAs(final Class arg0) {
		return false;
	}

	public <T> T unwrap(final Class<T> arg0) {
		return null;
	}

	public void closeConnection(final Connection arg0) throws SQLException {
		arg0.close();
		
	}

	public Connection getConnection() throws SQLException {
		return basicDataSource.getConnection();
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}
