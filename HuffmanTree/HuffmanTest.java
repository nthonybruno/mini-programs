package hw4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class HuffmanTest {

	@Test
	void test() {
		//This test checks the functionality of decode, encode and efficientEncode
		String b = "mississipi";
		HuffmanTree t = new HuffmanTree(b);
	
		//These do equal, decode, encode and efficient work
		assertEquals(t.decode(t.encode(b)), b);
		assertEquals(t.decode(t.efficientEncode(b)), b);
	}
	
	@Test
	void test2() {
		//This method checks decoding a created Boolean Array
		
		//Generates a new Huffman Tree
		String b = "Hoboken";
		HuffmanTree t = new HuffmanTree(b);
		
		//Boolean array of a path
		Boolean[] a = {true,false,false,true,true};
		assertEquals(t.decode(a), "ok"); 
		
		String k = "[true, true, false, true, true, true, false, true, true, false, true, false]";
		assertEquals(Arrays.toString(t.efficientEncode("Hbkn")),k);
		
		//Checking that efficient and encode throw
		assertThrows(IllegalArgumentException.class,() -> t.efficientEncode("hbkn")); // does throw when given invalid coding
		assertThrows(IllegalArgumentException.class,() -> t.encode("hbkn")); //does throw when given invalid coding
	
	}
	
	@Test
	void test3() {
		//This method tests the toString, bitsToString and checking that decode throws an Illegal Arg Exception
		String b = "supercalifragilisticexpialidocious";
		HuffmanTree t = new HuffmanTree(b);
		System.out.println(t.toString());
		
		//A correct path representing string super and a wrong made up path to check equals and throws
		Boolean[] m = {true, true, true, true, true, true, false, true, true, true, false, true, false, true, false, false, true, true, false, true, true};
		Boolean [] wrong = {true, false, false, true, false};
		
		
		assertEquals( t.bitsToString(m),"111111011101010011011"); //yes
		
		assertThrows(IllegalArgumentException.class,() -> t.decode(wrong)); // does throw when given wrong path
	}

}
