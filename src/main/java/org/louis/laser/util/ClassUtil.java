package org.louis.laser.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author louisjiang<493509534@qq.com>
 * @version 15-4-8 下午6:53
 */
public class ClassUtil {

    public static List<Field> getDeclaredFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        while (!(type == Object.class)) {
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                fields.add(field);
            }
            type = (Class<?>) type.getSuperclass();
        }
        Collections.sort(fields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return fields;
    }

    public static Map<Method, Method> getGetterAndSetterMethods(Class<?> type) {
        List<Field> declaredFields = getDeclaredFields(type);
        if (declaredFields == null || declaredFields.size() == 0) {
            return null;
        }
        Map<Method, Method> methods = new HashMap<Method, Method>();
        for (Field field : declaredFields) {
            String name = firstUpperCase(field.getName());
            Method getter = null, setter = null;
            try {
                getter = type.getDeclaredMethod("get" + name);
            } catch (NoSuchMethodException e) {
                try {
                    getter = type.getDeclaredMethod("is" + name);
                } catch (NoSuchMethodException e1) {
                }
            }
            try {
                setter = type.getDeclaredMethod("set" + name, field.getType());
            } catch (NoSuchMethodException e) {
            }
            if (getter != null && setter != null) {
                methods.put(getter, setter);
            }
        }
        return methods;
    }

    private static String firstUpperCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(ClassUtil.firstUpperCase("name"));
    }
}
