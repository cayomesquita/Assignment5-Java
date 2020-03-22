package ca.ciccc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDLPriorityQueue {

    VCPriorityQueue<Integer, String> queue;
    VCPriorityQueue<Integer, String> queueAux;

    @Before
    public void setUp() throws Exception {
        queue = new DLPriorityQueue();
        queueAux = new DLPriorityQueue();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void enqueue() {
        Integer key = 1;
        String value = "one";
        Entry<Integer, String> entry = queue.enqueue(key, value);
        assertEquals(key, entry.key);
        assertEquals(value, entry.value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void enqueueWithException() {
        Integer key = null;
        String value = "one";
        Entry<Integer, String> entry = queue.enqueue(key, value);
        fail("Not throw IllegalArgumentException");
    }

    @Test
    public void dequeueMin() {
        queue.enqueue(3, "three");
        Entry<Integer, String> expected = queue.enqueue(1, "one");
        Entry<Integer, String> expected2 = queue.enqueue(2, "two");
        Entry result = queue.dequeueMin();
        assertEquals(expected.key, result.key);
        assertEquals(expected.value, result.value);
        result = queue.dequeueMin();
        expected = expected2;
        assertEquals(expected.key, result.key);
        assertEquals(expected.value, result.value);
    }

    @Test
    public void peek() {
        queue.enqueue(3, "three");
        Entry<Integer, String> expected = queue.enqueue(1, "one");
        queue.enqueue(2, "two");
        Entry result = queue.peek();
        assertEquals(expected.key, result.key);
        assertEquals(expected.value, result.value);
        result = queue.peek();
        assertEquals(expected.key, result.key);
        assertEquals(expected.value, result.value);
    }

    @Test
    public void size() {
        assertEquals(0, queue.size());
        queue.enqueue(3, "three");
        queue.enqueue(1, "one");
        queue.enqueue(2, "two");
        assertEquals(3, queue.size());
        queue.peek();
        assertEquals(3, queue.size());
        queue.dequeueMin();
        assertEquals(2, queue.size());
        queue.dequeueMin();
        assertEquals(1, queue.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(3, "three");
        assertFalse(queue.isEmpty());
        queue.dequeueMin();
        assertTrue(queue.isEmpty());
        queue.enqueue(1, "one");
        assertFalse(queue.isEmpty());
        queue.peek();
        assertFalse(queue.isEmpty());
        queue.dequeueMin();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void merge() {
        queue.enqueue(3, "three");
        Entry<Integer, String> expected = queue.enqueue(1, "one");
        queue.enqueue(2, "two");
        Entry result = queue.peek();
        assertEquals(expected.key, result.key);
        assertEquals(expected.value, result.value);

        queueAux.enqueue(5, "five");
        Entry<Integer, String> expected2 = queueAux.enqueue(0, "zero");
        queueAux.enqueue(2, "two");
        result = queueAux.peek();
        assertEquals(expected2.key, result.key);
        assertEquals(expected2.value, result.value);

        VCPriorityQueue queueMerged = queue.merge(queueAux);

        result = queueMerged.peek();
        assertNotEquals(expected.key, result.key);
        assertNotEquals(expected.value, result.value);
        assertEquals(expected2.key, result.key);
        assertEquals(expected2.value, result.value);
        assertEquals(queue, queueMerged);
    }

}
