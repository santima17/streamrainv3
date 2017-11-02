package com.tsi2.streamrain.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StremRainDataContextLoader {
	
	private static ApplicationContext context = null;


	public static ApplicationContext contextLoader() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("classpath*:**/data-context.xml");
		}
		return context;
	}

}
