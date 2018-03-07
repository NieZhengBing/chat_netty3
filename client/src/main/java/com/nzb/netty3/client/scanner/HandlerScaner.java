package com.nzb.netty3.client.scanner;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.nzb.netty3.common.core.annotion.SocketCommand;
import com.nzb.netty3.common.core.annotion.SocketModule;

public class HandlerScaner implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<? extends Object> clazz = bean.getClass();
		Class<?>[] interfaces = clazz.getInterfaces();

		if (interfaces != null && interfaces.length > 0) {
			for (Class<?> interFace : interfaces) {
				SocketModule socketModule = interFace.getAnnotation(SocketModule.class);
				if (socketModule == null) {
					continue;
				}

				Method[] methods = interFace.getMethods();
				if (methods != null && methods.length > 0) {
					for (Method method : methods) {
						SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
						if (socketCommand == null) {
							continue;
						}

						short module = socketModule.module();
						short cmd = socketCommand.cmd();
						if (InvokerHoler.getInvoker(module, cmd) == null) {
							InvokerHoler.addInvoker(module, cmd, Invoker.valueOf(method, bean));
						} else {
							System.out.println("multiple command: " + "module: " + module + " cmd: " + cmd);
						}
					}
				}
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
