package org.louis.laser.codec.field;

import java.lang.reflect.Field;

import org.louis.laser.annotation.Optimization;
import org.louis.laser.codec.Codec;
import org.louis.laser.util.UnsafeUtil;

class FieldDescriptor {

	private Field field;
	private Class<?> type;
	private boolean plus;
	private Codec<Object> codec;

	public static FieldDescriptor get(Field field, Codec<Object> codec) {
		if (UnsafeUtil.available()) {
			return new UnsafeFieldDescriptor(field, codec);
		}
		return new FieldDescriptor(field, codec);
	}

	FieldDescriptor(Field field, Codec<Object> codec) {
		this.field = field;
		this.codec = codec;
		this.type = field.getType();
		this.plus = field.getAnnotation(Optimization.class) != null;
	}

	public Field field() {
		return field;
	}

	@SuppressWarnings("unchecked")
	public Class<Object> type() {
		return (Class<Object>) type;
	}

	public Codec<Object> codec() {
		return codec;
	}

	public boolean plus() {
		return plus;
	}

	public boolean getBoolean(Object obj) throws Exception {
		return field.getBoolean(obj);
	}

	public byte getByte(Object obj) throws Exception {
		return field.getByte(obj);
	}

	public char getChar(Object obj) throws Exception {
		return field.getChar(obj);
	}

	public short getShort(Object obj) throws Exception {
		return field.getShort(obj);
	}

	public int getInt(Object obj) throws Exception {
		return field.getInt(obj);
	}

	public long getLong(Object obj) throws Exception {
		return field.getLong(obj);
	}

	public float getFloat(Object obj) throws Exception {
		return field.getFloat(obj);
	}

	public double getDouble(Object obj) throws Exception {
		return field.getDouble(obj);
	}

	public Object getObject(Object obj) throws Exception {
		return field.get(obj);
	}

	public Object get(Object obj) throws Exception {
		if (type.isPrimitive()) {
			if (type == boolean.class) {
				return getBoolean(obj);
			} else if (type == byte.class) {
				return getByte(obj);
			} else if (type == char.class) {
				return getChar(obj);
			} else if (type == short.class) {
				return getShort(obj);
			} else if (type == int.class) {
				return getInt(obj);
			} else if (type == long.class) {
				return getLong(obj);
			} else if (type == float.class) {
				return getFloat(obj);
			} else if (type == double.class) {
				return getDouble(obj);
			}
		}
		return getObject(obj);
	}

	public void set(Object obj, Object value) throws Exception {
		if (type.isPrimitive()) {
			if (type == boolean.class) {
				setBoolean(obj, (boolean) value);
			} else if (type == byte.class) {
				setByte(obj, (byte) value);
			} else if (type == char.class) {
				setChar(obj, (char) value);
			} else if (type == short.class) {
				setShort(obj, (short) value);
			} else if (type == int.class) {
				setInt(obj, (int) value);
			} else if (type == long.class) {
				setLong(obj, (long) value);
			} else if (type == float.class) {
				setFloat(obj, (float) value);
			} else if (type == double.class) {
				setDouble(obj, (double) value);
			}
			return;
		}
		setObject(obj, value);
	}

	public void setObject(Object obj, Object value) throws Exception {
		field.set(obj, value);
	}

	public void setBoolean(Object obj, boolean value) throws Exception {
		field.setBoolean(obj, value);
	}

	public void setByte(Object obj, byte value) throws Exception {
		field.setByte(obj, value);
	}

	public void setChar(Object obj, char value) throws Exception {
		field.setChar(obj, value);
	}

	public void setShort(Object obj, short value) throws Exception {
		field.setShort(obj, value);
	}

	public void setInt(Object obj, int value) throws Exception {
		field.setInt(obj, value);
	}

	public void setLong(Object obj, long value) throws Exception {
		field.setLong(obj, value);
	}

	public void setFloat(Object obj, float value) throws Exception {
		field.setFloat(obj, value);
	}

	public void setDouble(Object obj, double value) throws Exception {
		field.setDouble(obj, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("fieldName=").append(field.getName());
		return builder.toString();
	}
}
