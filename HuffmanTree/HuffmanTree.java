package hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * CS284 Spring 2019
 * Author - Anthony Bruno
 * Implements a Huffman encoding tree.
 * 
 */
public class HuffmanTree {

	// ******************** Start of Stub Code ******************** //
	// ************************************************************ //
	/** Node<E> is an inner class and it is abstract.
	 * There will be two kinds
	 * of Node, one for leaves and one for internal nodes. */
	abstract static class Node implements Comparable<Node>{
		/** The frequency of all the items below this node */
		protected int frequency;

		public Node(int freq) {
			this.frequency = freq;
		}

		/** Needed for the Minimum Heap used later in this stub. */
		public int compareTo(Node other) {
			return this.frequency - other.frequency;
		}
	}



	/** Leaves of a Huffman tree contain the data items */
	protected static class LeafNode extends Node {
		// Data Fields
		/** The data in the node */
		protected char data;
		/** Constructor to create a leaf node (i.e. no children) */
		public LeafNode(char data, int freq) {
			super(freq);
			this.data = data;
		}
		/** toString method */
		public String toString() {
			return "[value= "+this.data + ",freq= "+frequency+"]";
		}
	}
	/** Internal nodes contain no data,
	 * just references to left and right subtrees */
	protected static class InternalNode extends Node {
		/** A reference to the left child */
		protected Node left;
		/** A reference to the right child */
		protected Node right;

		/** Constructor to create an internal node */
		public InternalNode(Node leftC, Node rightC) {
			super(leftC.frequency + rightC.frequency);
			left = leftC; right = rightC;
		}
		public String toString() {
			return "(freq= "+frequency+")";
		}
	}

	// Enough space to encode all "extended ascii" values
	// This size is probably overkill (since many of the values are not "printable" in the usual sense)
	private static final int codex_size = 256;

	/* Data Fields for Huffman Tree */
	private Node root;

	public HuffmanTree(String s) {
		root = buildHuffmanTree(s);
	}

	/**
	 * Returns the frequencies of all characters in s.
	 * @param s
	 * @return
	 */
	public static int[] frequency(String s) {
		int[] freq = new int[codex_size];
		for (char c: s.toCharArray()) {
			freq[c]++;
		}
		return freq;
	}

	/**
	 * Builds the actual Huffman tree for that particular string.
	 * @param s
	 * @return
	 */
	private static Node buildHuffmanTree(String s) {
		int[] freq = frequency(s);

		// Create a minimum heap for creating the Huffman Tree
		// Note to students: You probably won't know what this data structure
		// is yet, and that is okay.
		PriorityQueue<Node> min_heap = new PriorityQueue<Node>();

		// Go through and create all the nodes we need
		// as in, all the nodes that actually appear in our string (have a frequency greater then 0)
		for(int i = 0; i < codex_size; i++) {
			if (freq[i] > 0) {
				// Add a new node (for that character) to the min_heap, notice we have to cast our int i into a char.
				min_heap.add(new LeafNode((char) i, freq[i]));
			}
		}

		// Edge case (string was empty)
		if (min_heap.isEmpty()) {
			throw new NullPointerException("Cannot encode an empty String");
		}

		// Now to create the actual Huffman Tree 
		// NOTE: this algorithm is a bit beyond what we cover in cs284, 
		// you'll see this in depth in cs385

		// Merge smallest subtrees together
		while (min_heap.size() > 1) {
			Node left = min_heap.poll();
			Node right = min_heap.poll();
			Node merged_tree = new InternalNode(left, right);
			min_heap.add(merged_tree);
		}

		// Return our structured Huffman Tree
		return min_heap.poll();
	}

	// ******************** End of Stub Code ******************** //
	// ********************************************************** //

	/**
	 * Returns the string representations of 1's and 0's of an inputted Array of Booleans.
	 * I.E. [true, false] => 10
	 * @param encoding
	 * @return
	 */
	public String bitsToString(Boolean[] encoding) {

		String a = "";

		for(int i = 0; i < encoding.length; i++)
		{
			if(encoding[i] == true)
			{
				a += "1";
			}
			else if(encoding[i] == false)
			{
				a += "0";
			}
		}

		return a;
	}



