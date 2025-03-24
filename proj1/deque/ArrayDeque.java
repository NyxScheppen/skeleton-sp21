package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T>{

    private int size;
    private T[] array;
    private int max;
    private int first;
    private int last;

    // general method
    // magical

    public ArrayDeque(){
        size = 0;
        max = 8;
        first = 0;
        last = 0;
        array = (T[]) new Object[8];
    }

    private void resize() {
        T[] newItems = (T[]) new Object[max];
        for (int i = 0; i < size; i++) {
            newItems[i] = array[(first + i) % array.length];
        }
        first = 0;
        last = size;
        array = newItems;
    }


    public void addFirst(T item){
        if(size > max-1) {
            max *= 4;
            max += 1;
            resize();
            first = max-1;
        last = size;
        }
        if(first < 0){
            first = max-1;
        }
        array[first] = item;
        first -= 1;
        size += 1;
    }
    public void addLast(T item){
        if(size > max-1){
            max *= 4;
            max += 1;
            resize();
            last = size;
            first = max - 1;
        }
        if(last == max-1){
            last = 0;
        }
        array[last] = item;
        last += 1;
        size += 1;
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
             x = array[max-1];
        }
        else{
             x = array[first];
        }
        if(size < (double)max/4){
            max /= 4;
            max += 1;
            resize();
            last = size + 1;
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
            max /= 4;
            max += 1;
            resize();
            last = size;
            first = max - 1;
        }
        size -= 1;
        return x;
    }
    public T get(int index){
        if(index > size || size == 0 || index < 0){
            return null;
        }
        return array[(first + index) % (max-1)];
    }

    // unique method
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
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