package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class ObjectCodec implements Codec<Object> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Object value) throws Exception {
		laser.writeClassAndObject(context, out, value);
	}

	@Override
	public Object decode(Laser laser, Context context, ByteArray in, Class<Object> type) throws Exception {
		return laser.readClassAndObject(context, in);
	}

}
