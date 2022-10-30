package Maze;

import java.util.ArrayList;
import java.util.Stack;
import java.util.*;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 * Code modified & solved by Anthony Bruno
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
    	// COMPLETE HERE FOR PROBLEM 1
    	if(x < 0 ||  x > maze.getNCols()-1)
    	{
    		return false;
    	}
    	if(y < 0 || y > maze.getNRows()-1)
    	{
    		return false;
    	}
    	if(maze.getColor(x,y) != NON_BACKGROUND)
    	{
    		return false;
    	}
    	if(x == maze.getNCols()-1 && y == maze.getNRows()-1)
    	{
    		maze.recolor(x,y,PATH);
    		return true;
    	}
    	else {
    	
    		maze.recolor(x,y,PATH);
    		//
    		if( findMazePath(x+1,y))
    		{
    			return true;
    		}
    		if(findMazePath(x,y+1))
    		{
    			return true;
    		}
    		if(findMazePath(x-1,y))
    		{
    			return true;
    		}
    		
    		if(findMazePath(x,y-1))
    		{ 
    			return true;
    		}
    		else
    		{
    			maze.recolor(x,y,TEMPORARY);
    			return false;
    		}
    		
    	}
    }

    public void findMazePathStackBased(int x,int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace)
    {
    	
    	if(x >= 0 && x <= maze.getNCols()-1 && y >= 0 && y <= maze.getNRows()-1 && maze.getColor(x,y).equals(NON_BACKGROUND))
    	{
        	
    		
    		if(x== maze.getNCols()-1 && y == maze.getNRows()-1)
    		{
    			ArrayList<PairInt> temp = new ArrayList<PairInt>();
        		trace.push(new PairInt(x,y));
    			temp.addAll(trace);
    			result.add(temp);
    			
    			trace.pop();
    			return;

    		}

    		else

    		{
    			trace.push(new PairInt(x,y));
    			maze.recolor(x,y,PATH);
    			
    			findMazePathStackBased(x+1,y,result,trace);
    			
    			findMazePathStackBased(x-1,y,result,trace);
    		
    			findMazePathStackBased(x,y+1,result,trace);

    			findMazePathStackBased(x,y-1,result,trace);
    			
    			maze.recolor(x,y,NON_BACKGROUND);
    			trace.pop();
    			
    		}
    }
    	else
    	{
    		return;
    	}
    
    }
    	
    	
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x,int y)
    {
    	ArrayList <ArrayList <PairInt >> result = new ArrayList<>();
    	Stack <PairInt > trace = new Stack<>();
    	findMazePathStackBased (0,0,result , trace );
    	
    	if(result.size()==0)
    	{
    		result.add(new ArrayList<PairInt>());
    	}
    	return result;
    }
    
    
    
    public ArrayList<PairInt> findMazePathMin(int x,int y)
    {
    	
    	maze.recolor(PATH, BACKGROUND);
    	
    	ArrayList <ArrayList <PairInt >> minresult = findAllMazePaths(x,y);
    	
    	if(minresult.size()== 0)
    	{
    		return new ArrayList <PairInt>();
    	}
    	
    	
    	ArrayList <PairInt > shortest = new ArrayList <PairInt >();
    	
    	shortest = minresult.get(0);
    
    	for(int i = 1; i < minresult.size()-1;i++)
    	{
    		if (minresult.get(i+1).size() < shortest.size())
    		{
    			shortest = minresult.get(i+1);
    		}
    	}
    	
    	return shortest;
    	
    }
    
    

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
