package org.louis.laser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Context {

	private Map<Class<?>, Integer> typeToHeaders = new ConcurrentHashMap<Class<?>, Integer>();
	private Map<Integer, Class<?>> headerToTypes = new ConcurrentHashMap<Integer, Class<?>>();

	public int addHeader(Class<?> type, int header) {
		typeToHeaders.put(type, header);
		return header;
	}

	public Integer getHeader(Class<?> type) {
		return typeToHeaders.get(type);
	}

	public Class<?> getType(int header) {
		return headerToTypes.get(header);
	}

	public void addType(int header, Class<?> type) {
		headerToTypes.put(header, type);
	}

	public void reset() {
		typeToHeaders.clear();
		headerToTypes.clear();
	}

}
