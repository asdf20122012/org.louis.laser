package org.louis.laser.codec;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.io.ByteArray;

public class CharCodec implements Codec<Character> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, Character value) throws Exception {
		out.writeChar(value);
	}

	@Override
	public Character decode(Laser laser, Context context, ByteArray in, Class<Character> type) throws Exception {
		return in.readChar();
	}



}
