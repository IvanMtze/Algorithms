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
 */
public class RedBlackBST<Key extends Comparable, Value> {
    
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    private Node root;
    
    private class Node{
        Key key;
        Value value;
        Node left, right;
        int n;
        boolean color;

        public Node(Key key, Value value, int n, boolean color) {
            this.key = key;
            this.value = value;
            this.n = n;
            this.color = color;
        }
    }


    private boolean isRed(Node node){
        if(node == null)
            return false;
        return node.color == RED; 
    }
    
    public int size(){
        return  size(root);
    }
    
    private int size(Node x){
        if(x == null)
            return 0;
        return x.n;
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public Value get(Key key){
        if(key == null)
            throw new NullPointerException();
        return get(root, key);
    }
    
    private Value get(Node x, Key key){
        while( x!= null){
            int cmp = key.compareTo(x.key);
            if(cmp < 0) x = x.left;
            else if(cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }
    
    public boolean contains(Key key){
        return get(key) != null 
;    }
    
    public void put(Key key, Value value){
        if (key == null)
            throw new NullPointerException(); 
        if (value == null){
            delete(key);
            return;
        }
        root = put(root, key, value);
        root.color = BLACK;
    }
    
    private Node put(Node h, Key key, Value value){
        
        if(h == null) return new Node(key, value,1, RED);
        
        int cmp = key.compareTo(h.key);
        if (cmp < 0 ) h.left = put(h.left, key, value);
        else if(cmp > 0 ) h.right = put(h.right, key, value);
        else h.value = value;
        
        if(isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))
            flipColors(h);
        
        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }
    
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    
    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        x.color = RED;
        return x;
    }
    
    private void flipColors(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }
    
    private Node moveRedLeft(Node h){
        flipColors(h);
        if(isRed(h.right.left)){
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }
    
    private Node moveRedRight(Node h){
        flipColors(h);
        if(isRed(h.left.left)){
            h.left = rotateRight(h);
            flipColors(h);
        }
        return h;
    }
    
    private Node balance(Node h){
        if(isRed(h.right))
            h = rotateLeft(h);
        
        if(isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        
        if(isRed(h.left) && isRed(h.right))
            flipColors(h);
        
        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }
    
    public int height(){
        return height(root);
    }
    
    private int height(Node x){
        if(x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
    
    public Key min(){
        if(isEmpty())
            throw new NullPointerException();
        return min(root).key;
    }
    
    private Node min(Node x){
        if(x.left == null)
            return x;
        else 
            return min(x.left);
    }
    
    public Key max(){
        if(isEmpty())
            throw new NullPointerException();
        return max(root).key;
    }
    private Node max(Node x){
        if(x.right == null)
            return x;
        else
            return max(x.right);
    }
    
    public Key floor(Key key){
        if(key == null)
            throw new NullPointerException();
        if(isEmpty())
            throw new NullPointerException();
        Node x = floor(root, key);
        if (x == null)
            return null;
        else 
            return x.key;
    }
    
    private Node floor(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node t = floor (x.right,key);
        if (t != null)
            return t;
        else
            return x;
    }
    
    public Key ceiling(Key key){
        if (key == null)
            throw new NullPointerException();
        if (isEmpty())
            throw new NullPointerException();
        Node x = ceiling(root, key);
        if (x == null)
            return null;
        else
            return x.key;
   }
    
    private Node ceiling(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return x;
        if(cmp > 0)
            return ceiling(x.right, key);
        Node t = ceiling(x.left,key);
        if(t != null)   
            return t;
        else
            return x;
    }
    
    public Key select(int k){
        if(k < 0 || k >= size())
            throw new IllegalArgumentException();
        Node x = select(root, k);
        return x.key;
        
    }
    
    private Node select(Node x, int k){
        int t = size(x.left);
        if (t > k)
            return select(x.left, k);
        else if (t < k)
            return select(x.left, k - 1 - t);
        else 
            return x;
    }
    
    public int rank(Key key){
        if (key == null)
            throw new NullPointerException();
        return rank(root, key);
    }
    
    private int rank(Node x, Key key){
        if(x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            return rank(x.left, key);
        else if(cmp > 0)
            return 1 + size(x.left) + size(x.right);
        else 
            return size(x.left);
    }
    
    public Iterable<Key> keys(){
        if (isEmpty())
            return new Queue<Key>();
        return keys(min(), max());
    }
    
    public Iterable<Key> keys(Key lo, Key max){
        if(lo == null || max == null)
            throw new NullPointerException();
        Queue<Key> queue  = new Queue<Key>();
        keys(root, queue, lo, max);
        return queue;
    }
    
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi){
        if(x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if( cmplo < 0)
            keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)
            queue.enqueue(x.key);
        if(cmphi > 0)
            keys(x.right, queue, lo, hi);
    }
    
    public int size(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is null");
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
    
    public void deleteMin() {
        if (isEmpty()) throw new NullPointerException();

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }
    
        public void deleteMax() {
        if (isEmpty()) throw new NullPointerException();

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }
    
        public void delete(Key key) { 
        if (key == null) throw new IllegalArgumentException();
        if (!contains(key)) return;

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) { 

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }
}
