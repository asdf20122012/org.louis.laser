package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class EnumCodec implements Codec<Enum<?>> {

	private Enum<?>[] enumConstants;

	public EnumCodec(Class<? extends Enum<?>> enumType) {
		enumConstants = enumType.getEnumConstants();
	}

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Enum<?> value) throws Exception {
		if (value == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(value.ordinal() + 1);
	}

	@Override
	public Enum<?> decode(Laser laser, Context context, ByteArray in, Class<Enum<?>> type) throws Exception {
		int ordinal = in.readVarInt() - 1;
		if (ordinal == -1) {
			return null;
		}
		return enumConstants[ordinal];
	}

}
