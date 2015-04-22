package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class LongArrayCodec implements Codec<long[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, long[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (long value : values) {
				out.writeLong(value);
			}
		}
	}

	@Override
	public long[] decode(Laser laser, Context context, ByteArray in, Class<long[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		long[] array = new long[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readLong();
			}
		}
		return array;
	}
}