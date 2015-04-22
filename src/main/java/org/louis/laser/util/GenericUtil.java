package org.louis.laser.util;

import java.util.Collection;

public class GenericUtil {

	public static <T> boolean sameGeneric(Collection<T> values) {
		if (values.size() == 0 || values.size() == 1) {
			return true;
		}
		Class<?> lastGenericType = null;
		for (T t : values) {
			if (lastGenericType == null) {
				lastGenericType = t.getClass();
				continue;
			}
			if (lastGenericType != t.getClass()) {
				return false;
			}
			lastGenericType = t.getClass();
		}
		return true;
	}

	public static <T> boolean sameGeneric(T[] values) {
		if (values.length == 0 || values.length == 1) {
			return true;
		}
		Class<?> lastGenericType = null;
		for (T t : values) {
			if (lastGenericType == null) {
				lastGenericType = t.getClass();
				continue;
			}
			if (lastGenericType != t.getClass()) {
				return false;
			}
			lastGenericType = t.getClass();
		}
		return true;
	}

}
