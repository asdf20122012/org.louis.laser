package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class FloatArrayCodec implements Codec<float[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, float[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (float value : values) {
				out.writeFloat(value);
			}
		}
	}

	@Override
	public float[] decode(Laser laser, Context context, ByteArray in, Class<float[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		float[] array = new float[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readFloat();
			}
		}
		return array;
	}

}