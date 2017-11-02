package com.tsi2.streamrain.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StremRainUserServiceContextLoader {

	private static ApplicationContext context = null;


	public static ApplicationContext contextLoader() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("classpath*:**/services-context.xml");
		}
		return context;
	}
}
