package Chapter2;

import edu.princeton.cs.algs4.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wuser
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INI_CAPACITY = 4;
    
    private int n;
    private int m;
    private Key[] keys;
    private Value[] vals;
    
    public LinearProbingHashST(){
        this(INI_CAPACITY);
    }
    
    public LinearProbingHashST(int capacity){
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }
    
    public int size(){
        return n;
    }
    
    public boolean isEmpty(){
        return size() == 0;
    }
    
    public boolean contains(Key key){
        if(key == null)
            throw new NullPointerException();
        return get(key) != null;
    }
    
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % m;
    }
    
    private void resize(int capacity){
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
        for(int i = 0; i < m ; i++){
            if(keys[i] != null){
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
    }
    
    public void put(Key key, Value val){
        if(key == null)
            throw new NullPointerException();
        if(val == null){
            delete(key);
            return;
        }
        
        int i = 0;
        for(i = hash(key); keys[i] != null; i = (i+1)%m){
            if(keys[i].equals(key)){
                vals[i] = val;
                return;
            }
        }
        
        keys[i] = key;
        vals[i] = val;
        n++;
    }
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }
    
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);

        assert check();
    }
    
    private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }
    
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }
}
