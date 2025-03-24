package deque;

import java.util.Iterator;

import static java.lang.System.arraycopy;

public class ArrayDeque<T> implements Deque<T>{

    private int size;
    private T[] array;
    private int max;
    private int first;
    private int last;

    // general method
    // magical

    private void resize() {
        T[] a = (T[]) new Object[max];
        int x = first + 1;
        for(int i = 0;i < size;i += 1){
            if(x == size){
                x = 0;
            }
            a[i] = array[x];
            x += 1;
        }
        array = a;
    }

    public ArrayDeque(){
        size = 0;
        max = 8;
        first = 0;
        last = 1;
        array = (T[]) new Object[8];
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
        size -= 1;
        first += 1;
        if(first == max){
            first = 0;
        }
        T x = array[first];
        if(size < (double)max/4){
            max /= 4;
            max += 1;
            resize();
            last = size - 1;
            first = max - 1;
        }
        return x;
    }
    public T removeLast(){
        if(size == 0){
            return null;
        }
        size -= 1;
        last -= 1;
        if(last == -1){
            last = max - 1;
        }
        T x = array[last];
        if(size < (double)max/4){
            max /= 4;
            max += 1;
            resize();
            last = size - 1;
            first = max - 1;
        }
        return x;
    }
    public T get(int index){
        if(index > size){
            return null;
        } else if (size == 0) {
            return null;
        }
        if(first + index >= max){
            return array[first + 1 + index - max];
        }
        return array[first + 1 + index];
    }

    // unique method
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;

        ArrayDequeIterator() {
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
    public boolean equals(Object o){
        if(!(o instanceof ArrayDeque) || ((ArrayDeque<?>) o).size() != size){
            return false;
        }
        for(int i = 0;i < size;i += 1){
            if(((ArrayDeque<?>) o).get(i) != get(i)){
                return false;
            }
        }
        return true;
    }
}