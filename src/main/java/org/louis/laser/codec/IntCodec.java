package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class IntCodec implements Codec<Integer> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Integer value) throws Exception {
		out.writeInt(value);
	}

	@Override
	public Integer decode(Laser laser, Context context, ByteArray in, Class<Integer> type) throws Exception {
		return in.readInt();
	}

}