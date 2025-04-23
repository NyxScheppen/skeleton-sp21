package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    private int size;
    private BSTNode root;

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class BSTNode{

        public K key;

        public V value;

        public BSTNode left;

        public BSTNode right;

        public BSTNode(V value,K key){
            this.value = value;
            this.key = key;
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    public void clear(){
        root = null;
        size = 0;
    }

    public boolean containsKey(K key) {

        BSTNode search = root;

        while(search != null) {
            if (key.compareTo(search.key) < 0) {
                search = search.left;
            } else if(key.compareTo(search.key) > 0) {
                search = search.right;
            } else{
              return true;
            }
        }
        return false;
    }

    public V get(K key) {
        BSTNode search = root;

        while(search != null) {
            if (key.compareTo(search.key) < 0) {
                search = search.left;
            } else if(key.compareTo(search.key) > 0) {
                search = search.right;
            } else{
                return search.value;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    public void put(K key, V value) {
        BSTNode search = root;

        if(search == null){
            root = new BSTNode(value,key);
            size += 1;
            return;
        }

        while (search.left != null && search.right != null) {
            if (key.compareTo(search.key) < 0) {
                search = search.left;
            } else if (key.compareTo(search.key) > 0) {
                search = search.right;
            }
            else return;
        }

        if (key.compareTo(search.key) < 0) {
            BSTNode newnode = new BSTNode(value,key);
            search.left = newnode;
        } else if (key.compareTo(search.key) > 0) {
            BSTNode newnode = new BSTNode(value,key);
            search.right = newnode;
        }
        size += 1;

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    public void printInOrder(){
        BSTNode search = root;

        while(search.left != null) {
            search = search.left;
        }
        while(search != null){
            System.out.print(search.value);
            search = search.right;
        }
    }
}
