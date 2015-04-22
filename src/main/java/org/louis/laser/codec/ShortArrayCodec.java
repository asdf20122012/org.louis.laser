package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class ShortArrayCodec implements Codec<short[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, short[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (short value : values) {
				out.writeShort(value);
			}
		}
	}

	@Override
	public short[] decode(Laser laser, Context context, ByteArray in, Class<short[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		short[] array = new short[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readShort();
			}
		}
		return array;
	}
}