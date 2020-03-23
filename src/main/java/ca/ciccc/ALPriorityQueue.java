package ca.ciccc;

import java.util.ArrayList;

public class ALPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

    public static final Entry DEFAULT_PRIORITY_ENTRY = new Entry(Integer.MAX_VALUE, "");
    private ArrayList<Entry> arr = new ArrayList<>();

    @Override
    public int size() {
        return arr.size();
    }

    @Override
    public boolean isEmpty() {
        return arr.isEmpty();
    }

    @Override
    public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
        if (!(key instanceof Comparable) || !acceptableType(key)) {
            throw new IllegalArgumentException("key is not valid");
        }
        Entry<K,V> entry = new Entry<K,V>(key, value);
        return arr.add(entry) ? entry : null;
    }

    @Override
    public Entry peek() {
        Entry priorityEntry = DEFAULT_PRIORITY_ENTRY;
        for (Entry entry : arr) {
            priorityEntry = priorityEntry.key.compareTo(entry.key) > 0 ? entry : priorityEntry;
        }
        return priorityEntry;
    }

    @Override
    public Entry dequeueMin() {
        Entry priorityEntry = peek();
        return arr.remove(priorityEntry) ? priorityEntry : null;
    }

    @Override
    public VCPriorityQueue merge(VCPriorityQueue other) {
        while (!other.isEmpty()) {
            arr.add(other.dequeueMin());
        }
        return this;
    }

    private boolean acceptableType(K key) {
        if (!arr.isEmpty()) {
            Class<? extends Comparable> keyClass = arr.iterator().next().key.getClass();
            return key.getClass().isAssignableFrom(keyClass);
        }
        return true;
    }
}
