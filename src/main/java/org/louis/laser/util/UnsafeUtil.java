package org.louis.laser.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import sun.misc.Unsafe;

@SuppressWarnings({ "unchecked", "deprecation" })
public final class UnsafeUtil {

	private static Unsafe unsafe;
	private static boolean available;
	private static Map<Field, Long> fieldOffsets = new HashMap<Field, Long>();

	private UnsafeUtil() {
	}

	static {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			unsafe = (Unsafe) theUnsafe.get(null);
			available = true;
		} catch (Exception e) {
			available = false;
		}
	}

	public static <T> T allocateInstance(Class<T> type) throws InstantiationException {
		return (T) unsafe().allocateInstance(type);
	}

	public static void copyMemory(long arg0, long arg1, long arg2) {
		unsafe().copyMemory(arg0, arg1, arg2);
	}

	public static void copyMemory(Object arg0, long arg1, Object arg2, long arg3, long arg4) {
		unsafe().copyMemory(arg0, arg1, arg2, arg3, arg4);
	}

	public static void park(boolean arg0, long arg1) {
		unsafe().park(arg0, arg1);
	}

	public static void unpark(Object arg0) {
		unsafe().unpark(arg0);
	}

	public static Object getObject(Object arg0, int arg1) {
		return unsafe().getObject(arg0, arg1);
	}

	public static Object getObject(Object arg0, long arg1) {
		return unsafe().getObject(arg0, arg1);
	}

	public static void putObject(Object arg0, long arg1, Object arg2) {
		unsafe().putObject(arg0, arg1, arg2);
	}

	public static void putObject(Object arg0, int arg1, Object arg2) {
		unsafe().putObject(arg0, arg1, arg2);
	}

	public static boolean getBoolean(Object arg0, long arg1) {
		return unsafe().getBoolean(arg0, arg1);
	}

	public static boolean getBoolean(Object arg0, int arg1) {
		return unsafe().getBoolean(arg0, arg1);
	}

	public static void putBoolean(Object arg0, long arg1, boolean arg2) {
		unsafe().putBoolean(arg0, arg1, arg2);
	}

	public static void putBoolean(Object arg0, int arg1, boolean arg2) {
		unsafe().putBoolean(arg0, arg1, arg2);
	}

	public static byte getByte(Object arg0, int arg1) {
		return unsafe().getByte(arg0, arg1);
	}

	public static byte getByte(long arg0) {
		return unsafe().getByte(arg0);
	}

	public static byte getByte(Object arg0, long arg1) {
		return unsafe().getByte(arg0, arg1);
	}

	public static void putByte(long arg0, byte arg1) {
		unsafe().putByte(arg0, arg1);
	}

	public static void putByte(Object arg0, int arg1, byte arg2) {
		unsafe().putByte(arg0, arg1, arg2);
	}

	public static void putByte(Object arg0, long arg1, byte arg2) {
		unsafe().putByte(arg0, arg1, arg2);
	}

	public static short getShort(Object arg0, long arg1) {
		return unsafe().getShort(arg0, arg1);
	}

	public static short getShort(Object arg0, int arg1) {
		return unsafe().getShort(arg0, arg1);
	}

	public static short getShort(long arg0) {
		return unsafe().getShort(arg0);
	}

	public static void putShort(Object arg0, int arg1, short arg2) {
		unsafe().putShort(arg0, arg1, arg2);
	}

	public static void putShort(Object arg0, long arg1, short arg2) {
		unsafe().putShort(arg0, arg1, arg2);
	}

	public static void putShort(long arg0, short arg1) {
		unsafe().putShort(arg0, arg1);
	}

	public static char getChar(Object arg0, int arg1) {
		return unsafe().getChar(arg0, arg1);
	}

	public static char getChar(long arg0) {
		return unsafe().getChar(arg0);
	}

	public static char getChar(Object arg0, long arg1) {
		return unsafe().getChar(arg0, arg1);
	}

	public static void putChar(long arg0, char arg1) {
		unsafe().putChar(arg0, arg1);
	}

	public static void putChar(Object arg0, int arg1, char arg2) {
		unsafe().putChar(arg0, arg1, arg2);
	}

	public static void putChar(Object arg0, long arg1, char arg2) {
		unsafe().putChar(arg0, arg1, arg2);
	}

	public static int getInt(Object arg0, long arg1) {
		return unsafe().getInt(arg0, arg1);
	}

	public static int getInt(long arg0) {
		return unsafe().getInt(arg0);
	}

	public static int getInt(Object arg0, int arg1) {
		return unsafe().getInt(arg0, arg1);
	}

	public static void putInt(Object arg0, int arg1, int arg2) {
		unsafe().putInt(arg0, arg1, arg2);
	}

	public static void putInt(long arg0, int arg1) {
		unsafe().putInt(arg0, arg1);
	}

	public static void putInt(Object arg0, long arg1, int arg2) {
		unsafe().putInt(arg0, arg1, arg2);
	}

	public static long getLong(Object arg0, long arg1) {
		return unsafe().getLong(arg0, arg1);
	}

	public static long getLong(long arg0) {
		return unsafe().getLong(arg0);
	}

	public static long getLong(Object arg0, int arg1) {
		return unsafe().getLong(arg0, arg1);
	}

	public static void putLong(long arg0, long arg1) {
		unsafe().putLong(arg0, arg1);
	}

	public static void putLong(Object arg0, long arg1, long arg2) {
		unsafe().putLong(arg0, arg1, arg2);
	}

	public static void putLong(Object arg0, int arg1, long arg2) {
		unsafe().putLong(arg0, arg1, arg2);
	}

	public static float getFloat(Object arg0, int arg1) {
		return unsafe().getFloat(arg0, arg1);
	}

	public static float getFloat(long arg0) {
		return unsafe().getFloat(arg0);
	}

	public static float getFloat(Object arg0, long arg1) {
		return unsafe().getFloat(arg0, arg1);
	}

	public static void putFloat(Object arg0, int arg1, float arg2) {
		unsafe().putFloat(arg0, arg1, arg2);
	}

	public static void putFloat(long arg0, float arg1) {
		unsafe().putFloat(arg0, arg1);
	}

	public static void putFloat(Object arg0, long arg1, float arg2) {
		unsafe().putFloat(arg0, arg1, arg2);
	}

	public static double getDouble(long arg0) {
		return unsafe().getDouble(arg0);
	}

	public static double getDouble(Object arg0, int arg1) {
		return unsafe().getDouble(arg0, arg1);
	}

	public static double getDouble(Object arg0, long arg1) {
		return unsafe().getDouble(arg0, arg1);
	}

	public static void putDouble(Object arg0, int arg1, double arg2) {
		unsafe().putDouble(arg0, arg1, arg2);
	}

	public static void putDouble(long arg0, double arg1) {
		unsafe().putDouble(arg0, arg1);
	}

	public static void putDouble(Object arg0, long arg1, double arg2) {
		unsafe().putDouble(arg0, arg1, arg2);
	}

	public static Object getObjectVolatile(Object arg0, long arg1) {
		return unsafe().getObjectVolatile(arg0, arg1);
	}

	public static void putObjectVolatile(Object arg0, long arg1, Object arg2) {
		unsafe().putObjectVolatile(arg0, arg1, arg2);
	}

	public static boolean getBooleanVolatile(Object arg0, long arg1) {
		return unsafe().getBooleanVolatile(arg0, arg1);
	}

	public static void putBooleanVolatile(Object arg0, long arg1, boolean arg2) {
		unsafe().putBooleanVolatile(arg0, arg1, arg2);
	}

	public static byte getByteVolatile(Object arg0, long arg1) {
		return unsafe().getByteVolatile(arg0, arg1);
	}

	public static void putByteVolatile(Object arg0, long arg1, byte arg2) {
		unsafe().putByteVolatile(arg0, arg1, arg2);
	}

	public static short getShortVolatile(Object arg0, long arg1) {
		return unsafe().getShortVolatile(arg0, arg1);
	}

	public static void putShortVolatile(Object arg0, long arg1, short arg2) {
		unsafe().putShortVolatile(arg0, arg1, arg2);
	}

	public static char getCharVolatile(Object arg0, long arg1) {
		return unsafe().getCharVolatile(arg0, arg1);
	}

	public static void putCharVolatile(Object arg0, long arg1, char arg2) {
		unsafe().putCharVolatile(arg0, arg1, arg2);
	}

	public static int getIntVolatile(Object arg0, long arg1) {
		return unsafe().getIntVolatile(arg0, arg1);
	}

	public static void putIntVolatile(Object arg0, long arg1, int arg2) {
		unsafe().putIntVolatile(arg0, arg1, arg2);
	}

	public static long getLongVolatile(Object arg0, long arg1) {
		return unsafe().getLongVolatile(arg0, arg1);
	}

	public static void putLongVolatile(Object arg0, long arg1, long arg2) {
		unsafe().putLongVolatile(arg0, arg1, arg2);
	}

	public static float getFloatVolatile(Object arg0, long arg1) {
		return unsafe().getFloatVolatile(arg0, arg1);
	}

	public static void putFloatVolatile(Object arg0, long arg1, float arg2) {
		unsafe().putFloatVolatile(arg0, arg1, arg2);
	}

	public static double getDoubleVolatile(Object arg0, long arg1) {
		return unsafe().getDoubleVolatile(arg0, arg1);
	}

	public static void putDoubleVolatile(Object arg0, long arg1, double arg2) {
		unsafe().putDoubleVolatile(arg0, arg1, arg2);
	}

	public static long getAddress(long arg0) {
		return unsafe().getAddress(arg0);
	}

	public static void putAddress(long arg0, long arg1) {
		unsafe().putAddress(arg0, arg1);
	}

	public static boolean compareAndSwapObject(Object arg0, long arg1, Object arg2, Object arg3) {
		return unsafe().compareAndSwapObject(arg0, arg1, arg2, arg3);
	}

	public static boolean compareAndSwapLong(Object arg0, long arg1, long arg2, long arg3) {
		return unsafe().compareAndSwapLong(arg0, arg1, arg2, arg3);
	}

	public static boolean compareAndSwapInt(Object arg0, long arg1, int arg2, int arg3) {
		return unsafe().compareAndSwapInt(arg0, arg1, arg2, arg3);
	}

	public static void putOrderedObject(Object arg0, long arg1, Object arg2) {
		unsafe().putOrderedObject(arg0, arg1, arg2);
	}

	public static void putOrderedLong(Object arg0, long arg1, long arg2) {
		unsafe().putOrderedLong(arg0, arg1, arg2);
	}

	public static void putOrderedInt(Object arg0, long arg1, int arg2) {
		unsafe().putOrderedInt(arg0, arg1, arg2);
	}

	public static void throwException(java.lang.Throwable arg0) {
		unsafe().throwException(arg0);
	}

	public static <T> Class<T> defineClass(String arg0, byte[] arg1, int arg2, int arg3) {
		return unsafe().defineClass(arg0, arg1, arg2, arg3);
	}

	public static <T> Class<T> defineClass(String arg0, byte[] arg1, int arg2, int arg3, ClassLoader arg4, java.security.ProtectionDomain arg5) {
		return unsafe().defineClass(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public static <T> Object staticFieldBase(Class<T> arg0) {
		return unsafe().staticFieldBase(arg0);
	}

	public static Object staticFieldBase(java.lang.reflect.Field arg0) {
		return unsafe().staticFieldBase(arg0);
	}

	public static int fieldOffset(java.lang.reflect.Field arg0) {
		return unsafe().fieldOffset(arg0);
	}

	public static long objectFieldOffset(Field field) {
		Long offset = fieldOffsets.get(field);
		if (offset == null) {
			offset = unsafe.objectFieldOffset(field);
			fieldOffsets.put(field, offset);
		}
		return offset;
	}

	public static long staticFieldOffset(java.lang.reflect.Field arg0) {
		return unsafe().staticFieldOffset(arg0);
	}

	public static <T> void ensureClassInitialized(Class<T> arg0) {
		unsafe().ensureClassInitialized(arg0);
	}

	public static <T> boolean shouldBeInitialized(Class<T> arg0) {
		return unsafe().shouldBeInitialized(arg0);
	}

	public static int addressSize() {
		return unsafe().addressSize();
	}

	public static long allocateMemory(long arg0) {
		return unsafe().allocateMemory(arg0);
	}

	public static <T> int arrayBaseOffset(Class<T> arg0) {
		return unsafe().arrayBaseOffset(arg0);
	}

	public static <T> int arrayIndexScale(Class<T> arg0) {
		return unsafe().arrayIndexScale(arg0);
	}

	public static <T> Class<T> defineAnonymousClass(Class<T> arg0, byte[] arg1, Object[] arg2) {
		return unsafe().defineAnonymousClass(arg0, arg1, arg2);
	}

	public static void freeMemory(long arg0) {
		unsafe().freeMemory(arg0);
	}

	public static int getLoadAverage(double[] arg0, int arg1) {
		return unsafe().getLoadAverage(arg0, arg1);
	}

	public static void monitorEnter(Object arg0) {
		unsafe().monitorEnter(arg0);
	}

	public static void monitorExit(Object arg0) {
		unsafe().monitorExit(arg0);
	}

	public static int pageSize() {
		return unsafe().pageSize();
	}

	public static long reallocateMemory(long arg0, long arg1) {
		return unsafe().reallocateMemory(arg0, arg1);
	}

	public static void setMemory(Object arg0, long arg1, long arg2, byte arg3) {
		unsafe().setMemory(arg0, arg1, arg2, arg3);
	}

	public static void setMemory(long arg0, long arg1, byte arg2) {
		unsafe().setMemory(arg0, arg1, arg2);
	}

	public static boolean tryMonitorEnter(Object arg0) {
		return unsafe().tryMonitorEnter(arg0);
	}

	public static boolean available() {
		return available;
	}

	public static Unsafe unsafe() {
		if (!available) {
			throw new UnsupportedOperationException();
		}
		return unsafe;
	}

	public static void main(String[] args) {
		UnsafeUtil.unsafe();
	}
}
