package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

@SuppressWarnings("rawtypes")
public class ClassCodec implements Codec<Class> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Class value) throws Exception {
		laser.writeClass(context, out, value);
		out.writeBoolean(value.isPrimitive());
	}

	@Override
	public Class decode(Laser laser, Context context, ByteArray in, Class<Class> type) throws Exception {
		Class clazz = laser.readClass(context, in);
		boolean isPrimitive = in.readBoolean();
		if (isPrimitive) {
			clazz = getPrimitiveClass(clazz);
		}
		return clazz;
	}

	public Class getPrimitiveClass(Class type) {
		if (type == Integer.class)
			return int.class;
		else if (type == Float.class)
			return float.class;
		else if (type == Boolean.class)
			return boolean.class;
		else if (type == Long.class)
			return long.class;
		else if (type == Byte.class)
			return byte.class;
		else if (type == Character.class)
			return char.class;
		else if (type == Short.class)
			return short.class;
		else if (type == Double.class)
			return double.class;
		else if (type == Void.class)
			return void.class;
		else
			return type;
	}
}
