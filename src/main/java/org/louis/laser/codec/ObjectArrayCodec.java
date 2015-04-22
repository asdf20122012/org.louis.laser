package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class ObjectArrayCodec implements Codec<Object[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Object[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (Object value : values) {
				if (out.writeBoolean(value == null)) {
					continue;
				}
				laser.writeClassAndObject(context, out, value);
			}
		}
	}

	@Override
	public Object[] decode(Laser laser, Context context, ByteArray in, Class<Object[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		Object[] objs = new Object[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				if (in.readBoolean()) {
					continue;
				}
				objs[i] = laser.readClassAndObject(context, in);
			}
		}
		return objs;
	}

}
