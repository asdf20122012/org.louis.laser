package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class LongCodec implements Codec<Long> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Long value) throws Exception {
		out.writeLong(value);
	}

	@Override
	public Long decode(Laser laser, Context context, ByteArray in, Class<Long> type) throws Exception {
		return in.readLong();
	}


}