package org.louis.laser.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.louis.laser.util.UnsafeUtil;

/**
 * 
 * @author louisjiang<493509534@qq.com>
 * @version 2015-1-10 下午5:28:15
 */
public class ByteArray {

	protected int writePosition = 0;
	protected int readPosition = 0;
	protected int capacity;
	protected byte[] buffer = null;

	ByteArray(int capacity) {
		this.capacity = capacity;
		buffer = new byte[capacity];
	}

	ByteArray(byte[] buffer) {
		this.writePosition = this.capacity = buffer.length;
		this.buffer = buffer;
	}

	public static ByteArray get() {
		return get(1024);
	}

	public static ByteArray get(int capacity) {
		if (UnsafeUtil.available()) {
			return new UnsafeByteArray(capacity);
		} else {
			return new ByteArray(capacity);
		}
	}

	public static ByteArray get(byte[] buffer) {
		if (UnsafeUtil.available()) {
			return new UnsafeByteArray(buffer);
		} else {
			return new ByteArray(buffer);
		}
	}

	public boolean isUnsafe() {
		return false;
	}

	public byte getByte(int index) {
		requireIndex(index);
		return getByteNoRequire(index);
	}

	protected byte getByteNoRequire(int index) {
		return buffer[index];
	}

	public byte readByte() {
		requireReadPosition(1);
		return readByteNoRequire();
	}

	protected byte readByteNoRequire() {
		return buffer[readPosition++];
	}

	public void writeByte(byte value) {
		requireWritePosition(1);
		writeByteNoRequire(value);
	}

	protected void writeByteNoRequire(byte value) {
		buffer[writePosition++] = value;
	}

	public void writeBytesNoLength(byte[] values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		writeBytesNoLength(values, 0, values.length);
	}

	public void writeBytesNoLength(byte[] values, int position, int length) {
		writeBytes(values, position, length, false);
	}

	public void writeBytes(byte[] values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		writeBytes(values, 0, values.length);
	}

	public void writeBytes(byte[] values, int offset, int length) {
		writeBytes(values, offset, length, true);
	}

