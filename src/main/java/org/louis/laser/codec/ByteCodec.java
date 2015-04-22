package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class ByteCodec implements Codec<Byte> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Byte value) throws Exception {
		out.writeByte(value);
	}

	@Override
	public Byte decode(Laser laser, Context context, ByteArray in, Class<Byte> type) throws Exception {
		return in.readByte();
	}

}