	/**
	 * Returns the readable format of a Huffman tree
	 * 
	 * Uses a helper to scan through the tree
	 * @param none
	 * @return sb
	 */
	public String toString() { // 
		// TODO Complete toString method (see assignment specification)
		// HINT: Might need helper method for preOrderTraversal

		StringBuilder sb = new StringBuilder();

		RecursiveIterator(root, 1, sb);

		return sb.toString();
	}

	/**
	 * Helper to toString() which goes through the tree and modifies a string builder
	 * to equal its string representation. Of which, toString returns sb.
	 * 
	 * 
	 * @param node
	 * @param height
	 * @param sb
	 */
	private void RecursiveIterator(Node e, int count, StringBuilder sb) {

		if (e instanceof LeafNode) {

			if (count != 1) {

				sb.append("\n");
			}

			//Appends spacing for readability
			for(int i = 0; i < count - 1; i++) {

				sb.append("   ");

			}

			sb.append(e.toString());
		} 
		
		//Checks if the current node is an internal node, if it is it adds an indentation
		else if ( e instanceof InternalNode) {

			if (count != 1) {

				sb.append("\n");

			}
			//Appends the indentations based on tree depth
			for(int i = 0; i < count - 1; i++) {

				sb.append("   ");
			}
			sb.append(e.toString());


			//Left subtree recursive call
			RecursiveIterator((((InternalNode) e).left), count + 1, sb);

			// Right subtree recursive call
			RecursiveIterator((((InternalNode) e).right), count + 1, sb);
		}
	}

//
	//
	///
	//


	public String decode(Boolean[] coding) { //

		// TODO Complete decode method

		String intrep = bitsToString(coding);

		StringBuilder pile = new StringBuilder();

		Node localroot = root;
		Node temproot = localroot;

		// Loops through the length of the string representation of the boolean coding
		// Searches the tree for the char, if it finds that the coding is invalid
		// it throws an IllegalArgumentException
		for (int k = 0; k < intrep.length(); k++) 
		{

			char tempchar = intrep.charAt(k);

			// Do this if char is 2, corepsonding to a false boolean array coding
			if (tempchar == '0') {

				if(((InternalNode) temproot).left instanceof InternalNode == false)
				{
					if(((InternalNode) temproot).left instanceof LeafNode == false)
					{
						throw new IllegalArgumentException();
					}
				}


				temproot = (((InternalNode) temproot).left);

			}
			// Do this if char is 1, true in the boolean array coding
			else if (tempchar == '1') 
			{

				if(((InternalNode) temproot).right instanceof InternalNode == false)
				{
					// Throws illegal Arg excepton if the right child node is not a leaf or
					// an internal node. 
					// In this case it must be out of bounds/ invalid coding 
					if (((InternalNode) temproot).right instanceof LeafNode == false)
					{
						throw new IllegalArgumentException();
					}

				}
				temproot = (((InternalNode) temproot).right);
			}

			// Redundancy to check for false positive
			if(k == intrep.length() - 1)
				if(temproot instanceof InternalNode)
				{
					{
						throw new IllegalArgumentException();
					}
				}

			// Last but not least, if the char has been ound, append the char to the
			// string builder pile and return it
			if (temproot instanceof LeafNode) {
				pile.append(Character.toString((((LeafNode) temproot).data)));
				temproot = root;
			}
		}

		return pile.toString();
	}

	
	/**
	 * "Takes an input text string (sequence of characters), 
	 * looks up the bit sequence for each one, and returns an 
	 * array that concatenates all of the bit sequences."
	 * 
	 * Also throws an exception if it is an invalid input text to be encoded
	 * @param inputText
	 * @return
	 */
	public Boolean[] encode(String inputText) 
	
