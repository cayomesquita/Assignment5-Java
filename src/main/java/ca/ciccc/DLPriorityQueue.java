package ca.ciccc;

import java.util.Iterator;
import java.util.LinkedList;

public class DLPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

    public static final Entry DEFAULT_PRIORITY_ENTRY = new Entry(Integer.MAX_VALUE, "");
    private LinkedList<Entry> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
        if (!(key instanceof Integer) || !acceptableType(key)) {
            throw new IllegalArgumentException("key is not valid");
        }
        Entry newEntry = new Entry(key, value);
        Iterator<Entry> it = list.iterator();
        int index = 0;
        while (it.hasNext()) {
            Entry entry = it.next();
            if (entry.key.compareTo(newEntry.key) > 0) {
                break;
            }
            index++;
        }
        list.add(index, newEntry);
        return newEntry;
    }

    private boolean acceptableType(K key) {
        if (!list.isEmpty()) {
            Class<? extends Comparable> keyClass = list.iterator().next().key.getClass();
            return key.getClass().isAssignableFrom(keyClass);
        }
        return true;
    }

    @Override
    public Entry peek() {
        return list.peek();
    }

    @Override
    public Entry dequeueMin() {
        return list.poll();
    }

    @Override
    public VCPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
        int index = 0;
        Iterator<Entry> it = null;
        while (!other.isEmpty()) {
            it = list.listIterator(index);
            Entry newEntry = other.dequeueMin();
            while (it.hasNext()) {
                Entry entry = it.next();
                if (entry.key.compareTo(newEntry.key) > 0) {
                    break;
                }
                index++;
            }
            list.add(index++, newEntry);
        }
        return this;
    }
}
