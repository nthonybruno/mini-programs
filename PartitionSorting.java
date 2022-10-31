package classes;

import java.util.Arrays;

public class Sort {

	private static int[] merge(int[] a, int[] b) {
		   int[] c = new int[a.length+b.length];
		   int ia=0;
		   int ib=0;
		   int ic=0;
		   
		   while (ia<a.length && ib<b.length) {
			   if (a[ia]<b[ib]) {
				   c[ic] = a[ia];
				   ia++;
			   } else {
				   c[ic] = b[ib];
				   ib++;
			   }
			   ic++;
		   }
		   
		   while (ia<a.length) {
			   c[ic]=a[ia];
			   ia++;
			   ic++;
		   }
		   
		   while (ib<b.length) {
			   c[ic]=b[ib];
			   ib++;
			   ic++;
		   }
		   
		   
		   
		   return c;
	}
	
	public static <E extends Comparable<E>> void qs(E[] a) {
		qs(a,0,a.length-1);
	}
	
	private static <E extends Comparable<E>> void swap(E[] a, int i, int j) {
		E temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	private static <E extends Comparable<E>> int partition(E[] a, int first, int last) {
		int up=first;
		int down=last;
		int pivot=first;
		
		do {
			 while (a[up].compareTo(a[pivot])<=0 && up<down) { up++; }
			 while (a[down].compareTo(a[pivot])>0) { down--; }
			 if (up<down) { swap(a,up,down); }
		} while(up < down);
				
		swap(a,pivot,down);
		return down;
	}
	
	private static <E extends Comparable<E>> int partition2(E[] a, int first, int last) {
		if (last-first>2) {
			int left=first;
			int right=last;
			int middle=(last-first)/2;
			/* complete here*/
					
			swap(a,left,middle);
		}
		
		return partition(a,first,last);
	}
	
	/* no repetitions in a 
	 Solution to quiz 9 */
	private static <E extends Comparable<E>> int partition3(E[] a, int first, int last) {
		int pivot = first;
		int oppEnd = last;
		int incr = -1;
		
		while (pivot!=oppEnd) {
			if (incr==-1 ? (a[oppEnd].compareTo(a[pivot])>0):(a[oppEnd].compareTo(a[pivot])<0)) {
				oppEnd = oppEnd + incr;
			} else {
				swap(a,pivot,oppEnd);
				int temp = pivot;
				pivot = oppEnd;
				oppEnd = temp;
				incr = -incr;
			}
		}
		
		return pivot;
	}
	
	private static <E extends Comparable<E>> void qs(E[] a, int first, int last) {
		if (first<last) { // apply qs if there are two or more elements to sort
			int pivIndex=partition(a,first,last);
			qs(a,first,pivIndex-1);
			qs(a,pivIndex+1,last);
		}
	}
	
	public static void main(String[] args) {
		//Integer[] a = {10, 7, 19, 22, 30, 2, 5};
		Integer[] a = {3,0,1,8,7,2,5,4,9,6};
		//qs(a);
		partition3(a,0,a.length-1);
		System.out.println(Arrays.toString(a));
		
	}
}
