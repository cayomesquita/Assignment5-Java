package ca.ciccc;

import java.util.Comparator;

/**
 * Class for the Entry element of VCPriorityQueue
 *
 * @author Derrick Park
 */
public class Entry<K extends Comparable, V> implements Comparable {
    K key;
    V value;

    /**
     * Returns the key stored in this entry.
     *
     * @return the entry's key
     */
    K getKey() {
        return key;
    }

    /**
     * Returns the value stored in this entry.
     *
     * @return the entry's value
     */
    V getValue() {
        return value;
    }

    public Entry(K k, V v) {
        key = k;
        value = v;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        if (o instanceof Entry) {
            Entry other = (Entry) o;
            return key.compareTo(other.key);
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                '}';
    }
}