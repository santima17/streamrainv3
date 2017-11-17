/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsi2.streamrain.context;

import org.hibernate.Session;
/**
 *
 * @author santiago
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.util.StringUtils;


/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author santima17
 */
public class DBHibernateUtil {

	private static Session sessionFactoryGenerator;
	private static final SessionFactory sessionFactoryMain;
	private static String currentTenantID = "";

	static {
		try {
			sessionFactoryGenerator = null;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactoryGenerator creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	static {
		try {
			sessionFactoryMain = new Configuration().configure("hibernate_main.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactoryGenerator creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static Session getSessionFactoryGenerator(final String tenantID) {
		if (StringUtils.isEmpty(currentTenantID) || (!tenantID.equals(currentTenantID))) {
			currentTenantID = tenantID;
			sessionFactoryGenerator = (Session) new Configuration().configure("hibernate_gen.cfg.xml")
					.buildSessionFactory().withOptions().tenantIdentifier(tenantID).openSession();
		}
		return sessionFactoryGenerator;
	}

	public static Session getSessionFactoryMain() {
		return sessionFactoryMain.openSession();
	}
	
}
