package org.louis.laser.codec;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public abstract class Wrapped<T> implements Codec<T> {

	@Override
	public void encode(Laser laser, Context context, ByteArray out, T value) throws Exception {
		if (out.writeBoolean(value == null)) {
			return;
		}
		writeValue(out, value);
	}

	@Override
	public T decode(Laser laser, Context context, ByteArray in, Class<T> type) throws Exception {
		if (in.readBoolean()) {
			return null;
		}
		return readValue(in);
	}

	protected abstract void writeValue(ByteArray out, T value);

	protected abstract T readValue(ByteArray in);

	public static class BooleanCodec extends Wrapped<Boolean> {

		@Override
		protected void writeValue(ByteArray out, Boolean value) {
			out.writeBoolean(value);
		}

		@Override
		protected Boolean readValue(ByteArray in) {
			return in.readBoolean();
		}
	}

	public static class ByteCodec extends Wrapped<Byte> {

		@Override
		protected void writeValue(ByteArray out, Byte value) {
			out.writeByte(value);
		}

		@Override
		protected Byte readValue(ByteArray in) {
			return in.readByte();
		}
	}

	public static class CharCodec extends Wrapped<Character> {

		@Override
		protected void writeValue(ByteArray out, Character value) {
			out.writeChar(value);
		}

		@Override
		protected Character readValue(ByteArray in) {
			return in.readChar();
		}
	}

	public static class DoubleCodec extends Wrapped<Double> {

		@Override
		protected void writeValue(ByteArray out, Double value) {
			out.writeDouble(value);
		}

		@Override
		protected Double readValue(ByteArray in) {
			return in.readDouble();
		}
	}

	public static class FloatCodec extends Wrapped<Float> {

		@Override
		protected void writeValue(ByteArray out, Float value) {
			out.writeFloat(value);
		}

		@Override
		protected Float readValue(ByteArray in) {
			return in.readFloat();
		}
	}

	public static class IntCodec extends Wrapped<Integer> {

		@Override
		protected void writeValue(ByteArray out, Integer value) {
			out.writeInt(value);

		}

		@Override
		protected Integer readValue(ByteArray in) {
			return in.readInt();
		}
	}

	public static class LongCodec extends Wrapped<Long> {

		@Override
		protected void writeValue(ByteArray out, Long value) {
			out.writeLong(value);

		}

		@Override
		protected Long readValue(ByteArray in) {
			return in.readLong();
		}
	}

	public static class ShortCodec extends Wrapped<Short> {

		@Override
		protected void writeValue(ByteArray out, Short value) {
			out.writeShort(value);
		}

		@Override
		protected Short readValue(ByteArray in) {
			return in.readShort();
		}
	}
}
