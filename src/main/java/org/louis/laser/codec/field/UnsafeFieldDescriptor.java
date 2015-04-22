package org.louis.laser.codec.field;

import java.lang.reflect.Field;

import org.louis.laser.codec.Codec;
import org.louis.laser.util.UnsafeUtil;

class UnsafeFieldDescriptor extends FieldDescriptor {

	private long offset = -1;

	UnsafeFieldDescriptor(Field field, Codec<Object> codec) {
		super(field, codec);
		offset = UnsafeUtil.unsafe().objectFieldOffset(field);
	}

	public boolean getBoolean(Object obj) throws Exception {
		return UnsafeUtil.getBoolean(obj, offset);
	}

	public byte getByte(Object obj) throws Exception {
		return UnsafeUtil.getByte(obj, offset);
	}

	public char getChar(Object obj) throws Exception {
		return UnsafeUtil.getChar(obj, offset);
	}

	public short getShort(Object obj) throws Exception {
		return UnsafeUtil.getShort(obj, offset);
	}

	@Override
	public int getInt(Object obj) throws Exception {
		return UnsafeUtil.getInt(obj, offset);
	}

	public long getLong(Object obj) throws Exception {
		return UnsafeUtil.getLong(obj, offset);
	}

	public float getFloat(Object obj) throws Exception {
		return UnsafeUtil.getFloat(obj, offset);
	}

	public double getDouble(Object obj) throws Exception {
		return UnsafeUtil.getDouble(obj, offset);
	}

	public Object getObject(Object obj) throws Exception {
		return UnsafeUtil.getObject(obj, offset);
	}

	public void setObject(Object obj, Object value) throws Exception {
		UnsafeUtil.putObject(obj, offset, value);
	}

	public void setBoolean(Object obj, boolean value) throws Exception {
		UnsafeUtil.putBoolean(obj, offset, value);
	}

	public void setByte(Object obj, byte value) throws Exception {
		UnsafeUtil.putByte(obj, offset, value);
	}

	public void setChar(Object obj, char value) throws Exception {
		UnsafeUtil.putChar(obj, offset, value);
	}

	public void setShort(Object obj, short value) throws Exception {
		UnsafeUtil.putShort(obj, offset, value);
	}

	public void setInt(Object obj, int value) throws Exception {
		UnsafeUtil.putInt(obj, offset, value);
	}

	public void setLong(Object obj, long value) throws Exception {
		UnsafeUtil.putLong(obj, offset, value);
	}

	public void setFloat(Object obj, float value) throws Exception {
		UnsafeUtil.putFloat(obj, offset, value);
	}

	public void setDouble(Object obj, double value) throws Exception {
		UnsafeUtil.putDouble(obj, offset, value);
	}
}
