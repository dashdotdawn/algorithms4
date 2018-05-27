import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] queue;
  private int size;

  public RandomizedQueue() {
    queue = (Item []) new Object[1];
  }
  private void resize(int capacity) {
    Item[] newQueue = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      newQueue[i] = queue[i];
    }
    queue = newQueue;
  }
  private void swap(int p, int q) {
    Item item = queue[p];
    queue[p] = queue[q];
    queue[q] = item;
  }
  public boolean isEmpty() {
    return size == 0;
  }
  public int size() {
    return size;
  }
  public void enqueue(Item item) {
    if (item == null) throw new java.lang.IllegalArgumentException();
    if (size == queue.length) resize(queue.length * 2);
    queue[size++] = item;
  }
  public Item dequeue() {
    if (size == 0) throw new java.util.NoSuchElementException();
    if (size == queue.length / 4) resize(queue.length / 2);
    int k = StdRandom.uniform(size);
    Item item = queue[k];
    swap(k, --size);
    return item;
  }
  public Item sample() {
    if (size == 0) throw new java.util.NoSuchElementException();
    int k = StdRandom.uniform(size);
    return queue[k];
  }
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }
  private class RandomizedQueueIterator implements Iterator<Item> {
    private int current = 0;
    private Item[] iteratorQueue;
    
    public RandomizedQueueIterator() {
      iteratorQueue = (Item[]) new Object[size];
      for (int i = 0; i < size; i++) {
        iteratorQueue[i] = queue[i];
      }
    }
    private void swap(int p, int q) {
      Item item = iteratorQueue[p];
      iteratorQueue[p] = iteratorQueue[q];
      iteratorQueue[q] = item;
    }
    public boolean hasNext() {
      return current < size;
    }
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
    public Item next() {
      if (!hasNext()) throw new java.util.NoSuchElementException();
      int k = StdRandom.uniform(current, size);
      Item item = iteratorQueue[k];
      swap(k, current++);
      return item;
    }
  }
  public static void main(String[] args) {
    RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
    for (int i = 0; i < 10; i++) {
      q.enqueue(i);
    }
    for (int s: q) {
      StdOut.print(s + " ");
    }
    StdOut.println();
    for (int s: q) {
      StdOut.print(s + " ");
    }
  }
}
