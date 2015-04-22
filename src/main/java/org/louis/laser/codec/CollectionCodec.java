package org.louis.laser.codec;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;
import org.louis.laser.util.GenericUtil;

public abstract class CollectionCodec<T> implements Codec<Collection<T>> {

	private final boolean isFinal;
	private Codec<T> genericCodec;
	private Class<T> genericType;

	public CollectionCodec() {
		this.isFinal = false;
	}

	public CollectionCodec(Laser laser, Class<T> genericType) {
		this.isFinal = Modifier.isFinal(genericType.getModifiers());
		if (this.isFinal) {
			this.genericType = genericType;
			this.genericCodec = laser.getCodec(genericType);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void encode(Laser laser, Context context, ByteArray out, Collection<T> values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.size() + 1);
		if (values.size() > 0) {
			boolean sameGeneric = false;
			if (!isFinal) {
				sameGeneric = GenericUtil.sameGeneric(values);
				out.writeBoolean(sameGeneric);
			}
			Codec<T> genericCodec = this.genericCodec;
			boolean writeClass = sameGeneric;
			for (T value : values) {
				if (!isFinal) {
					if (sameGeneric && writeClass) {
						writeClass = false;
						genericCodec = (Codec<T>) laser.getCodec(value.getClass());
						laser.writeClass(context, out, value.getClass());
					} else if (!sameGeneric) {
						laser.writeClass(context, out, value.getClass());
						genericCodec = (Codec<T>) laser.getCodec(value.getClass());
					}
				}
				genericCodec.encode(laser, context, out, value);
			}
		}
	}

	@Override
	public Collection<T> decode(Laser laser, Context context, ByteArray in, Class<Collection<T>> type) throws Exception {
		int size = in.readVarInt() - 1;
		if (size == -1) {
			return null;
		}
		Collection<T> values = newCollection(size);
		if (size > 0) {
			boolean sameGeneric = false;
			if (!isFinal) {
				sameGeneric = in.readBoolean();
			}
			Class<T> genericType = this.genericType;
			Codec<T> genericCodec = this.genericCodec;
			boolean readClass = sameGeneric;
			for (int i = 0; i < size; i++) {
				if (!isFinal) {
					if (!sameGeneric) {
						genericType = laser.readClass(context, in);
						genericCodec = laser.getCodec(genericType);
					} else if (sameGeneric && readClass) {
						readClass = false;
						genericType = laser.readClass(context, in);
						genericCodec = laser.getCodec(genericType);
					}
				}
				values.add(genericCodec.decode(laser, context, in, genericType));
			}
		}
		return values;
	}

	protected abstract Collection<T> newCollection(int size);

	public static class ArrayListCodec<T> extends CollectionCodec<T> {

		public ArrayListCodec() {
		}

		public ArrayListCodec(Laser laser, Class<T> genericType) {
			super(laser, genericType);
		}

		@Override
		protected Collection<T> newCollection(int size) {
			return new ArrayList<T>(size);
		}

	}

	public static class HashSetCodec<T> extends CollectionCodec<T> {

		public HashSetCodec() {
		}

		public HashSetCodec(Laser laser, Class<T> genericType) {
			super(laser, genericType);
		}

		@Override
		protected Collection<T> newCollection(int size) {
			return new HashSet<T>(size);
		}

	}

	public static class LinkedHashSetCodec<T> extends CollectionCodec<T> {

		public LinkedHashSetCodec() {
		}

		public LinkedHashSetCodec(Laser laser, Class<T> genericType) {
			super(laser, genericType);
		}

		@Override
		protected Collection<T> newCollection(int size) {
			return new LinkedHashSet<T>(size);
		}

	}

	public static class LinkedListCodec<T> extends CollectionCodec<T> {

		public LinkedListCodec() {
		}

		public LinkedListCodec(Laser laser, Class<T> genericType) {
			super(laser, genericType);
		}

		@Override
		protected Collection<T> newCollection(int size) {
			return new LinkedList<T>();
		}

	}
}