	{
		// TODO Complete encode method
		
				//Temporary arrays for the build path
				ArrayList<Boolean> hold1 = new ArrayList<Boolean>();
				
				ArrayList<Boolean> hold2 = new ArrayList<Boolean>();
				
				int a = inputText.length();
						
				Node e = root;
				
				//loops through the array to determine validity
				for (int k = 0; k < a; k++)
					{
					//Stores the size of the array	
					int b = eeHelp(Character.toString(inputText.charAt(k))).size();
					
					if( b == 0) 
					{
						 throw new IllegalArgumentException();
					}
					
					char hold3temp = inputText.charAt(k);
					
					eHelp(e, hold1,Character.toString(hold3temp));
				}
			
				// if its size is 0, it is invalid
				if(hold1.size() == 0) 
				
				{
					 throw new IllegalArgumentException();
				}
				
				/**
				 * for(int k = 0; k < hold1.size(); k++)
				 * {
				 * 
				 * }
				 */
				
				
				int size = hold1.size();
				
				//Compile the boolean coding to be returned
				Boolean[] finalcoding = new Boolean[size];
				
				//Convert to Proper SEQ. Array
				finalcoding = hold1.toArray(finalcoding);
				
				hold1.clear();
				
				hold2.clear();
	
				return finalcoding;
		
	}
	
	
	/**
	 * Calls eHelp with given parameters based on the pased in inputText
	 * @param inputText
	 * @return
	 */
	public ArrayList<Boolean> eeHelp(String inputText) 
	{
		//Array to hold and return the path, used as a helper for all encoding methods
		ArrayList<Boolean> hold6 = new ArrayList<Boolean>();
		
		String t = Character.toString(inputText.charAt(0));
		eHelp(root, hold6,t);
		
		return hold6;
		
	}
	
	
	/**
	 * This helper method for eHelp saves it time and code in checking paths all the way,
	 * this method checks if the node being serached for is below a given leaf or internal node
	 * 
	 * @param e
	 * @param hold
	 * @param mod
	 * @return
	 */
	public boolean encodeChecker(Node e, ArrayList<Boolean> hold, String mod)
	{
		// If the node is null the path is not possible
		if (e == null) {
			return false;
		}
		// Checks first if its a leaf node, because then it compares the data to determine
		// the possibility of equivalence
		if (e instanceof LeafNode) 
		{
			
			if (Character.toString(((LeafNode) e).data).equals(mod)) 
			{
			
				return true;
			}
		}	
		//If its an internal node, recursively search through to determine
		if (e instanceof InternalNode)
		{
			// Left Check
			if (encodeChecker(((InternalNode) e).left, hold, mod))
			{
				return true;
			}
			
			//Right checl
			if(encodeChecker(((InternalNode) e).right, hold, mod)) 
			{
				return true;
			}
		}	
		//If all else fails then its false
		return false;
	}
	
	
	

	/**
	 * Helper method 1 for encode. 
	 * 
	 * Compiles the paths of the Node e's children
	 * 
	 * @param e
	 * @param holdtemp
	 * @param mod
	 */
	public void eHelp(Node e, ArrayList<Boolean> hold, String mod)
	{
		//First check the type of the node
		if (e instanceof LeafNode) {
		//Returns nothing if the data is equal to the character
			if (Character.toString(((LeafNode) e).data).equals(mod))
			{
				return;
			
			}
			//else it modifies the size of holds indices
			else if (hold.size() != 0)
			
			{
			
				hold.remove(hold.size()-1);
			
			}
		}
			//recursive calls to encodeChecker to check if paths are below current node
			// As well as self calls to compute lower nodes if it is below.
		else if (e instanceof InternalNode) 
		{
			//Left check
			if (encodeChecker(((InternalNode) e).left, hold,mod))
			{
				
				hold.add(false);
			
				eHelp(((InternalNode) e).left, hold,mod);
				
			}
			
			//Right check
			else if (encodeChecker(((InternalNode) e).right, hold,mod))
			{
				hold.add(true);
			
				eHelp(((InternalNode) e).right, hold,mod);
			}
		}
	}
	
//	public ArrayList<ArrayList<Boolean>> paths(Node e)
//	{
//		ArrayList<Boolean> a = new ArrayList<Boolean>();
//		ArrayList<ArrayList<Boolean>> b = new ArrayList<ArrayList<Boolean>>();
//
//		if(e == null)
//		{
//			return b;
//		}
//		pathsHelper(e,a,b);
//
//		return b;
//	}
//
//	public void pathsHelper(Node e, ArrayList<Boolean> a, ArrayList<ArrayList<Boolean>> b)
//	{
//
//		System.out.print(a);
//		System.out.println();
//		if(e instanceof LeafNode)
//		{
//			b.add(a);
//
//			if(a.size() != 0)
//			{
//				a.remove(a.size()-1);
//			}
//		}
//
//		if(e instanceof InternalNode)
//		{
//			if( ((InternalNode)e).left != null)
//			{
//				Node temp = ((InternalNode)e).left;
//				a.add(false);
//				pathsHelper(temp,a,b);
//
//
//				if(a.size() != 0)
//				{
//					b.add(a);
//					a.remove(a.size()-1);
//				}
//			}
//
//			if( ((InternalNode)e).right != null)
//			{
//				Node temp = ((InternalNode)e).right;
//				a.add(true);
//
//				pathsHelper(temp,a,b);
//
//				if(a.size() != 0)
//				{
//					b.add(a);
//					a.remove(a.size()-1);
//				}
//
//
//			}
//		}
//	}


