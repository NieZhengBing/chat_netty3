package com.nzb.netty3.server.scanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invoker {
	private Method method;

	private Object target;

	public static Invoker valueOf(Method method, Object target) {
		Invoker invoker = new Invoker();
		invoker.setMethod(method);
		invoker.setTarget(target);
		return invoker;
	}

	public Object invoker(Object... paramValue) {
		try {
			return method.invoke(target, paramValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
