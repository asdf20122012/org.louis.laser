package org.louis.laser.io;

import org.louis.laser.util.UnsafeUtil;

/**
 * 
 * @author louisjiang<493509534@qq.com>
 * @version 2015-1-10 下午5:41:24
 */
class UnsafeByteArray extends ByteArray {

	private static final long OFFSET = UnsafeUtil.unsafe().arrayBaseOffset(byte[].class);

	UnsafeByteArray(byte[] buffer) {
		super(buffer);
	}

	UnsafeByteArray(int capacity) {
		super(capacity);
	}

	@Override
	public boolean isUnsafe() {
		return true;
	}

	@Override
	protected byte getByteNoRequire(int index) {
		return UnsafeUtil.unsafe().getByte(buffer, OFFSET + index);
	}

	@Override
	protected byte readByteNoRequire() {
		long position = OFFSET + readPosition;
		readPosition++;
		return UnsafeUtil.unsafe().getByte(buffer, position);
	}

	@Override
	protected void writeByteNoRequire(byte value) {
		long position = OFFSET + writePosition;
		writePosition++;
		UnsafeUtil.unsafe().putByte(buffer, position, value);
	}

	@Override
	public void copy(byte[] src, int srcPosition, byte[] dest, int destPosition, int length) {
		UnsafeUtil.unsafe().copyMemory(src, OFFSET + srcPosition, dest, OFFSET + destPosition, length);
	}

}
