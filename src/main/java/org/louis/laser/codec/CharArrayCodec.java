package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class CharArrayCodec implements Codec<char[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, char[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (char value : values) {
				out.writeChar(value);
			}
		}
	}

	@Override
	public char[] decode(Laser laser, Context context, ByteArray in, Class<char[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		char[] array = new char[length];
		if (length >= 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readChar();
			}
		}
		return array;
	}
}
