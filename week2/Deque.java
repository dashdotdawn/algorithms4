import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int size;

  private class Node {
    Item item;
    Node prev;
    Node next;
  }
  public Deque() {

  }
  public boolean isEmpty() {
    return size == 0;
  }
  public int size() {
    return size;
  }
  public void addFirst(Item item) {
    if (item == null) throw new java.lang.IllegalArgumentException();
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    if (isEmpty()) last = first;
    else oldfirst.prev = first;
    size++;
  }
  public void addLast(Item item) {
    if (item == null) throw new java.lang.IllegalArgumentException();
    Node oldLast = last;
    last = new Node();
    last.item = item;
    last.prev = oldLast;
    if (isEmpty()) first = last;
    else oldLast.next = last;
    size++;
  }
  public Item removeFirst() {
    if (isEmpty()) throw new java.util.NoSuchElementException();
    Item item = first.item;
    first = first.next;
    size--;
    if (isEmpty()) last = null;
    else first.prev = null;
    return item;
  }
  public Item removeLast() {
    if (isEmpty()) throw new java.util.NoSuchElementException();
    Item item = last.item;
    last = last.prev;
    size--;
    if (isEmpty()) first = null;
    else last.next = null;
    return item;
  }
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
  private class DequeIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext() {
      return current != null;
    }
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
    public Item next() {
      if (!hasNext()) throw new java.util.NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<Integer>();
    deque.addLast(1);
    deque.addLast(2);
    deque.addFirst(3);
    deque.addLast(4);
    deque.removeLast();
    StdOut.println(deque.size());
    for (int i : deque) {
      StdOut.println(i);
    }

  }
}