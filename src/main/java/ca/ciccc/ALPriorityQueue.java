package ca.ciccc;

import java.util.ArrayList;
import java.util.Comparator;

public class ALPriorityQueue implements VCPriorityQueue<Integer, String> {

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
    public Entry<Integer, String> enqueue(Integer key, String value) throws IllegalArgumentException {
        if (!(key instanceof Integer)) {
            throw new IllegalArgumentException("key is not a valid Integer class");
        }
        Entry<Integer, String> entry = new Entry<>(key, value);
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
        while (!other.isEmpty()){
            arr.add(other.dequeueMin());
        }
        return this;
    }
}
