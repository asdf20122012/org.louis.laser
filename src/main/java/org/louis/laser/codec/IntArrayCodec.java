package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class IntArrayCodec implements Codec<int[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, int[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (int value : values) {
				out.writeInt(value);
			}
		}
	}

	@Override
	public int[] decode(Laser laser, Context context, ByteArray in, Class<int[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		int[] array = new int[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readInt();
			}
		}
		return array;
	}
}