	public void writeBytes(byte[] values, int offset, int length, boolean writeLength) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		length = Math.min(length, values.length - offset);
		if (writeLength) {
			writeVarInt(length);
		}
		requireWritePosition(length);
		copy(values, offset, buffer, writePosition, length);
		writePosition += length;
	}

	public byte[] readBytes() {
		int length = readVarInt();
		return readBytes(length);
	}

	public byte[] readBytes(int length) {
		requireReadPosition(length);
		byte[] b = new byte[length];
		copy(buffer, readPosition, b, 0, length);
		readPosition += length;
		return b;
	}

	public byte[] getBytes(int index) {
		int[] array = _getVarInt(index);
		return getBytes(array[1], array[0]);
	}

	public byte[] getBytes(int index, int length) {
		byte[] b = new byte[length];
		requireIndex(index + length);
		copy(buffer, index, b, 0, length);
		return b;
	}

	public short getUnsignedByte(int index) {
		return (short) (getByte(index) & 0xff);
	}

	public short readUnsignedByte() {
		return (short) (readByte() & 0xff);
	}

	public void writeUnsignedByte(short value) {
		if (value < 0 || value >= 1 << 8)
			throw new IllegalArgumentException(Short.toString(value));
		writeByte((byte) value);
	}

	public short getShort(int index) {
		requireIndex(index + 2);
		return (short) ((getByteNoRequire(index) & 0xff) << 0 | (getByteNoRequire(index + 1) & 0xff) << 8);
	}

	public short readShort() {
		requireReadPosition(2);
		return (short) ((readByteNoRequire() & 0xff) << 0 | (readByteNoRequire() & 0xff) << 8);
	}

	public void writeShort(short value) {
		requireWritePosition(2);
		writeByteNoRequire((byte) (value >>> 0));
		writeByteNoRequire((byte) (value >>> 8));
	}

	public int getUnsignedShort(int index) {
		return getShort(index) & 0xffff;
	}

	public int readUnsignedShort(int index) {
		requireReadPosition(2);
		return ((readByteNoRequire() & 0xff) << 0 | (readByteNoRequire() & 0xff) << 8) & 0xffff;
	}

	public void writeUnsignedShort(int value) {
		if (value < 0 || value >= 1 << 16)
			throw new IllegalArgumentException(Integer.toString(value));
		requireWritePosition(2);
		writeByteNoRequire((byte) (value >>> 0));
		writeByteNoRequire((byte) (value >>> 8));
	}

	public int getInt(int index) {
		requireIndex(index + 4);
		return (getByteNoRequire(index) & 0xff) << 0 | (getByteNoRequire(index + 1) & 0xff) << 8 | (getByteNoRequire(index + 2) & 0xff) << 16 | (getByteNoRequire(index + 3) & 0xff) << 24;
	}

	public int readInt() {
		requireReadPosition(4);
		return (readByteNoRequire() & 0xff) << 0 | (readByteNoRequire() & 0xff) << 8 | (readByteNoRequire() & 0xff) << 16 | (readByteNoRequire() & 0xff) << 24;
	}

	public void writeInt(int value) {
		requireWritePosition(4);
		writeByteNoRequire((byte) (value >>> 0));
		writeByteNoRequire((byte) (value >>> 8));
		writeByteNoRequire((byte) (value >>> 16));
		writeByteNoRequire((byte) (value >>> 24));
	}

	public long getUnsignedInt(int index) {
		return getInt(index) & 0xffffffffL;
	}

	public long readUnsignedInt() {
		return (readInt()) & 0xffffffffL;
	}

	public void writeUnsignedInt(long value) {
		if (value < 0 || value >= 1L << 32)
			throw new IllegalArgumentException(Long.toString(value));
		requireWritePosition(4);
		writeByteNoRequire((byte) (value >>> 0));
		writeByteNoRequire((byte) (value >>> 8));
		writeByteNoRequire((byte) (value >>> 16));
		writeByteNoRequire((byte) (value >>> 24));
	}

	public long getLong(int index) {
		requireIndex(index + 8);
		return (getByteNoRequire(index) & 0xffL) << 0 | (getByteNoRequire(index + 1) & 0xffL) << 8 | (getByteNoRequire(index + 2) & 0xffL) << 16 | (getByteNoRequire(index + 3) & 0xffL) << 24
				| (getByteNoRequire(index + 4) & 0xffL) << 32 | (getByteNoRequire(index + 5) & 0xffL) << 40 | (getByteNoRequire(index + 6) & 0xffL) << 48 | (getByteNoRequire(index + 7) & 0xffL) << 56;
	}

	public long readLong() {
		requireReadPosition(8);
		return (readByteNoRequire() & 0xffL) << 0 | (readByteNoRequire() & 0xffL) << 8 | (readByteNoRequire() & 0xffL) << 16 | (readByteNoRequire() & 0xffL) << 24
				| (readByteNoRequire() & 0xffL) << 32 | (readByteNoRequire() & 0xffL) << 40 | (readByteNoRequire() & 0xffL) << 48 | (readByteNoRequire() & 0xffL) << 56;
	}

	public void writeLong(long value) {
		requireWritePosition(8);
		writeByteNoRequire((byte) (value >>> 0));
		writeByteNoRequire((byte) (value >>> 8));
		writeByteNoRequire((byte) (value >>> 16));
		writeByteNoRequire((byte) (value >>> 24));
		writeByteNoRequire((byte) (value >>> 32));
		writeByteNoRequire((byte) (value >>> 40));
		writeByteNoRequire((byte) (value >>> 48));
		writeByteNoRequire((byte) (value >>> 56));
	}

	public boolean getBoolean(int index) {
		if (getByte(index) == (byte) 0) {
			return true;
		}
		return false;
	}

	public boolean readBoolean() {
		if (readByte() == (byte) 0) {
			return true;
		}
		return false;
	}

	public boolean writeBoolean(boolean value) {
		writeByte((byte) (value ? 0 : 1));
		return value;
	}

	public char getChar(int index) {
		requireIndex(index + 2);
		return (char) (((getByteNoRequire(index) & 0xFF) << 8) | (getByteNoRequire(index + 1) & 0xFF));
	}

	public char readChar() {
		requireReadPosition(2);
		return (char) (((readByteNoRequire() & 0xFF) << 8) | (readByteNoRequire() & 0xFF));
	}

	public void writeChar(char value) {
		requireWritePosition(2);
		writeByteNoRequire((byte) (value >>> 8));
		writeByteNoRequire((byte) value);
	}

	public float getFloat(int index) {
		return Float.intBitsToFloat(getInt(index));
	}

	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	public void writeFloat(float value) {
		writeInt(Float.floatToIntBits(value));
	}

	public double getDouble(int index) {
		return Double.longBitsToDouble(getLong(index));
	}

	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	public void writeDouble(double value) {
		writeLong(Double.doubleToLongBits(value));
	}

	public String getString(int index) {
		int[] array = _getVarInt(index);
		int length = array[0];
		index = array[1];
		if (length == 0) {
			return null;
		} else if (length == 1) {
			return "";
		} else {
			length = length - 1;
			byte[] buffer = getBytes(index, length);
			try {
				return new String(buffer, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return new String(buffer);
			}
		}
	}

	public String readString() {
		int length = readVarInt();
		if (length == 0) {
			return null;
		} else if (length == 1) {
			return "";
		} else {
			length = length - 1;
			byte[] buffer = readBytes(length);
			try {
				return new String(buffer, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return new String(buffer);
			}
		}
	}

	public void writeString(String value) {
		if (value == null) {
			writeVarInt(0);
			return;
		}
		if (value.equals("")) {
			writeVarInt(1);
		}
		byte[] values = null;
		try {
			values = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			values = value.getBytes();
		}
		writeVarInt(values.length + 1);
		writeBytesNoLength(values);
	}

	public void writeVarInt(int value) {
		if (value < 0) {
			throw new IllegalArgumentException(value + " < 0");
		}
		if (value >> 7 == 0) {
			writeByte((byte) value);
			return;
		}
		if (value >> 14 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7));
			return;
		}
		if (value >> 21 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14));
			return;
		}
		if (value >> 28 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14 | 0x80));
			writeByte((byte) (value >> 21));
			return;
		}
	}

	public int getVarInt(int index) {
		return _getVarInt(index)[0];
	}

	protected int[] _getVarInt(int index) {
		byte b = getByte(index++);
		int i = b & 0x7F;
		if ((b & 0x80) != 0) {
			b = getByte(index++);
			i |= (b & 0x7F) << 7;
			if ((b & 0x80) != 0) {
				b = getByte(index++);
				i |= (b & 0x7F) << 14;
				if ((b & 0x80) != 0) {
					b = getByte(index++);
					i |= (b & 0x7F) << 21;
					if ((b & 0x80) != 0) {
						b = getByte(index++);
						i |= (b & 0x7F) << 28;
					}
				}
			}
		}
		return new int[] { i, index };
	}

	public int readVarInt() {
		byte b = readByte();
		int i = b & 0x7F;
		if ((b & 0x80) != 0) {
			b = readByte();
			i |= (b & 0x7F) << 7;
			if ((b & 0x80) != 0) {
				b = readByte();
				i |= (b & 0x7F) << 14;
				if ((b & 0x80) != 0) {
					b = readByte();
					i |= (b & 0x7F) << 21;
					if ((b & 0x80) != 0) {
						b = readByte();
						i |= (b & 0x7F) << 28;
					}
				}
			}
		}
		return i;
	}

	public long readVarLong() {
		byte b = readByte();
		long i = b & 0x7F;
		if ((b & 0x80) != 0) {
			b = readByte();
			i |= (b & 0x7F) << 7;
			if ((b & 0x80) != 0) {
				b = readByte();
				i |= (b & 0x7F) << 14;
				if ((b & 0x80) != 0) {
					b = readByte();
					i |= (b & 0x7F) << 21;
					if ((b & 0x80) != 0) {
						b = readByte();
						i |= (long) (b & 0x7F) << 28;
						if ((b & 0x80) != 0) {
							b = readByte();
							i |= (long) (b & 0x7F) << 35;
							if ((b & 0x80) != 0) {
								b = readByte();
								i |= (long) (b & 0x7F) << 42;
								if ((b & 0x80) != 0) {
									b = readByte();
									i |= (long) (b & 0x7F) << 49;
									if ((b & 0x80) != 0) {
										b = readByte();
										i |= (long) b << 56;
									}
								}
							}
						}
					}
				}
			}
		}
		return i;
	}

	public long getVarLong(int index) {
		return _getVarLong(index)[0];
	}

	protected long[] _getVarLong(int index) {
		byte b = getByte(index++);
		long l = b & 0x7F;
		if ((b & 0x80) != 0) {
			b = getByte(index++);
			l |= (b & 0x7F) << 7;
			if ((b & 0x80) != 0) {
				b = getByte(index++);
				l |= (b & 0x7F) << 14;
				if ((b & 0x80) != 0) {
					b = getByte(index++);
					l |= (b & 0x7F) << 21;
					if ((b & 0x80) != 0) {
						b = getByte(index++);
						l |= (long) (b & 0x7F) << 28;
						if ((b & 0x80) != 0) {
							b = getByte(index++);
							l |= (long) (b & 0x7F) << 35;
							if ((b & 0x80) != 0) {
								b = getByte(index++);
								l |= (long) (b & 0x7F) << 42;
								if ((b & 0x80) != 0) {
									b = getByte(index++);
									l |= (long) (b & 0x7F) << 49;
									if ((b & 0x80) != 0) {
										b = getByte(index++);
										l |= (long) b << 56;
									}
								}
							}
						}
					}
				}
			}
		}
		return new long[] { l, index };
	}

	public void writeVarLong(long value) {
		if (value < 0) {
			throw new IllegalArgumentException(value + " < 0");
		}
		if (value >> 7 == 0) {
			writeByte((byte) value);
			return;
		}
		if (value >> 14 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7));
			return;
		}
		if (value >> 21 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14));
			return;
		}
		if (value >> 28 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14 | 0x80));
			writeByte((byte) (value >> 21));
			return;
		}
		if (value >> 35 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14 | 0x80));
			writeByte((byte) (value >> 21 | 0x80));
			writeByte((byte) (value >> 28));
			return;
		}
		if (value >> 42 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14 | 0x80));
			writeByte((byte) (value >> 21 | 0x80));
			writeByte((byte) (value >> 28 | 0x80));
			writeByte((byte) (value >> 35));
			return;
		}
		if (value >> 49 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14 | 0x80));
			writeByte((byte) (value >> 21 | 0x80));
			writeByte((byte) (value >> 28 | 0x80));
			writeByte((byte) (value >> 35 | 0x80));
			writeByte((byte) (value >> 42));
			return;
		}
		if (value >> 56 == 0) {
			writeByte((byte) ((value & 0x7F) | 0x80));
			writeByte((byte) (value >> 7 | 0x80));
			writeByte((byte) (value >> 14 | 0x80));
			writeByte((byte) (value >> 21 | 0x80));
			writeByte((byte) (value >> 28 | 0x80));
			writeByte((byte) (value >> 35 | 0x80));
			writeByte((byte) (value >> 42 | 0x80));
			writeByte((byte) (value >> 49));
			return;
		}
	}

	public void copy(byte[] src, int srcPosition, byte[] dest, int destPosition, int length) {
		System.arraycopy(src, srcPosition, dest, destPosition, length);
	}

	public byte[] getBuffer() {
		return Arrays.copyOf(buffer, writePosition);
	}

	public int writePosition() {
		return writePosition;
	}

	public int readPosition() {
		return readPosition;
	}

	public void reset() {
		writePosition = 0;
		readPosition = 0;
		capacity = 1024;
		buffer = null;
		buffer = new byte[capacity];
	}

	public OutputStream writeTo(OutputStream out) throws IOException {
		out.write(buffer, 0, writePosition);
		return out;
	}

	public void readFrom(InputStream in) throws IOException {
		reset();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = (in.read(buffer))) != -1) {
			writeBytesNoLength(buffer, 0, length);
		}
		buffer = null;
	}

	protected synchronized void requireWritePosition(int required) {
		if (writePosition + required - buffer.length >= 0) {
			int length = (buffer.length * 2 - buffer.length / 2);
			byte[] dest = new byte[length];
			copy(buffer, 0, dest, 0, buffer.length);
			capacity = length;
			buffer = null;
			buffer = dest;
			dest = null;
		}
	}

	protected void requireReadPosition(int required) {
		if (readPosition + required > writePosition) {
			throw new IndexOutOfBoundsException(String.format("readPosition(%d) + length(%d) exceeds writePosition(%d): %s", readPosition, required, writePosition, this));
		}
	}

	protected void requireIndex(int index) {
		if (index < 0 || index > writePosition) {
			throw new IndexOutOfBoundsException(String.format("index: %d (expected: range(0, %d))", index, writePosition));
		}
	}

	@Override
	public String toString() {
		return "capacity=" + capacity + ",writePosition=" + writePosition + ",readPosition=" + readPosition;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ByteArray array = ByteArray.get(100);
		array.writeBytes(new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 1, 11);
		array.writeString("加盟能够阿萨斯阿斯顿爱上大s大s大 爱上爱上啊撒大的的");
		array.writeInt(12345);
		System.out.println(array.readBytes().length);
		System.out.println(array.getString(array.readPosition()));
		System.out.println(array.readString());
		System.out.println(array.getInt(array.readPosition()));
		System.out.println(array.readInt());
		System.out.println(System.currentTimeMillis() - start);
	}

}
