package org.louis.laser.codec.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.louis.laser.Laser;
import org.louis.laser.Context;
import org.louis.laser.annotation.Exclude;
import org.louis.laser.codec.Codec;
import org.louis.laser.io.ByteArray;

@SuppressWarnings("unchecked")
public class FieldCodec<T> implements Codec<T> {

    protected Laser laser;
    protected Class<T> classType;
    protected List<FieldDescriptor> descriptors = new ArrayList<FieldDescriptor>();

    public FieldCodec(Laser laser, Class<T> classType) {
        this.laser = laser;
        this.classType = classType;
        this.init();
    }

    public void init() {
        Class<T> type = this.classType;
        if (type.isInterface()) {
            return;
        }
        while (!(type == Object.class)) {
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.getAnnotation(Exclude.class) != null) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                Type genericType = field.getGenericType();
                Codec<?> codec = null;
                if (fieldType == genericType) {
                    codec = laser.getCodec(fieldType);
                } else {
                    Class<?>[] genericTypes = getGenericTypes(genericType);
                    codec = laser.getCodec(fieldType, genericTypes);
                }
                descriptors.add(FieldDescriptor.get(field, (Codec<Object>) codec));
            }
            type = (Class<T>) type.getSuperclass();
        }
        Collections.sort(descriptors, new Comparator<FieldDescriptor>() {
            @Override
            public int compare(FieldDescriptor o1, FieldDescriptor o2) {
                return o1.field().getName().compareTo(o2.field().getName());
            }
        });
    }

    protected Class<?>[] getGenericTypes(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) genericType).getActualTypeArguments();
            Class<?>[] generics = new Class<?>[types.length];
            for (int i = 0, n = types.length; i < n; i++) {
                Type actualType = types[i];
                if (actualType instanceof Class) {
                    generics[i] = (Class<?>) actualType;
                }
            }
            return generics;
        }
        return null;
    }

    @Override
    public void encode(Laser laser, Context context, ByteArray out, T value) throws Exception {
        if (out.writeBoolean(value == null)) {
            return;
        }
        for (FieldDescriptor descriptor : descriptors) {
            descriptor.codec().encode(laser, context, out, descriptor.get(value));
        }
    }

    @Override
    public T decode(Laser laser, Context context, ByteArray in, Class<T> type) throws Exception {
        if (in.readBoolean()) {
            return null;
        }
        T obj = laser.newInstance(type);
        for (FieldDescriptor descriptor : descriptors) {
            Object value = descriptor.codec().decode(laser, context, in, descriptor.type());
            descriptor.set(obj, value);
        }
        return obj;
    }
}
