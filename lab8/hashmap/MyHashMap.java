package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @nyx YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    private int initialSize;

    public MyHashMap() {
        initialSize = 16;
        loadFactor = 0.75;
        buckets = createTable(initialSize);
    }

    public MyHashMap(int initialSize) {
        loadFactor = 0.75;
        this.initialSize = initialSize;
        buckets = createTable(initialSize);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = createTable(initialSize);
        this.loadFactor = loadFactor;
        this.initialSize = initialSize;
    }

    private Node createNode(K key, V value) {
        return new Node(key,value);
    }


    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */

    private Collection<Node>[] createTable(int tableSize) {
       Collection[] bucketlist = new Collection[tableSize];
       for(int i = 0;i < tableSize;i++){
           bucketlist[i] = createBucket();
       }
       return bucketlist;
    }

    @Override
    public void clear() {
        size = 0;
        initialSize = 16;
        loadFactor = 0.75;
        buckets = createTable(initialSize);
    }

    @Override
    public boolean containsKey(K key) {
       int pos = getPosition(key);
       Collection<Node> list = buckets[pos];
        for(Node p:list){
            if(p.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int pos = getPosition(key);
        Collection<Node> list = buckets[pos];
        if (list == null) {
            return null;
        }
        for (Node p : list) {
            if (p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if(isOverload()){
            resize();
        }
        Node newnode = createNode(key,value);
        int pos = getPosition(key);
        for (Node node : buckets[pos]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[pos].add(newnode);
        size += 1;
    }

    @Override
    public Set<K> keySet() {
        if(size == 0){
            return null;
        }
        Set<K> key = new HashSet<>();
        for(int i = 0; i < initialSize; i += 1){
            Collection<Node> list = buckets[i];
            if(list.iterator().hasNext()){
                for(Node n : list){
                    key.add(n.key);
                }
            }
        }
        return key;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new hsmpIterator<K>();
    }

    private int getPosition(K key) {
        return Math.floorMod(key.hashCode(), initialSize);
    }

    private boolean isOverload() {
        return (double) size / initialSize >= loadFactor;
    }

    private void resize(){
        MyHashMap<K, V> temp = new MyHashMap<>(initialSize * 2);
       // ?why
        for(int i = 0; i < initialSize; i += 1){
            Collection<Node> list = buckets[i];
            if(list.iterator().hasNext()){
                for(Node n : list){
                    temp.put(n.key,get(n.key));
                }
            }
        }
        initialSize *= 2;
        buckets = temp.buckets;
        loadFactor = initialSize/size;
    }

    private class hsmpIterator<K> implements Iterator{

        private Collection<Node> finder;
        private Collection<Node>[] bkt = buckets;
        private int nowposition = findPos(0);
        private Iterator<Node> iter;

        hsmpIterator(){
            finder = buckets[nowposition];
            iter = finder.iterator();
        }

        private int findPos(int cur) {
            int pos = cur;
            while (pos < initialSize && bkt[pos] == null) {
                pos++;
            }
            return pos;
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext() || findPos(nowposition + 1) < initialSize;
        }

        @Override
        public K next() {
            if(iter.hasNext()){
               return (K) iter.next().key;
            } else{
                nowposition = findPos(nowposition + 1);
                finder = bkt[nowposition];
                iter = finder.iterator();
                return (K) iter.next().key;
            }
        }
    }
}
