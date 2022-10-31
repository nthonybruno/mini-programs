package hw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Anthony Bruno
 * @purpose To test the functionality of a TREAP
 * 
 */

public class treaptester<E extends Comparable<E>>{
		
		Treap<Integer> Test1 = new Treap<Integer>(520);
		Treap<Integer> Test2 = new Treap<Integer>(50);
		Treap<Integer> Test3 = new Treap<Integer>();		
		
		@Test
		public void testTest1() {
			Test1.add(4);
			assertTrue(Test1.add(5));
			Test1.add(6);
			Test1.add(23);
			Test1.add(34);
			Test1.add(25);
			assertFalse(Test1.add(25));
			System.out.println(Test1.toString());
		}
		
		@Test
		public void testTest2() {
			Test2.add(435);
			assertTrue(Test2.add(5));
			Test2.add(6352);
			Test2.add(23433);
			assertTrue(Test2.add(42));
			Test2.add(343252);
			Test2.add(25124);
			assertTrue(Test2.add(212415));
			assertFalse(Test2.add(42));
			assertFalse(Test2.add(42));
			System.out.println(Test2.toString());
		}
		
		@Test
		public void testTest3() {
			Test3.add(4,19);
			Test3.add(2,31);
			Test3.add(6,70);
			assertFalse(Test3.add(6,70));
			Test3.add(1,84);
			assertTrue(Test3.add(3,12));
			Test3.add(5,83);
			assertFalse(Test3.add(5,83));
			Test3.add(7,26);
			System.out.println(Test3.toString());
		}
		
		

	}
