package org.louis.laser.codec;

import java.util.Date;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class DateCodec implements Codec<Date> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Date value) throws Exception {
		if (value == null) {
			out.writeVarLong(0);
			return;
		}
		out.writeVarLong(value.getTime());
	}

	@Override
	public Date decode(Laser laser, Context context, ByteArray in, Class<Date> type) throws Exception {
		long time = in.readVarLong();
		if (time == 0) {
			return null;
		}
		return new Date(time);
	}

}
