package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class DoubleArrayCodec implements Codec<double[]> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, double[] values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.length + 1);
		if (values.length > 0) {
			for (double value : values) {
				out.writeDouble(value);
			}
		}
	}

	@Override
	public double[] decode(Laser laser, Context context, ByteArray in, Class<double[]> type) throws Exception {
		int length = in.readVarInt() - 1;
		if (length == -1) {
			return null;
		}
		double[] array = new double[length];
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				array[i] = in.readDouble();
			}
		}
		return array;
	}
}