package interview.leetcode.design.simple;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * https://leetcode.com/problems/lru-cache/
 *
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * LRU: https://en.wikipedia.org/wiki/Cache_replacement_policies#LRU
 *
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache.
 * If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * The functions get and put must each run in O(1) average time complexity.
 */
public class LRUCache {
    private final LinkedHashMap<Integer, Integer> map;
    private final int maxCapacity;

    public LRUCache(int capacity) {
        this.maxCapacity = capacity;
        this.map = new LinkedHashMap<>(capacity);
    }

    public int get(int key) {
        final Integer v = map.get(key);
        if (v != null) {
            reput(key, v);
            return v;
        }
        else {
            return -1;
        }
    }

    public void put(int key, int value) {
        reput(key, value);
        if (map.size() > maxCapacity) {
            final Iterator<Integer> it = map.keySet().iterator();
            it.next();
            it.remove();
        }
    }

    private void reput(int key, Integer v) {
        map.remove(key);        //this will make the returned item last in order for LRU
        map.put(key, v);
    }

    @Override
    public String toString() {
        return "LRUCache{" +
                "map=" + map +
                '}';
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);

        lRUCache.put(1, 1); // cache is {1=1}
        System.out.println(lRUCache);

        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        System.out.println(lRUCache);

        lRUCache.get(1);    // return 1
        System.out.println(lRUCache);

        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        System.out.println(lRUCache);

        lRUCache.get(2);    // returns -1 (not found)
        System.out.println(lRUCache);

        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        System.out.println(lRUCache);

        lRUCache.get(1);    // return -1 (not found)
        System.out.println(lRUCache);

        lRUCache.get(3);    // return 3
        System.out.println(lRUCache);

        lRUCache.get(4);    // return 4
        System.out.println(lRUCache);
    }

}
