package classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IDLListTest {
	//Class for test cases for IDLList

	@Test
	void test() {
		//Tests populating a new IDLList using the add method
		IDLList<Integer> testcases = new IDLList<Integer>();
		testcases.add(1);
		testcases.add(1);
		testcases.add(0);
		testcases.add(1);
		
		assertEquals("1;0;1;1",testcases.toString());
		
		
		//Tests adding an element to the list at a given index
		
		testcases.add(2,1);
		assertEquals("1;0;1;1;1",testcases.toString());
		
		
		//Tests Appending an element to a list
		
		testcases.append(5);
		assertEquals("1;0;1;1;1;5",testcases.toString());
		
		
		//Tests Appending to an empty IDLList
		IDLList<Integer> testcases2 = new IDLList<Integer>();
		testcases2.append(5);
		assertEquals("5",testcases2.toString());
		
		
		//Tests the get method by getting an element at an index
		
		assertEquals(Integer.valueOf(1),testcases.get(3));
		
		//Tests the getHead method by checking the head element of the list
		
		assertEquals(Integer.valueOf(1),testcases.getHead());
		
		//Tests the getLast method
		
		assertEquals(Integer.valueOf(5),testcases.getLast());
		
		//Tests the size of the list using the size method
		
		assertEquals(6,testcases.size());
		
		//Tests the remove method
		
		assertEquals(Integer.valueOf(1),testcases.remove());
		
		//Tests the removeLast method
		
		assertEquals(Integer.valueOf(5),testcases.removeLast());
		
		//Tests the removeAt function
		
		assertEquals(Integer.valueOf(1),testcases.removeAt(1));
		
		
	}

}
