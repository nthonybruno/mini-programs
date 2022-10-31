package hw;

import java.util.Random;

public class Treap<E extends Comparable<E>> {
	
	/**
	 * 
	 * @author Anthony Bruno
	 * @purpose Generates a TREAP
	 * @param <E>
	 */
	
	private static class Node<E> {
		
		//Inner-class genereates a new Node of type E
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		
		
		private Node(E data ,int priority){
			if(data == null) {
				throw new NullPointerException("Error, the Data of the Node Cannot be Null");
			}
			
			this.data = data;
			this.priority = priority;
		}
		
		/**
		 * Rotates the BST to the right according to heirarchal priority
		 */
		void rotateRight()
		{
			Node<E> reference = new Node<E>(this.data, this.priority);
			
			if(this.left != null) {
				
				if(this.left.right != null)
				{
					
					reference.left = this.left.right;
				}
				
				if(this.right != null)
				{
					reference.right = this.right;
				}
				
				
				this.data = this.left.data;
				
				this.priority = this.left.priority;
				
				
				this.right = reference;
				
				if(this.left.left != null)
				{
					this.left = this.left.left;
				}
				else {
					this.left = null;
				}
				
			}
		}
		
		
		/**
		 * Rotates right
		 */
		
		void rotateLeft()
		{
			Node<E> reference = new Node<E>(this.data, this.priority);
			
			if(this.right != null) {
				
				if(this.right.left != null)
				{
					
					reference.right = this.right.left;
				}
				
				if(this.left != null)
				{
					reference.left = this.left;
				}
				
				
				this.data = this.right.data;
				
				this.priority = this.right.priority;
				
				
				this.left = reference;
				
				if(this.right.right != null)
				{
					this.right = this.right.right;
				}
				else {
					this.right = null;
				}
				
			}
			
		}
		
		
	}
	
	/**
	 * Private instance variables for TREAP
	 */
	private Random  priorityGenerator;
	private Node <E> root; // Must be comparable
	
	
	
	/**'
	 * Blank constructor for TREAP
	 */
	public Treap()
	{
		this.priorityGenerator = new Random();
		this.root = null;
	}
	
	
	/**
	 * Constructor for treap that takes a long seed which is used for priority generating
	 * @param seed
	 */
	public Treap(long seed){
		this.priorityGenerator = new Random(seed);
		this.root = null;
		
	}
	
	/**
	 * Boolean variables initilization
	 * USed in the add, delete and search methods
	 */
	boolean insertionSuccess = false;
	boolean deletionSuccess = false;
	boolean searchSuccess = false;
	
	/**
	 * Inserts a node into a given tree, then calls insert and search in order to place it in
	 * the correct spot.
	 * 
	 * @param key
	 * @return insertionSuccess
	 */
	public boolean add(E key)
	{
		return add(key, priorityGenerator.nextInt(100));		
	}
	
	/**
	 * Used as a helper for add(E key) and as its own method to handle keys and priorities
	 * @param key
	 * @param priority
	 * @return
	 */
	public boolean add(E key, int priority)
	{
		root = insert(root, key, priority);
		
		return insertionSuccess;		
	}
	
/**
 * 	
 * This method is called by insert to first determine if the key is in the treap, if it is
 *  then it returns True, else it returns false 
 *  
 *  The variable in is used to determine whether or not it is in the treap
 * 
 * @param check
 * @return in
 */	
	private Node<E> insert(Node<E> aRoot, E key, int priority)
	{
	
		/**
		if(search(aRoot) == true)
		{
			insertionSuccess = false;
			return aRoot;
		}
		else
		{
			insertionSuccess = true;
		}	
		
		**/
		
		if(aRoot == null)
		{
			insertionSuccess = true;
			return new Node<E>(key, priority);
		}
		
		
		else if (key.compareTo(aRoot.data) < 0)
		{
			aRoot.left = insert(aRoot.left,key, priority);
			
			
			if(aRoot.left.priority <= aRoot.priority)
			{
				//
			}
			
			else if (aRoot.left.priority > aRoot.priority)
			{
				aRoot.rotateRight();
			}
						
		}

		else if (key.compareTo(aRoot.data) > 0)
		{
			aRoot.right = insert(aRoot.right, key, priority);
			
			if(aRoot.right.priority > aRoot.priority)
			{
				aRoot.rotateLeft();
			}
		}
		
		
		else
		{
			insertionSuccess = false;
		}
		
		return aRoot;
		
	}
	
	
	/**
	 * The parent for deleting, it calls deleteNode which handles the iterations
	 * @param key
	 * @return
	 */
	public boolean delete(E key)
	{
		root = deleteNode(root, key);
		
		return deletionSuccess;
	}
	
