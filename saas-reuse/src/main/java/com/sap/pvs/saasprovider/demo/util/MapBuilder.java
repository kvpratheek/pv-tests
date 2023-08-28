package com.sap.pvs.saasprovider.demo.util;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {

    private MapBuilder() {}

    // All the "of" methods can be replaced by Map.of in Java 9+, see: https://docs.oracle.com/javase/9/docs/api/java/util/Map.html#of-K-V-
    public static <K, V> Map<K, V> of(K key1, V value1) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        return unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return unmodifiableMap(map);
    }

    @SuppressWarnings("squid:S00107") // too many method parameters
    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        return unmodifiableMap(map);
    }

    @SuppressWarnings("squid:S00107") // too many method parameters
    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4, K key5, V value5) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        map.put(key5, value5);
        return unmodifiableMap(map);
    }

}