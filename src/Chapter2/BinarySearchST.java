/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter2;

import edu.princeton.cs.algs4.Queue;

/**
 *
 * @author wuser
 * @param <Key>
 * @param <Value>
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int size = 0;

    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        values = tempv;
        keys = tempk;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < size && keys[i].compareTo(key) == 0) {
            return values[i];
        }
        return null;
    }

    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int lower = 0, higher = size() - 1;
        while (lower <= higher) {
            int mid = lower + (higher - lower) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                higher = mid + 1;
            } else if (cmp > 0) {
                lower = mid - 1;
            } else {
                return mid;
            }
        }
        return lower;
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (val == null) {
            delete(key);
            return;
        }
        int i = rank(key);
        if (i < size() && keys[i].compareTo(key) == 0) {
            values[i] = val;
            return;
        }

        if (size == keys.length) {
            resize(2 * keys.length);
        }

        for (int j = size(); j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = val;
        size++;

        assert check();
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return;
        }
        int i = rank(key);

        if (i == size() || keys[i].compareTo(key) != 0) {
            return;
        }
        for (int j = i; j < size() - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }
        size--;
        values[i] = null;
        keys[i] = null;
        if (size > 0 && size() == keys.length / 4) {
            resize(keys.length / 2);
        }
        assert check();
    }

    public void deleteMin() throws NoSuchFieldException {
        if (isEmpty()) {
            throw new NoSuchFieldException();
        }
        delete(min());
    }

    public void deleteMax() throws NoSuchFieldException {
        if (isEmpty()) {
            throw new NoSuchFieldException();
        }
        delete(max());
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchFieldError();
        }
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchFieldError();
        }
        return keys[size() - 1];
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException();
        }
        return keys[k];
    }

    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = rank(key);
        if (i < size() && key.compareTo(keys[i]) == 0) {
            return keys[i];
        }
        if (i == 0) {
            return null;
        } else {
            return keys[i - 1];
        }
    }

    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = rank(key);
        if (i == size()) {
            return null;
        } else {
            return keys[i];
        }
    }

    public int size(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException();
        }
        if (hi == null) {
            throw new IllegalArgumentException();
        }

        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }

        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) {
            return queue;
        }
        for (int i = rank(lo); i < rank(hi); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) {
            queue.enqueue(keys[rank(hi)]);
        }
        return queue;
    }

    private boolean check() {
        return isSorted() && rankCheck();
    }

    private boolean isSorted() {
        for (int i = 1; i < size(); i++) {
            if (keys[i].compareTo(keys[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (int i = 0; i < size(); i++) {
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) {
                return false;
            }
        }
        return true;
    }
}
