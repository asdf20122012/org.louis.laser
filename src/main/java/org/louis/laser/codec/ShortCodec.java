package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class ShortCodec implements Codec<Short> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Short value) throws Exception {
		out.writeShort(value);
	}

	@Override
	public Short decode(Laser laser, Context context, ByteArray in, Class<Short> type) throws Exception {
		return in.readShort();
	}


}