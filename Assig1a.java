//Zach Piekut - CS 1501
//Project 1
//June 5, 2013

import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Assig1a
{
	DictInterface MyDic; //Create new MyDictionary object
	ArrayList<StringBuilder> puzzleList = new ArrayList<StringBuilder>();	//Create list to hold anagram puzzles read from file
	ArrayList<ArrayList<String>> solutionList = new ArrayList<ArrayList<String>>();	//Create a list to hold the puzzle solutions
    int puzzleNum = 0;

	public Assig1a(String[] args) throws IOException
 	{
		
		
		Scanner dScan = new Scanner(new File(args[0]));	//Create scanner to scan in dictionary file
		Scanner tScan = new Scanner(new File(args[1])); //Create scanner to scan in the letter groups test file

		//check which data structure to use
    	if(args[2].equals("orig"))
    	{
    		MyDic = new MyDictionary();
    	}
    	else if(args[2].equals("dlb"))
    	{
    		MyDic = new DLB();
    	}

		//Scan in dictionary file and create list in MyDic object
		while(dScan.hasNextLine()) 
    	{
    		String tempLine = dScan.nextLine();
    		MyDic.add(tempLine);
    	}
		
		//Scan in a list of anagram puzzles, add them to the arraylist
    	while(tScan.hasNextLine())
    	{
    		String tempLine = tScan.nextLine();	
    		StringBuilder puzzleSB = new StringBuilder(tempLine);
    		puzzleList.add(puzzleSB);
    	}

    	//pass each puzzle to the solver and slove
    	for(StringBuilder puzzle : puzzleList)
    	{
    		StringBuilder emptysb = new StringBuilder(); 
    		char[] letters = new char[puzzle.length()];
    		for (int i = 0; i < puzzle.length(); i++)
    		{
    			letters[i] = puzzle.charAt(i);
    		}
    		//create an arraylist to add solutions to
    		ArrayList<String> tempAL = new ArrayList<String>();
    		solutionList.add(tempAL);
    		findAnagrams(emptysb, letters); //FIND SOLUTIONS
    		puzzleNum++;
    	}


    	//WRITE RESULTS TO A FILE
    	FileWriter f = new FileWriter("output.txt",false);
		PrintWriter printer = new PrintWriter(f);

		int puzzleNum = 0;
    	for(ArrayList al : solutionList)
    	{
    		//add the puzzle solutions to a hashSet, which will eliminate duplicates
    		HashSet hs = new HashSet();
			hs.addAll(al);
			al.clear();
			//add them back into the list, then sort
			al.addAll(hs);
    		Collections.sort(al);

    		//write solutions to file
		    printer.println("Here are the results for " + puzzleList.get(puzzleNum) + ":");
		    printer.println("1 word solutions:");
		    for(int j = 0; j < al.size(); j++)
    		{
    			 printer.println(al.get(j));
    		}
    		printer.println("\n");
		    puzzleNum++;
    	} 

    	printer.flush();
		printer.close();
		
 	}


 	public void findAnagrams(StringBuilder sb, char[] letters)
	{
		//iterate through each letter in the puzzle
		for(int j = 0; j < letters.length; j++)
		{
			//this if makes sure placeholder characters arent added and checked
			if(letters[j] != '*')
			{
				//append a letter to the puzzle and remove from array
				sb.append(letters[j]);
				letters[j] = '*';

				//search dictionary for string
				int spReturn = MyDic.searchPrefix(sb);

				//if it is a prefix, make a recursive call 
				//(which will add letters rather than replacing the current one)
				if(spReturn == 1 || spReturn == 3)
		    	{
		    		findAnagrams(sb, letters);
		    	}

		    	//if it is a word, check if it is a full solution
		    	if(spReturn == 2 || spReturn == 3)
		    	{
		    		//Check how many letters have been used
		    		int c = 0;
		    		for (int k = 0; k < letters.length; k++)
		    		{
		    			if(letters[k] == '*')
		    			{c++;}
		    		}

		    		//if all, solution found. if not, multi word check
		    		if(c == letters.length)
	    			{
	    				//SOLUTION FOUND - add to list
	    				ArrayList<String> tempAL = solutionList.get(puzzleNum);
	    				tempAL.add(sb.toString());
	    			}
	    			else
	    			{
	    				//Multi word solution code goes here
	    			}
		    	}

		    	//return character to array and remove from stringbuilder
		    	letters[j] = sb.charAt(sb.length()-1);
		    	sb.deleteCharAt(sb.length()-1);
			}
		}
		return;	
	}

		
	//The main program simply calls the assig1a constructor
	public static void main(String [] args) throws IOException
	{
		Assig1a stuff = new Assig1a(args);
	}

}