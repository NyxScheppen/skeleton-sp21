package deque;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class ArrayDeque<T> implements Deque<T>,Iterable<T>{

    private int size;
    private T[] array;
    private int max;
    private int first;
    private int last;

    // magical

    public ArrayDeque(){
        size = 0;
        max = 8;
        first = 0;
        last = 0;
        array = (T[]) new Object[8];
    }

    private void resize(int m) {
        T[] newItems = (T[]) new Object[m];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        array = newItems;
    }


    public void addFirst(T item){
        size += 1;
        if(size > max-1) {
            resize(max * 4 + 1);
            max *= 4;
            max += 1;
            first = max-1;
            last = size;
        }
        if(last == first){
            last += 1;
        }
        array[first] = item;
        first -= 1;
        if(first < 0){
            first = max-1;
        }

    }
    public void addLast(T item){
        size += 1;
        if(size > max-1){
            resize(max * 4 + 1);
            max *= 4;
            max += 1;
            last = size;
            first = max - 1;
        }
        if(last == first){
            first = max - 1;
        }
        array[last] = item;
        last += 1;
        if(last == max){
            last = 0;
        }
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        int x = first + 1;
        for(int i = 0;i < size;i++){
            if(array[x] != null){
                System.out.print(array[x]+"\n");
            }
            x += 1;
            if(x == max){
                x = 0;
            }
        }
    }
    public T removeFirst(){
        if(size == 0){
            return null;
        }
        first += 1;
        T x;
        if(first == max){
            first = 0;
             x = array[0];
        }
        else{
             x = array[first];
        }
        if(size < (double)max/4){
            resize( max / 4 + 1);
            max /= 4;
            max += 1;
            last = size;
            first = 0;
        }
        size -= 1;
        return x;
    }
    public T removeLast(){
        if(size == 0){
            return null;
        }
        last -= 1;
        T x;
        if(last == -1){
            last = max - 1;
             x = array[0];
        }
        else{
             x = array[last];
        }
        if(size < (double)max/4){
            resize(max / 4 + 1);
            max /= 4;
            max += 1;
            last = size - 1;
            first = max - 1;
        }
        size -= 1;
        return x;
    }
    public T get(int index){
        if(index > size || size == 0 || index < 0){
            return null;
        }
        return array[(first + index + 1) % max];
    }

    // unique method
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> M = new ArrayDeque<>();
        for (int i = 0; i < 100; i += 1) {
            int operationNumber;
            if (M.size() > 0) {
                operationNumber = StdRandom.uniform(0, 5);
            } else {
                operationNumber = 0;
            }
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                System.out.println(M.size());
            } else if (operationNumber == 2) {
                //removeLast
                int m = M.removeLast();
            } else if (operationNumber == 3) {
                System.out.println(M.get(M.size() - 1));
            } else if (operationNumber == 4) {
                M.removeFirst();
            }
        }
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;

        public ArrayDequeIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T item = get(index);
            index += 1;
            return item;
        }
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque<?>) {
            if (this.size != ((Deque<?>) o).size()) {
                return false;
            }
            int i = 0;
            for (T item : this) {
                if (!item.equals(((Deque<?>) o).get(i))) {
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }
}

