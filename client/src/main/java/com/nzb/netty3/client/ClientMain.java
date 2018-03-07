package com.nzb.netty3.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nzb.netty3.client.swing.Swingclient;

public class ClientMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Swingclient swing = applicationContext.getBean(Swingclient.class);
		swing.setVisible(true);
	}

}
