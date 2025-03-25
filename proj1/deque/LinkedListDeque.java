package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>,Iterable<T> {
    private int size;

    // anyway,here is the class that can generate the node
    // or whatever,my English is poor

    private class node {
        public T item;
        public node front;
        public node next;

        public node(T a) {
            item = a;
        }
    }

    private node sentinal;

    // general method
    // double way linked list

    public LinkedListDeque() {
        size = 0;
        sentinal = new node(null);
        sentinal.next = sentinal;
        sentinal.front = sentinal;
    }

    // const time
    @Override
    public void addFirst(T item) {
        size += 1;
        node n = new node(item);
        n.next = sentinal.next;
        n.next.front = n;
        sentinal.next = n;
        n.front = sentinal;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        node n = new node(item);
        n.front = sentinal.front;
        n.front.next = n;
        sentinal.front = n;
        n.next = sentinal;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        node m = sentinal.next;
        while (m != sentinal) {
            System.out.print(m.item + "\n");
            m = m.next;
        }
    }

    // const time
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        sentinal.next.next.front = sentinal;
        T x = sentinal.next.item;
        sentinal.next = sentinal.next.next;
        return x;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        sentinal.front.front.next = sentinal;
        T x = sentinal.front.item;
        sentinal.front = sentinal.front.front;
        return x;
    }

    @Override
    public T get(int index) {
        if (size == 0 || index > size || index < 0) {
            return null;
        }
        node m = sentinal.next;
        for (int i = 0; i < index; i++) {
            m = m.next;
        }
        return m.item;
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int index;

        public LinkedListDequeIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            T item = get(index);
            index += 1;
            return item;
        }
    }

    // unique method
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinal.next);
    }

    private T getRecursive(int index, node p) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        if (index == 0) {
            return p.item;
        } else {
            return getRecursive(index - 1, p.next);
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque) || ((Deque<T>) o).size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i += 1) {
            if (((Deque<T>) o).get(i) != this.get(i)) {
                return false;
            }
        }
        return true;
    }
}