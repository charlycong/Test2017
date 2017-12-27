package com.lisj.collection;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

/**
 * @author etix-2017-3
 * @version 1.0
 * @Date 2017-12-29 14:41
 */
public class CustomTreeMap<K, V> extends AbstractMap<K, V>
        implements NavigableMap<K, V>, Cloneable, java.io.Serializable {

    private final Comparator<? super K> comparator;

    private transient Entry<K, V> root;

    private transient int size = 0;

    /**
     * The number of structural modifications to the tree.
     */
    private transient int modCount = 0;


    public CustomTreeMap() {
        comparator = null;
    }


    public V put(K key, V value) {
        CustomTreeMap.Entry<K,V> t = root;
        if (t == null) {
            compare(key, key); // type (and possibly null) check

            root = new CustomTreeMap.Entry<>(key, value, null);
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        CustomTreeMap.Entry<K,V> parent;
        // split comparator and comparable paths
        Comparator<? super K> cpr = comparator;
        if (cpr != null) {
            do {
                parent = t;
                cmp = cpr.compare(key, t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else
                    return t.setValue(value);
            } while (t != null);
        }
        else {
            if (key == null)
                throw new NullPointerException();
            @SuppressWarnings("unchecked")
            Comparable<? super K> k = (Comparable<? super K>) key;
            do {
                parent = t;
                cmp = k.compareTo(t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else
                    return t.setValue(value);
            } while (t != null);
        }
        CustomTreeMap.Entry<K,V> e = new CustomTreeMap.Entry<>(key, value, parent);
        if (cmp < 0)
            parent.left = e;
        else
            parent.right = e;
        //fixAfterInsertion(e);
        size++;
        modCount++;
        return null;
    }


    final int compare(Object k1, Object k2) {
        return comparator==null ? ((Comparable<? super K>)k1).compareTo((K)k2)
                : comparator.compare((K)k1, (K)k2);
    }



    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        return null;
    }

    @Override
    public K lowerKey(K key) {
        return null;
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return null;
    }

    @Override
    public K floorKey(K key) {
        return null;
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return null;
    }

    @Override
    public K ceilingKey(K key) {
        return null;
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        return null;
    }

    @Override
    public K higherKey(K key) {
        return null;
    }

    @Override
    public Entry<K, V> firstEntry() {
        return null;
    }

    @Override
    public Entry<K, V> lastEntry() {
        return null;
    }

    @Override
    public Entry<K, V> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<K, V> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return null;
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return null;
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return null;
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return null;
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return null;
    }

    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public K firstKey() {
        return null;
    }

    @Override
    public K lastKey() {
        return null;
    }

    static final boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }

    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        CustomTreeMap.Entry<K,V> left;
        CustomTreeMap.Entry<K,V> right;
        CustomTreeMap.Entry<K,V> parent;
        boolean color = BLACK;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(K key, V value, CustomTreeMap.Entry<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;

            return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Returns the first Entry in the TreeMap (according to the TreeMap's
     * key-sort function).  Returns null if the TreeMap is empty.
     */
    final CustomTreeMap.Entry<K,V> getFirstEntry() {
        CustomTreeMap.Entry<K,V> p = root;
        if (p != null)
            while (p.left != null)
                p = p.left;
        return p;
    }

    /**
     * Returns the last Entry in the TreeMap (according to the TreeMap's
     * key-sort function).  Returns null if the TreeMap is empty.
     */
    final CustomTreeMap.Entry<K,V> getLastEntry() {
        CustomTreeMap.Entry<K,V> p = root;
        if (p != null)
            while (p.right != null)
                p = p.right;
        return p;
    }

    /**
     * Returns the successor of the specified Entry, or null if no such.
     */
    static <K,V> CustomTreeMap.Entry<K,V> successor(CustomTreeMap.Entry<K,V> t) {
        if (t == null)
            return null;
        else if (t.right != null) {
            CustomTreeMap.Entry<K,V> p = t.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            CustomTreeMap.Entry<K,V> p = t.parent;
            CustomTreeMap.Entry<K,V> ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * Returns the predecessor of the specified Entry, or null if no such.
     */
    static <K,V> CustomTreeMap.Entry<K,V> predecessor(CustomTreeMap.Entry<K,V> t) {
        if (t == null)
            return null;
        else if (t.left != null) {
            CustomTreeMap.Entry<K,V> p = t.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            CustomTreeMap.Entry<K,V> p = t.parent;
            CustomTreeMap.Entry<K,V> ch = t;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }
}
