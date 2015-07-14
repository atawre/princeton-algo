import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        // cast needed since no generic array creation in Java
        q = (Item[]) new Object[2];
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = q[i];
        q = temp;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (item == null) throw new NullPointerException();
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[N++] = item;                        // add item
    }

    /**
     * Removes and returns the random item on this queue.
     * @return the random item on this queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int index = StdRandom.uniform(N);
        Item item = q[index];
        q[index] = q[--N];
        q[N] = null;
        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }

    /**
     * return (but do not remove) a random item
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int index = StdRandom.uniform(N);
        return q[index];
    }
    
    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in RANDOM order, separated by spaces
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
     */

    /**
     * Returns an iterator that iterates over items in this queue in Randomly
     * @return an iterator that iterates over items in this queue in Randomly
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // an iterator, doesn't implement remove()
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] indexes;
        public RandomizedQueueIterator() {
            indexes = new int [N];
            for (int j = 0; j < N; j++)
                indexes[j] = j;
            StdRandom.shuffle(indexes);
        }

        public boolean hasNext() { return i < N; }
        public void remove()     { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[indexes[i++]];
            return item;
        }
    }

   /**
     * Unit tests the <tt>RandomizedQueue</tt> data type.
     */
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue) " + q);
    }
}
