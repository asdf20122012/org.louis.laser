package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.Packable;
import org.louis.laser.io.ByteArray;

public class PackableCodec implements Codec<Packable> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Packable value) throws Exception {
		if (out.writeBoolean(value == null)) {
			return;
		}
		laser.writeClass(context, out, value.getClass());
		value.writeTo(laser, context, out);
	}

	@Override
	public Packable decode(Laser laser, Context context, ByteArray in, Class<Packable> type) throws Exception {
		if (in.readBoolean()) {
			return null;
		}
		type = laser.readClass(context, in);
		Packable objectPackable = laser.newInstance(type);
		objectPackable.readFrom(laser, context, in);
		return objectPackable;
	}

}
