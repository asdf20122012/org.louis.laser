package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public interface Codec<T> {

	void encode(Laser laser, Context context, ByteArray out, T value) throws Exception;

	T decode(Laser laser, Context context, ByteArray in, Class<T> type) throws Exception;

}
