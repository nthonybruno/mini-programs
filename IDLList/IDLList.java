package classes;

import java.util.ArrayList;

//

public class IDLList<E> 
	{
	//Class for implenting a Double LL with fast accessing. 
	// Does so by its internal indexing to nodes
	
	
	class Node<E>{
		//Instance and Data Fields for private inner Class Node
		//Constructors
		
		E data;
		
		Node<E> next;
		Node<E> prev;
		
		Node (E elem){
			this.data = elem;
		}
		
		
	
		Node (E elem, Node<E> prev, Node<E> next){
			
			this.data = elem;
			
			this.prev = prev;
			
			this.next = next;
		}
		
	}
	
	//Other Data/Instance Fields
	
	private Node<E> head;
	private Node<E> tail;
	
	private int size;
	
	private ArrayList<Node<E>> indicies;
	
	
	
	//Additonal Constructor
	public IDLList() {
		
			indicies = new ArrayList<Node<E>>(1);
		
			head = new Node<E>(null);
			tail = new Node<E>(null);
		
			size = 0;
			tail.prev = head;
			head.next = tail;
			
			indicies.add(tail);
	}
	
	
	
	public boolean add (int index, E elem) {
		//Adds an element at the position Index
		int a;
		
		if (index < size){
			
			Node<E> temp = new Node<E>(elem,indicies.get(index),indicies.get(index+1));
			indicies.get(index).next = temp;
			
			
			indicies.get(index).next.prev = temp;
			indicies.add(index,temp);
			
			size= size + 1;
			a= size - 1;
			
			return true;
			
		}
		
			else if(index == size--)
		{
				append(elem);
				return true;
		}
		
			else if(index == 0)
			{
				add(elem);
				return true;
			}
		
		else
			{
				return false;
			}
	}
	
	public boolean add (E elem) {
		//Adds elem at the head using fast accessing.
		
		Node<E> addnode = new Node<E>(elem, null, head);
		
		
		indicies.add(0,addnode);
		
		head.prev = addnode;
		head = addnode;
		
		size=size+1;
		
		return true;
	}
	
	 public boolean append (E elem) {
		 
		 //Adds Elem to the to the last element of the list
		 
		Node<E> appendnode = new Node<E>(elem, tail, null);
		
		if(size() == 0)
		{
			add(elem);
		}
		
		else if(size() != 0)
		{
			indicies.add(size,appendnode);
			tail.next = appendnode;
			
			tail = appendnode;
			
			size=size+1;
		}
		
		else {
			//
		}
		return true;
		
	 }
	 
	 
	 public E get (int index) {
		 //Returns the element at an index
		 
		 return indicies.get(index).data;
	 }
	 
	 public E getHead () 
	 //Returns the first element(the head)
	 {
		 
		 
		 return head.data;
	 }
	
	
	 public E getLast () 
	 //Returns the last element(the tail)
	 {
		 return tail.data;
	 }
	 
	 public int size() 
	 //Returns the size of the LL
	 {
		 int a = size;
		 return a;
	 }
	 
	 public E remove() {
		 //Removes the head of the LL and returns that element
		 E newremove = head.data;
		 
		indicies.get(1).prev = null;
		
		indicies.remove(0);
		head = indicies.get(0);
		
		size = size -1;
		return newremove;
	 }
	 
	 public E removeLast () 
	 {
		 //Removes the last element/the tail in the LL and returns it
		 E removeLasttemp = tail.data;
		 
		 int a = size -1;
		 int b = size -2;
		 
		indicies.get(b).next = null;
		indicies.remove(a);
		
		tail = indicies.get(a);
		size= size -1;
		
		
		return removeLasttemp;
	 }
	 
	 public E removeAt (int index)
	 {
		 //Removes the element at an index and returns it
		 E removeAtindex = indicies.get(index).data;
		 
		 if (index == 0) 
		 {
				remove();
				
		 }
		 
		 else if (index < size)
		 {
			 //Checks to make sure the index is less than the LL's size
			 int a = index-1;
			 int b = index +1;
				indicies.get(a).next = indicies.get(b);
				
				
				indicies.get(b).prev = indicies.get(a);
				
				indicies.remove(index);
				
				size--;
			}
		 
		 else if(index == size-1)
			 //Checks if the index is equal to the one under size
		 {
			 removeLast();
		 }
			return removeAtindex;
	 }
	 
	 
	 
	 public boolean remove (E elem)
	 {
		 //Removes the first occurence of elem in the list and returns true
		 
		 for (int scan=0; scan<size;scan++)
		 {
				if (indicies.get(scan).data == elem)
				{
					
					removeAt(scan);
				}
			}
		 
			return true;
	 }
	 
	 public String toString() 
	 //Returns a string formatted version of the list
	 
	 {
		 String newrep = new String();
		 
			for (int scan2=0; scan2<size;scan2++)
			{
				newrep+=indicies.get(scan2).data;
				
				newrep+= ";";
			}
			
			
			return newrep.substring(0,newrep.length()-1);
	 }
	 
	 
	 public static void main(String[] args)
	 {
		//Main method-- empty
		 }
	 }