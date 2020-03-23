package ca.ciccc;

import java.util.Arrays;

public class BHPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

    public static final int DEFAULT_INITIAL_SIZE = 100;
    Entry[] heap;

    int size;

    public BHPriorityQueue() {
        this(DEFAULT_INITIAL_SIZE);
    }

    public BHPriorityQueue(int initialSize) {
        super();
        this.heap = new Entry[initialSize > 0 ? initialSize : DEFAULT_INITIAL_SIZE];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
        if (!(key instanceof Comparable) || !acceptableType(key)) {
            throw new IllegalArgumentException("key is not valid");
        }
        Entry newEntry = new Entry(key, value);
        heap[size] = newEntry;
        bubbleUp(size++);
        if (heap.length == size) {
            increaseHeapSize();
        }
        return newEntry;
    }

    @Override
    public Entry peek() {
        return isEmpty() ? null : heap[0];
    }

    @Override
    public Entry dequeueMin() {
        if (size > 0) {
            Entry entry = heap[0];
            swap(0, --size);
            trickle(0);
            return entry;
        }
        return null;
    }

    private void trickle(int index) {
        if (index < size) {
            int leftIndex = getChildLeftIndex(index);
            int rightIndex = getChildRightIndex(index);
            int indexToSwap = getIndexHierPriority(index, leftIndex, rightIndex);
            if (indexToSwap != index) {
                swap(index, indexToSwap);
                trickle(leftIndex);
            }
        }
    }

    private int getIndexHierPriority(int parentIndex, int leftIndex, int rightIndex) {
        int indexToSwap = parentIndex;
        if (leftIndex < size) {
            Entry parent = heap[parentIndex];
            Entry childLeft = heap[leftIndex];
            indexToSwap = higherPriority(childLeft, parent) ? leftIndex : parentIndex;
            if (rightIndex < size && higherPriority(heap[rightIndex], parent)) {
                Entry childRight = heap[rightIndex];
                indexToSwap = higherPriority(childLeft, childRight) ? leftIndex : rightIndex;
            }
        }
        return indexToSwap;
    }

    private boolean higherPriority(Entry entryA, Entry entryB) {
        return entryA.compareTo(entryB) < 0;
    }

    @Override
    public VCPriorityQueue merge(VCPriorityQueue other) {
        while (!other.isEmpty()) {
            Entry<K, V> newEntry = other.dequeueMin();
            this.enqueue(newEntry.key, newEntry.value);
        }
        return this;
    }

    private void bubbleUp(int index) {
        int parentIndex = getParentIndex(index);
        if (higherPriority(heap[index], heap[parentIndex])) {
            swap(index, parentIndex);
            bubbleUp(parentIndex);
        }
    }

    private void swap(int indexA, int indexB) {
        Entry aux = heap[indexA];
        heap[indexA] = heap[indexB];
        heap[indexB] = aux;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getChildLeftIndex(int index) {
        return getChildRightIndex(index) - 1;
    }

    private int getChildRightIndex(int index) {
        return (index + 1) * 2;
    }

    private void increaseHeapSize() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    private boolean acceptableType(K key) {
        if (size > 0) {
            Class<? extends Comparable> keyClass = heap[0].key.getClass();
            return key.getClass().isAssignableFrom(keyClass);
        }
        return true;
    }
}
