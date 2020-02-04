/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter2;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author wuser
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value>{
    private Node root;

    private boolean isEmpty() {
        return root != null;
    }
    
    private class Node{
        private Key key; //key
        private Value val; //associated value
        private Node left, rigth;
        private int n; //# nodes in a subtree rooted here

        public Node(Key key, Value val, int n) {
            this.key = key;
            this.val = val;
            this.n = n;
        }
        
    }
    public int size(){
        return size(root);
    }
    
    private int size(Node x){
        return x == null? 0 : x.n;
    }
    
    public Value get(Key key){
        return get(root,key);
    }
    
    private Value get(Node x, Key key){
        if(x==null) return null;
        int cmp = x.key.compareTo(key);
        if(cmp<0) return get(x.left, key);
        else if(cmp > 0) return get(x.rigth, key);
        else return x.val;
    }
    
    public void put(Key key, Value val){
        root = put(root, key, val);
    }
    
    private Node put(Node x, Key key, Value val){
        if(x==null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = put(x.left, key, val);
        else if(cmp > 0) x.rigth = put(x.rigth, key, val);
        else x.val = val;
        x.n = size(x.left) + size(x.rigth) + 1;
        return x;
    }
    
    public Key min(){
        if (isEmpty()) throw new NullPointerException();
        Node x = min(root);
        return x.key;
    }
    
    public Node min(Node x){
        if(x.left == null) return x;
        return min(x.left);
    }
    
    public Key select(int k){
        if(k < 0 || k > size())
            throw new IllegalArgumentException();
        Node x = select(root, k);
        return x.key;
    }
    
    private Node select(Node x, int key){
        if(x == null) return null;
        int t = size(x);
        if(t > key)
            return select(x.left, key);
        else if(t < key)
            return select(x.left, key -t-1);
        else 
            return x;
    }
    
    public Key floor(Key key) throws NoSuchFieldException{
        Node x = floor(root, key);
        if (x == null) 
            throw new NoSuchFieldException();
        return x.key;
    }
    
    private Node floor(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) 
            return x;
        if(cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.rigth, key);
        if(t != null)
            return t;
        else
            return x;
    }
    
    public int rank(Key key){
        return rank(key, root);
    }
    
    private int rank(Key key, Node x){
        if(x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return rank(key, x.left);
        else if(cmp > 0 ) return 1 + size(x.left)  + rank(key, x.rigth);
        else return size(x.left);
    }
    
    public void delete(Key key){
        root = delete(root, key);
    }
    
    private Node delete(Node x, Key key){
        if (x == null) 
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp  < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.rigth = delete(x.rigth, key);
        else{
            if(x.rigth == null)
                return x.left;
            if(x.left == null)
                return x.rigth;
            Node t = x;
            x = min(t.rigth);
            x.rigth = deleteMin(t.rigth);
            x.left = t.left;
        }
        x.n = size(x.left) + size(x.rigth) + 1;
        return x;
    }
    
    public void deleteMin(){
        if(isEmpty())
            throw new NullPointerException();
        root = deleteMin(root);
    }
    
    public Node deleteMin(Node x){
        if(x.left == null)
            return x.rigth;
        x.left = deleteMin(x.left);
        x.n = size(x.left) + size(x.rigth) +1;
        return x;
    }
    
    public void deleteMax() {
        if (isEmpty()) throw new NullPointerException();
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.rigth == null) return x.left;
        x.rigth = deleteMax(x.rigth);
        x.n = size(x.left) + size(x.rigth) + 1;
        return x;
    }
    
     public Key max() {
        if (isEmpty()) throw new NullPointerException("calls max() with empty symbol table");
        return max(root).key;
    } 

    private Node max(Node x) {
        if (x.rigth == null) return x; 
        else                 return max(x.rigth); 
    } 
    
    public Iterable<Key> keys(){
        return keys(min(), max());
    }
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.rigth, queue, lo, hi); 
    } 

    
    private boolean check() {
        if (!isBST())            StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.rigth, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.n != size(x.left) + size(x.rigth) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.rigth);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

}
