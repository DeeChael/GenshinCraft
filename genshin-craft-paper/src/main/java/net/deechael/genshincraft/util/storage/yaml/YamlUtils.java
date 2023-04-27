package net.deechael.genshincraft.util.storage.yaml;

import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.annotation.share.Comment;
import net.deechael.genshincraft.util.storage.annotation.yaml.YamlCategory;
import net.deechael.genshincraft.util.storage.annotation.yaml.YamlObject;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

final class YamlUtils {

    public static <T> YamlConfiguration serialize(T t) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            for (Field field : t.getClass().getFields()) {
                YamlObject yamlObject = field.getAnnotation(YamlObject.class);
                if (yamlObject == null)
                    continue;
                Class<?> clazz = field.getType();
                String key = field.getName();
                YamlCategory category = field.getAnnotation(YamlCategory.class);
                if (category != null) {
                    if (!category.value().isEmpty()) {
                        String prefix = category.value();
                        while (prefix.startsWith("."))
                            prefix = prefix.substring(1);
                        while (prefix.endsWith("."))
                            prefix = prefix.substring(0, prefix.length() - 1);
                        if (!prefix.isEmpty()) {
                            key = prefix + "." + key;
                        }
                    }
                }
                if (List.class.isAssignableFrom(clazz)) {
                    configuration.set(key, serializeList((List<?>) field.get(t), (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]));
                } else if (Map.class.isAssignableFrom(clazz)) {
                    configuration.set(key, mapToSection((Map<?, ?>) field.get(t)));
                } else if (DataHolder.class.isAssignableFrom(clazz)) {
                    configuration.set(key, serialize(field.get(t)));
                } else {
                    configuration.set(key, field.get(t));
                }
                Comment comment = field.getAnnotation(Comment.class);
                if (comment != null) {
                    configuration.setComments(key, Arrays.asList(comment.value()));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return configuration;
    }

    private static Object serializeList(List<?> list, Class<?> clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            if (list.size() == 0)
                return list;
            else {
                Class<?> type = null;
                for (Object obj : list) {
                    List<?> l = (List<?>) obj;
                    if (l.size() > 0) {
                        type = l.get(0).getClass();
                        break;
                    }
                }
                if (type == null)
                    return list;
                Class<?> finalType = type;
                return list.stream().map(o -> (List<?>) o).map(o -> serializeList(o, finalType)).collect(Collectors.toList());
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            return list.stream().map(o -> (Map<?, ?>) o).map(YamlUtils::mapToSection).collect(Collectors.toList());
        } else if (DataHolder.class.isAssignableFrom(clazz)) {
            return list.stream().map(YamlUtils::serialize).collect(Collectors.toList());
        }
        return list;
    }

    public static <T extends DataHolder> T parseResult(Class<T> clazz, ConfigurationSection section) {
        try {
            T t = clazz.newInstance();
            for (Field field : clazz.getFields()) {
                YamlObject yamlObject = field.getAnnotation(YamlObject.class);
                if (yamlObject == null)
                    continue;
                String key = field.getName();
                YamlCategory category = field.getAnnotation(YamlCategory.class);
                if (category != null) {
                    if (!category.value().isEmpty()) {
                        String prefix = category.value();
                        while (prefix.startsWith("."))
                            prefix = prefix.substring(1);
                        while (prefix.endsWith("."))
                            prefix = prefix.substring(0, prefix.length() - 1);
                        if (!prefix.isEmpty()) {
                            key = prefix + "." + key;
                        }
                    }
                }
                if (!section.contains(key))
                    continue;
                Object object = parse(field, section, key, field.getType());
                if (object == null)
                    continue;
                field.set(t, object);
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parse(Field field, ConfigurationSection section, String name, Class<?> clazz) {
        if (clazz == Integer.class || clazz == int.class) {
            return section.getInt(name);
        } else if (clazz == Long.class || clazz == long.class) {
            return section.getLong(name);
        } else if (clazz == Double.class || clazz == double.class) {
            return section.getDouble(name);
        } else if (clazz == Float.class || clazz == float.class) {
            return (float) section.getDouble(name);
        } else if (clazz == Byte.class || clazz == byte.class) {
            return (byte) section.getInt(name);
        } else if (clazz == Short.class || clazz == short.class) {
            return (short) section.getInt(name);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return section.getBoolean(name);
        } else if (clazz == UUID.class) {
            return UUID.fromString(Objects.requireNonNull(section.getString(name)));
        } else if (clazz == String.class) {
            return section.getString(name);
        } else if (Enum.class.isAssignableFrom(clazz)) {
            return parseEnum(clazz, name);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return sectionToMap(section.getConfigurationSection(name));
        } else if (DataHolder.class.isAssignableFrom(clazz)) {
            return parseResult((Class<? extends DataHolder>) clazz, section.getConfigurationSection(name));
        } else {
            Type type = field.getGenericType();
            if (!(type instanceof ParameterizedType parameterizedType))
                return null;
            if (!List.class.isAssignableFrom((Class<?>) parameterizedType.getRawType()))
                return null;
            Type propertyType = parameterizedType.getActualTypeArguments()[0];
            return parseList(section, name, (Class<?>) propertyType);
        }
    }

    private static Object parseList(ConfigurationSection section, String name, Class<?> clazz) {
        if (clazz == String.class) {
            return section.getStringList(name);
        } else if (clazz == Integer.class || clazz == int.class) {
            return section.getIntegerList(name);
        } else if (clazz == Long.class || clazz == long.class) {
            return section.getLongList(name);
        } else if (clazz == Double.class || clazz == double.class) {
            return section.getDoubleList(name);
        } else if (clazz == Float.class || clazz == float.class) {
            return section.getFloatList(name);
        } else if (clazz == Byte.class || clazz == byte.class) {
            return section.getByteList(name);
        } else if (clazz == Short.class || clazz == short.class) {
            return section.getShortList(name);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return section.getBooleanList(name);
        } else if (clazz == Character.class || clazz == char.class) {
            return section.getCharacterList(name);
        } else if (clazz == UUID.class) {
            return section.getStringList(name).stream().map(UUID::fromString).collect(Collectors.toList());
        } else if (Map.class.isAssignableFrom(clazz)) {
            return section.getMapList(name);
        } else if (DataHolder.class.isAssignableFrom(clazz)) {
            List<?> list = new ArrayList<>();
            for (Map<?, ?> map : section.getMapList(name)) {
                list.add(parseResult(castToDataHolderClass(clazz), mapToSection(map)));
            }
            return list;
        } else {
            return null;
        }
    }

    private static <E extends Enum<E>> Object parseEnum(Class<?> clazz, String name) {
        return Enum.valueOf((Class<E>) clazz, name);
    }

    private static <T extends DataHolder> Class<T> castToDataHolderClass(Class<?> clazz) {
        return (Class<T>) clazz;
    }

    private static ConfigurationSection mapToSection(Map<?, ?> map) {
        ConfigurationSection section = new MemoryConfiguration();
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            section.set(String.valueOf(key), value instanceof Map ? mapToSection((Map<?, ?>) value) : serialize(value));
        }
        return section;
    }

    private static Map<?, ?> sectionToMap(ConfigurationSection section) {
        Map<Object, Object> map = new HashMap<>();
        for (String key : section.getKeys(false)) {
            if (section.isConfigurationSection(key))
                map.put(key, sectionToMap(section.getConfigurationSection(key)));
            else
                map.put(key, section.get(key));
        }
        return map;
    }

    private YamlUtils() {
    }

}
