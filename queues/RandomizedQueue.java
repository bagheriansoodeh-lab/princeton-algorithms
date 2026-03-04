import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) copy[i] = items[i];
        items = copy;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (size == items.length) resize(items.length * 2);
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue is empty");
        int idx = StdRandom.uniformInt(size);
        Item item = items[idx];
        items[idx] = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue is empty");
        return items[StdRandom.uniformInt(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] order;
        private int current;

        @SuppressWarnings("unchecked")
        RandomizedQueueIterator() {
            order = new int[size];
            for (int i = 0; i < size; i++) order[i] = i;
            StdRandom.shuffle(order);
            current = 0;
        }

        public boolean hasNext() {
            return current < order.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items");
            return items[order[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }
}
