package interview.leetcode.strings;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * A map from a key to value and from value to map.
 * Allows to quickly check if a value exists or to get the key for a certain value.
 */
@Slf4j
class BijectionMap<K, V> {
    final Map<K, V> map = new HashMap<>();
    final Map<V, K> inverseMap = new HashMap<>();

    public void put(K key, V value) {
        log.debug("{} => {}", key, value);
        map.put(key, value);
        inverseMap.put(value, key);
    }

    public V get(K key) {
        return map.get(key);
    }

    public boolean containsValue(V value) {
        return inverseMap.containsKey(value);
    }

    public K getKey(V value) {
        return inverseMap.get(value);
    }

    @Override
    public String toString() {
        return String.valueOf(map);
    }
}
