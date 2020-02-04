/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1;

import java.util.Iterator;

/**
 *
 * @author wuser
 */
public class FixedCapacityStack<Item> implements Iterable<Item>{
    private Item[] a;
    private int n;
    
    public FixedCapacityStack(int tam){
        a =  (Item[]) new Object[tam];
    }
    
    public boolean isEmpty(){
        return n == 0;
    }
    
    public void push(Item i){
        if(n == a.length) resize(2*a.length);
        a[n++] = i;
    }
    
    public Item pop(){
        Item item = a[--n];
        a[n] = null;
        if(n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }
    
    private void resize(int max){
        Item[] temp = (Item[]) new Object[max];
        for(int i = 0; i < n; i++)
            temp[i] = a[i];
        a = temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }
    private class ReverseArrayIterator implements Iterator<Item>{
        private int i = n -1;
        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public Item next() {
            return a[i--]; 
       }
        
    }
}
