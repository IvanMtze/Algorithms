package Chapter2;

import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

/**
 *
 * @author wuser
 */
public class SequentialSearchST<Key, Value> implements Iterable<Key>{
    private Node first;
    private int size;

    
    private class Node{
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    public Value get(Key key){
        for (Node i = first; i!=null; i = i.next){
            if(key.equals(i.key))
                return i.value;
        }
        return null;
    }
    
    public void put(Key key, Value value){
        if(key == null) 
            throw new IllegalArgumentException();
        if(value == null){
            delete(key);
            return;
        }
        for(Node x = first; x != null; x = x.next){
            if(key.equals(x.key)){
                x.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
        size++;
    }
    
    public int size(){
        return size;
    }
    
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null"); 
        first = delete(first, key);
    }

       
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            size--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    public boolean isEmtpy(){
        return size == 0;
    }
    
    public boolean contains(Key key){
        if (key == null)
            throw new IllegalArgumentException("The key given is null");
        return get(key) != null;
    }
    
    @Override
    public Iterator<Key> iterator() {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue.iterator();
    }
    
    
    public Iterable<Key> keys()  {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }

    
}
