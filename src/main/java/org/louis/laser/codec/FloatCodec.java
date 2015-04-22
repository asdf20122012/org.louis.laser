package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class FloatCodec implements Codec<Float> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Float value) throws Exception {
		out.writeFloat(value);
	}

	@Override
	public Float decode(Laser laser, Context context, ByteArray in, Class<Float> type) throws Exception {
		return in.readFloat();
	}


}
