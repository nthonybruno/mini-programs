package Maze;

// Author: Anthony Bruno

public class PairInt{
	
	private int x;
	private int y;
	
	public PairInt(int x, int y)
	{
		this.x = x;
		this.y = y;
		
	}
	
	
	//Getters and Setters
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
			
	public void setY(int y) {
		this.y = y;
	}
	
	
	//Overwritten Methods
	
	public boolean equals(Object p)
	{
		PairInt Pnew = (PairInt) p;
		
		if(this.x == Pnew.x && this.y == Pnew.y)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public String toString()
	{
		String xval = String.valueOf(x);
		String yval = String.valueOf(y);
		String a = "("	+ xval + "," + yval + ")";
		return a;
		
	}
	
	public PairInt copy() {
		
		PairInt NewCopy = new PairInt(x,y);
		
		return NewCopy;
		
	}

}
