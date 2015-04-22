package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class BooleanCodec implements Codec<Boolean> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Boolean value) throws Exception {
		out.writeBoolean(value);
	}

	@Override
	public Boolean decode(Laser laser, Context context, ByteArray in, Class<Boolean> type) throws Exception {
		return in.readBoolean();
	}

}