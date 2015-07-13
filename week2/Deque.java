import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;               // number of elements on queue
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;        
    }

    /**
     * Initializes an empty queue.
     */
    public Deque() {
        first = null;
        last  = null;
        N = 0;
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;     
    }

    /**
     * Adds the item to this queue in first position.
     * @param item the item to add
     */
    public void addFirst(Item item) {
    	if (item.toString() == "") throw new NullPointerException();
    	Node<Item> oldfirst = first;
    	first = new Node<Item>();
    	first.item = item;
    	first.prev = null; // should be circular ?
    	if(N==0) {
    		last = first;
    		first.next = null;
    	} else {
    		oldfirst.prev = first;
    		first.next = oldfirst;
    	}
        N++;
    }

    public void addLast(Item item) {
    	if (item.toString() == "") throw new NullPointerException();  	
    	Node<Item> oldlast = last;
    	last = new Node<Item>();
    	last.item = item;
    	last.next = null; // should be circular ?
    	if(N==0) {
    		first = last;
    	} else {
    		oldlast.next = last;
    		last.prev = oldlast;
    	}
        N++;
    }
    
    public void removeFirst() {
    	if(N > 0) {
    		Node<Item> tmp = first;
    		first = first.next; // should be circular ?
        	N--;
        	tmp=null;
    	} else {
    		throw new NoSuchElementException();
    	}
    }

    public void removeLast() {
    	if(N > 0) {
    		Node<Item> tmp = last;
    		last = last.prev; // should be circular ?
    		last.next = null;
        	N--;
        	tmp=null;
    	} else {
    		throw new NoSuchElementException();
    	}
    }
   
    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
        return new ListIterator<Item>(first);  
	}

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() { return current != null; }
        public void remove()     { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deque<String> d = new Deque<String>();
		d.addFirst("abhi");
		d.addFirst("taware");
		d.addFirst("shantaram");
		d.addLast("anaya");
		System.out.println(d);
		d.removeFirst();
		System.out.println(d);
		d.removeLast();
		System.out.println(d);
	}

}