	public Boolean[] efficientEncode(String inputText) 
	{
		// TODO Complete efficientEncode method
		// NOTE: Should only go through the tree once.
		
		//Create a "Dictionary" to reimplement code from encode BUT store the values
		//to get rid of repetitive recursive calls
		Map<String, ArrayList<Boolean>> pathBase = new HashMap<String, ArrayList<Boolean>>();
		
		//New array to hold individual paths to be inputted into the dict
		ArrayList<Boolean> hold4 = new ArrayList<Boolean>();
		
		ArrayList<Boolean> hold5 = new ArrayList<Boolean>();
		
		int b = 0;
		
		// Loop through the database to determine if its already in
		for (int k = 0; k < inputText.length(); k++)
		
		{
			
			//Check if the resulting array size is 0, if it is then it is invalid encoding
			if(eeHelp(Character.toString(inputText.charAt(k))).size() == 0)
			{
				 throw new IllegalArgumentException();
			}
			
			//if its not 0, check the dictionary to see if it is already stored/looked up/saved
			if (pathBase.containsKey(Character.toString(inputText.charAt(k)))) 
			{
				 
				hold4.addAll(pathBase.get(Character.toString(inputText.charAt(k))));
				
			}
			
			else
			{
				String tempCheck2 = Character.toString(inputText.charAt(k));
				eHelp(root, hold4, tempCheck2);
				
				//Else, it must not be in the dict, so find it and add it
				pathBase.put(tempCheck2, eeHelp(tempCheck2));
			}
		}
		
		 if(hold4.size() == 0) 
		 {
			 throw new IllegalArgumentException();
		 }
		 
		//Compile the boolean coding to be returned
		
		Boolean[] final2 = new Boolean[hold4.size()];
		//Convert to Proper SEQ. Array
		final2 = hold4.toArray(final2);
		//Clear temp arrays 
		hold4.clear();
		hold5.clear();
		
		return final2;
	}
	
	
	////////////////////////////////////////:)///////////////////////////////
	

	public static void main(String[] args) {
		// Code to see if stuff works...
		//String s = "Some string you want to encode";
		String b = "supercalifragilisticexpialidocious";
		HuffmanTree t = new HuffmanTree(b); // Creates specific Huffman Tree for "s"
		// Now you can use encode, decode, and toString to interact with your specific Huffman Tree
		
		
		
//		System.out.println(t.toString());
//		//Boolean[] a = {true,false,false,true,true};
//	//System.out.println(t.decode(a));
//		//System.out.println(Arrays.toString(t.efficientEncode("Hbkn")));
////		System.out.println(t.decode(t.efficientEncode("aaaaaaaaaxxeeee")));
////
////		System.out.println("Encode Method Return:" + Arrays.toString(t.encode("aaaaaaaaaxxeeee")));
////		System.out.println(t.decode(t.encode("aaaaaaaaaxxeeee")));
//		
//		
//		System.out.println(Arrays.toString(t.efficientEncode("super")));
//		
//		Boolean[] a = {true, true, true, true, true, true, false, true, true, true, false, true, false, true, false, false, true, true, false, true, true};
//		System.out.println(t.bitsToString(a));
//		
	}


	/**
	 * To form a code, traverse the tree from the root to the chosen character, 
	 * appending 0 if you turn left, and 1 if you turn right.
	 * 
	 * 
	 * 
	 */
}
