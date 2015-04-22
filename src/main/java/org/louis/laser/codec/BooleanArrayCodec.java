package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class BooleanArrayCodec implements Codec<boolean[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, boolean[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (boolean value : values) {
				out.writeBoolean(value);
			}
		}
	}

	@Override
	public boolean[] decode(Laser laser, Context context, ByteArray in, Class<boolean[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		boolean[] array = new boolean[length];
		if (length >= 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readBoolean();
			}
		}
		return array;
	}
}
