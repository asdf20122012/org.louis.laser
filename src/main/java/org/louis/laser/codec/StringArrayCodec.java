package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class StringArrayCodec implements Codec<String[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, String[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (String value : values) {
				out.writeString(value);
			}
		}
	}

	@Override
	public String[] decode(Laser laser, Context context, ByteArray in, Class<String[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		String[] array = new String[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readString();
			}
		}
		return array;
	}
}
