package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>,Iterable<T>{
    private int size;

    // anyway,here is the class that can generate the node
    // or whatever,my English is poor

    private class node{
        public T item;
        public node front;
        public node next;

        public node(T a){
            item = a;
        }
    }

    private node sentinal;

    // general method
    // double way linked list

    public LinkedListDeque(){
        size = 0;
        sentinal = new node(null);
        sentinal.next = sentinal;
        sentinal.front = sentinal;
    }

    // const time

    public void addFirst(T item){
        size += 1;
        node n = new node(item);
        n.next = sentinal.next;
        n.next.front = n;
        sentinal.next = n;
        n.front = sentinal;
    }

    public void addLast(T item){
        size += 1;
        node n = new node(item);
        n.front = sentinal.front;
        n.front.next = n;
        sentinal.front = n;
        n.next = sentinal;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        node m = sentinal.next;
        while(m!= sentinal){
          System.out.print(m.item + "\n");
          m = m.next;
        }
    }

    // const time

    public T removeFirst(){
        if(size == 0){
            return null;
        }
        size -= 1;
        sentinal.next.next.front = sentinal;
        T x = sentinal.next.item;
        sentinal.next = sentinal.next.next;
        return x;
    }

    public T removeLast(){
        if(size == 0){
            return null;
        }
        size -= 1;
        sentinal.front.front.next = sentinal;
        T x = sentinal.front.item;
        sentinal.front = sentinal.front.front;
        return x;
    }

    public T get(int index){
        if(size == 0){
            return null;
        } else if (index > size) {
            return null;
        }
        node m = sentinal.next;
        for(int i = 1;i < index;i++){
            m = m.next;
        }
        return m.item;
    }

    private class LinkedListDequeIterator implements Iterator<T>{
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
    public Iterator<T> iterator(){
        return new LinkedListDequeIterator();
    }

    public boolean equals(Object o){
        if(!(o instanceof LinkedListDeque) || ((LinkedListDeque<?>) o).size() != size){
            return false;
        }
        for(int i = 0;i < size;i += 1){
            if(((LinkedListDeque<?>) o).get(i) != get(i)){
                return false;
            }
        }
        return true;
    }
}