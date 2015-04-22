package org.louis.laser;

import org.louis.laser.io.ByteArray;

public interface Packable {

	public void writeTo(Laser laser, Context context, ByteArray out) throws Exception;

	public void readFrom(Laser laser, Context context, ByteArray in) throws Exception;

}
