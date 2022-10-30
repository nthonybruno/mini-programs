/**
 * THIS CODE USES BIG-ENDIAN FORMAT
 * a class BinaryNumber that represents binary numbers and a few simple operations on them
 */
public class BinaryNumber {
	//Instance Fields
	private String bin = "";
	
	private int data[];
	private boolean overflow = false;
	

	public BinaryNumber(int length) {
		// Creates a template for a binary number of length length
		for(int i=0;i<length;i++)
		{
			this.bin += "0";
		}
		
	}
	
	
	public BinaryNumber(String str) {
		//Creates an instance of the binary string supplied
		this.bin = str;
	}
	
	public int getLength() {
		//returns the length of a string passed in
		return this.bin.length();
	}
	
	
	public int getDigit(int index) {
		// Returns the digit at a given index
		if(index < this.bin.length() && index > 0) {
			return java.lang.Character.getNumericValue(bin.charAt(index));
		}
		else {
			System.out.println("Error, index out of range");
			return -1; // Returns if out of range
		}
		
		
	}
	
	
	public int toDecimal(){
		//Converts a binary to decimal
		int decimal = 0;
		
		for(int i = this.bin.length()-1; i >= 0; i--) {
			int k = java.lang.Character.getNumericValue(bin.charAt(i));
			
			decimal += k*Math.pow(2,this.bin.length()-1-i);
			
		}
		
		return decimal;
		
	}
	
	
	public 	void shiftR(int amount) {
		//Shifts the binary by an amount, amount, to the left since done in Big Endian
		for(int i=0; i < amount; i++)
		{
			this.bin += "0";
		}
		
	}
	
	
	public void add(BinaryNumber aBinaryNumber) {
		//Adds two binary numbers of the same length, if they are not, it prints an error.
		if(this.bin.length() != aBinaryNumber.bin.length()) {
			System.out.println("Error, the two Binary Numbers are not the same length.");
		}
		else
		{
			String tempbin = "";
			int length1 = this.bin.length();
			int carryover = 0;
			
			for(int i = length1-1; i>=0; i--)
			{
				int primary = java.lang.Character.getNumericValue(bin.charAt(i));
				int secondary = java.lang.Character.getNumericValue(aBinaryNumber.bin.charAt(i));
				
				
				if(primary+secondary+carryover == 3) {
					
					carryover = 1;
					
					tempbin = "1" + tempbin;
					
				}
				
				else if(primary+secondary+carryover == 2){
					tempbin = "0" + tempbin;
					carryover = 1;
				}
				
				
				else if(primary+secondary+carryover == 1) {
					tempbin = "1" + tempbin;
					carryover = 0;
				}
				else {
					tempbin = "0" + tempbin;
					
					carryover = 0;
				}
			}
			
			
			
			if (carryover == 1) {
				overflow = true;
				tempbin = "1" + tempbin;
			} 
			this.bin = tempbin;
		}
		
		
	}
	
	
	public void clearOverflow() {
		//Toggles the overflow to false
		this.overflow = false;
	}
	
	public String toString() {
		//Returns the string of the binary number.
		if(overflow == true)
		{
			return "Overflow";
		}
		else
		{
			return this.bin;
		}
		
	}
	
	
	public static void main(String Args[])
	// Main method
	{
		
		
	}
	
	// The End
}
