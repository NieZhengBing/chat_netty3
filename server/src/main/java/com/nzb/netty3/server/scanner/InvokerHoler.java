package com.nzb.netty3.server.scanner;

import java.util.HashMap;
import java.util.Map;

public class InvokerHoler {
	private static Map<Short, Map<Short, Invoker>> invokers = new HashMap<Short, Map<Short, Invoker>>();

	public static void addInvoker(Short module, Short cmd, Invoker invoker) {
		Map<Short, Invoker> map = invokers.get(module);
		if (map == null) {
			map = new HashMap<Short, Invoker>();
			invokers.put(module, map);
		}
		map.put(cmd, invoker);
	}

	public static Invoker getInvoker(Short module, short cmd) {
		Map<Short, Invoker> map = invokers.get(module);
		if (map != null) {
			return map.get(cmd);
		}
		return null;
	}

}