	/**
	 * Iterator for delete(E key)
	 * @param aRoot
	 * @param key
	 * @return
	 */
	private Node<E> deleteNode(Node<E> aRoot, E key)
	{
		if(aRoot == null)
		{
			deletionSuccess = false;
			return null;
		}
		
		else if (key.compareTo(aRoot.data) < 0)
		{
			aRoot.left = deleteNode(aRoot.left,key);
			
		}
		
		else if (key.compareTo(aRoot.data) > 0)
		{
			aRoot.right = deleteNode(aRoot.right,key);
		}
		
		else
		{
			deletionSuccess = true;
			
			if(aRoot.left == null)
			{
				if(aRoot.right == null)
				{
					return null;
				}
			}
			
			
			else if(aRoot.right == null)
			{
				aRoot.rotateRight();
				aRoot.right = deleteNode(aRoot.right, key);
				
			}
			else if(aRoot.left == null)
			{
				aRoot.rotateLeft();
				aRoot.left = deleteNode(aRoot.left,key);
				
			}
			
			else if(aRoot.right.priority < aRoot.left.priority)
				{
						aRoot.rotateRight();
						aRoot.right = deleteNode(aRoot.right,key);
				}
				
				else
				{
					aRoot.rotateLeft();
					aRoot.left = deleteNode(aRoot.left,key);
				}
				
			
		}
				return aRoot;
			}
	
		
	
	
	
		/**
		 * 
		 * 
		 * Excess code for original insert helper function, may serve function in the future
		 * 
		 * 
		Node<E> node = new Node<E>(key, priority);
		
		if(search(node) == true)
		{
			insertionSuccess = false;
			return node;
		}
		else
		{
			insertionSuccess = true;
		}
		
		Node<E> parent = null;
		Node<E> current = root;
		
		while(current != null)
		{
			parent = current;
			
			if(key.compareTo(current.data) >= 0)
			{
				current = current.right;
			}
			else
			{
				current = current.left;
			}
			
		}
		if(key.compareTo(parent.data) >= 0)
		{
			parent.right = node;
		}
		else
		{
			parent.left = node;
		}
		return aRoot;
	}
		
	**/
	
	
	
	
	
	/**
	 * Original attempt at find, not deleting cause still useful for future
	 * 
	private boolean search(Node<E> findroot, E key)
	{
		boolean in = false;
		Node<E> rootnode = root;
		
		while(findroot.data.compareTo(rootnode.data) != 0)
		{
			if(findroot.data.compareTo(rootnode.data) < 0)
			{
				rootnode = rootnode.left;
			}
			else
			{
				rootnode = rootnode.right;
			}
			
			if (rootnode == null)
			{
				in = false;
			}
			else
			{
				in = true;
			}
		}
		
		return in;
	}
	
	**/
	
	
	
	/**
	 * Intilizes and returns searchSuccess
	 * Able to handle null
	 * Parent for find(Node<E> temproot, E key)
	 * 
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public boolean find(E key)
	{
		if(key == null)
		{
			throw new NullPointerException("The Key is null");
			
		}
		
		if(findHelp(root,key) == null)
		{
			searchSuccess = false;
		}
		else {
			searchSuccess = true;
		}
		
		return searchSuccess;
	}
	
	
	/**
	 * Co-parent for find, this function handles a given root and key
	 * 
	 * 
	 * @param temproot
	 * @param key
	 * @return
	 */
	public boolean find(Node<E> temproot, E key)
	{
		if(key == null)
		{
			throw new NullPointerException("The Key is null");
			
		}
		
		
		if(findHelp(temproot,key) == null)
		{
			searchSuccess = false;
		}
		else {
			searchSuccess = true;
		}
		
		
		return searchSuccess;
	}
	
	
	/**
	 * Helper for the two find methods, iterates over the BST to determine if a given
	 * key is in the tree
	 * 
	 * @param node
	 * @param key
	 * @return current
	 */
	private E findHelp(Node<E> node, E key)
	{
		
		E current = null;
		
		if(node.data == key)
		{
			return key;
		}
		
		else if(node.left != null)
		{
			current = findHelp(node.left,key);
		}
		
		else if(node.right != null)
		{
			if(current == null)
			{
				current = findHelp(node.right, key);
			}
		}
		
		return current;
	}
	
	
	/**
	 * Method overrides the default toString to print out the BST in a readable manner
	 * 
	 * Calls iterator to run through the tree
	 */
		public String toString()
		{
			StringBuilder retstate;
			
			retstate = new StringBuilder();
			
			iterator(root, 1, retstate);
			
			return retstate.toString();
		}
		
		
		/**
		 * Helper for toString
		 * Handles the iteraion
		 * 
		 * @param node
		 * @param treesize
		 * @param tempret
		 */
		private void iterator(Node<E> node, int treesize, StringBuilder tempret) 
		
		{
			for (int i = 1; i < treesize; i++)
			
			{
				tempret.append("  ");
			}
			
			
			if (node == null) 
			
			{
				tempret.append("null\n");
			} 
			
			
			else 
			{
				tempret.append("(key=");
				
				tempret.append(node.data);
				
				tempret.append(", ");
				
				tempret.append("priority=");
				tempret.append(node.priority);
				tempret.append(")");
				
				tempret.append("\n");
				iterator(node.left, treesize + 1, tempret);
				
				
				iterator(node.right, treesize + 1, tempret);
				
			}
		}
		
		
	}
		
		




