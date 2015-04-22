package org.louis.laser.codec;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;
import org.louis.laser.util.GenericUtil;

public abstract class MapCodec<K, V> implements Codec<Map<K, V>> {

	private Codec<K> genericKeyCodec;
	private Codec<V> genericValueCodec;
	private Class<K> genericKeyType;
	private Class<V> genericValueType;
	private final boolean isKeyFinal, isValueFinal;

	public MapCodec() {
		isKeyFinal = false;
		isValueFinal = false;
	}

	public MapCodec(Laser laser, Class<K> genericKeyType, Class<V> genericValueType) {
		this.isKeyFinal = Modifier.isFinal(genericKeyType.getModifiers());
		this.isValueFinal = Modifier.isFinal(genericValueType.getModifiers());
		if (this.isKeyFinal) {
			this.genericKeyType = genericKeyType;
			this.genericKeyCodec = laser.getCodec(genericKeyType);
		}
		if (this.isValueFinal) {
			this.genericValueType = genericValueType;
			this.genericValueCodec = laser.getCodec(genericValueType);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void encode(Laser laser, Context context, ByteArray out, Map<K, V> values) throws Exception {
		if (values == null) {
			out.writeVarInt(0);
			return;
		}
		out.writeVarInt(values.size() + 1);
		if (values.size() > 0) {
			boolean keySameGeneric = false, valueSameGeneric = false;
			if (!isKeyFinal) {
				keySameGeneric = GenericUtil.sameGeneric(values.keySet());
				out.writeBoolean(keySameGeneric);
			}
			if (!isValueFinal) {
				valueSameGeneric = GenericUtil.sameGeneric(values.values());
				out.writeBoolean(valueSameGeneric);
			}
			boolean writeKeyClass = keySameGeneric, writeValueClass = valueSameGeneric;
			Codec<K> genericKeyCodec = this.genericKeyCodec;
			Codec<V> genericValueCodec = this.genericValueCodec;
			for (Entry<K, V> entry : values.entrySet()) {
				K key = entry.getKey();
				V value = entry.getValue();
				if (!isKeyFinal) {
					if (keySameGeneric && writeKeyClass) {
						writeKeyClass = false;
						laser.writeClass(context, out, key.getClass());
						genericKeyCodec = (Codec<K>) laser.getCodec(key.getClass());
					} else if (!keySameGeneric) {
						laser.writeClass(context, out, key.getClass());
						genericKeyCodec = (Codec<K>) laser.getCodec(key.getClass());
					}
				}
				if (!isValueFinal) {
					if (valueSameGeneric && writeValueClass) {
						writeValueClass = false;
						laser.writeClass(context, out, value.getClass());
						genericValueCodec = (Codec<V>) laser.getCodec(value.getClass());
					} else if (!valueSameGeneric) {
						laser.writeClass(context, out, value.getClass());
						genericValueCodec = (Codec<V>) laser.getCodec(value.getClass());
					}
				}
				genericKeyCodec.encode(laser, context, out, key);
				genericValueCodec.encode(laser, context, out, value);
			}
		}
	}

	@Override
	public Map<K, V> decode(Laser laser, Context context, ByteArray in, Class<Map<K, V>> type) throws Exception {
		int size = in.readVarInt() - 1;
		if (size == -1) {
			return null;
		}
		Map<K, V> values = newMap(size);
		if (size > 0) {
			boolean keySameGeneric = false, valueSameGeneric = false;
			if (!isKeyFinal) {
				keySameGeneric = in.readBoolean();
			}
			if (!isValueFinal) {
				valueSameGeneric = in.readBoolean();
			}
			boolean readKeyClass = keySameGeneric, readValueClass = valueSameGeneric;
			Class<K> genericKeyType = this.genericKeyType;
			Codec<K> genericKeyCodec = this.genericKeyCodec;
			Class<V> genericValueType = this.genericValueType;
			Codec<V> genericValueCodec = this.genericValueCodec;
			for (int i = 0; i < size; i++) {
				if (!isKeyFinal) {
					if (keySameGeneric && readKeyClass) {
						readKeyClass = false;
						genericKeyType = laser.readClass(context, in);
						genericKeyCodec = laser.getCodec(genericKeyType);
					} else if (!keySameGeneric) {
						genericKeyType = laser.readClass(context, in);
						genericKeyCodec = laser.getCodec(genericKeyType);
					}
				}
				if (!isValueFinal) {
					if (valueSameGeneric && readValueClass) {
						readValueClass = false;
						genericValueType = laser.readClass(context, in);
						genericValueCodec = laser.getCodec(genericValueType);
					} else if (!valueSameGeneric) {
						genericValueType = laser.readClass(context, in);
						genericValueCodec = laser.getCodec(genericValueType);
					}
				}
				K key = genericKeyCodec.decode(laser, context, in, genericKeyType);
				V value = genericValueCodec.decode(laser, context, in, genericValueType);
				values.put(key, value);
			}
		}
		return values;
	}

	protected abstract Map<K, V> newMap(int size);

	public static class HashMapCodec<K, V> extends MapCodec<K, V> {

		public HashMapCodec() {
		}

		public HashMapCodec(Laser laser, Class<K> genericKeyType, Class<V> genericValueType) {
			super(laser, genericKeyType, genericValueType);
		}

		@Override
		protected Map<K, V> newMap(int size) {
			return new HashMap<K, V>(size);
		}
	}

	public static class LinkedHashMapCodec<K, V> extends MapCodec<K, V> {

		public LinkedHashMapCodec() {
		}

		public LinkedHashMapCodec(Laser laser, Class<K> genericKeyType, Class<V> genericValueType) {
			super(laser, genericKeyType, genericValueType);
		}

		@Override
		protected Map<K, V> newMap(int size) {
			return new LinkedHashMap<K, V>(size);
		}

	}

}
