package ca.ciccc;

import java.util.Iterator;
import java.util.LinkedList;

public class DLPriorityQueue implements VCPriorityQueue<Integer, String> {

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
    public Entry<Integer, String> enqueue(Integer key, String value) throws IllegalArgumentException {
        if (!(key instanceof Integer)) {
            throw new IllegalArgumentException("key is not a valid Integer class");
        }
        Entry<Integer, String> newEntry = new Entry(key, value);
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

    @Override
    public Entry peek() {
        return list.peek();
    }

    @Override
    public Entry dequeueMin() {
        return list.poll();
    }

    @Override
    public VCPriorityQueue merge(VCPriorityQueue<Integer, String> other) {
        int index = 0;
        Iterator<Entry> it = null;
        while (!other.isEmpty()) {
            it = list.listIterator(index);
            Entry<Integer, String> newEntry = other.dequeueMin();
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
