//Zach Piekut - CS 1501
//Project 1
//June 5, 2013

import java.util.*;

public class DLB implements DictInterface
{

	boolean prefix = false;
	boolean word = false;

	private dlbNode rootNode;

	public boolean add(String s)
	{
		dlbNode currNode = rootNode;
		boolean done = false;

		//loop through each letter in the string
		for(int i = 0; i < s.length(); i++)
		{			
			if(rootNode == null) 
			{
				//on the very first call, this will add the first char to the root node
				//a child node is made and moved to so that the currnode is on the next 
				//level already in the next loop
				rootNode = new dlbNode(s.charAt(i));
				rootNode.child = new dlbNode();
				currNode = rootNode.child;
			}
			else 
			{
				//for all successive calls, the current node is checked
				//the currnode always moves to the child of a node after an add
				//the while loop continues until a node with the character is found, or added
				done = false; 
				while(!done)
				{	
					if (currNode.data == '/') 
					{
						//First check if the current node is empty, and add if it is
						currNode.data = s.charAt(i);
						currNode.child = new dlbNode();
						currNode = currNode.child;
						done = true;
					}
					else if(currNode.data == s.charAt(i)) 
					{
						//if the character has already been added, then simply move to its child, or create one if needed
						if(currNode.child == null)
						{
							currNode.child = new dlbNode();
						}
						currNode = currNode.child;
						done = true;
					}
					else if(currNode.sibling != null) 
					{
						//if the sibling exists, move there so it can be checked
						currNode = currNode.sibling;
					}
					else if(currNode.sibling == null) 
					{
						//if end of siblings is reached without finding the char, add it as another sibling
						currNode.sibling = new dlbNode(s.charAt(i));
						currNode = currNode.sibling;
						currNode.child = new dlbNode();
						currNode = currNode.child;
						done = true;
					}
				}

			}
		}

		//this loop checks for and adds the sentinal character to distinguish the end of a word
		done = false; 
		while(!done)
		{
			if(currNode.data == '$')
			{
				done = true;
			}
			else if(currNode.data == '/')
			{
				currNode.data = '$';
				done = true;
			}
			else if(currNode.sibling != null)
			{
				currNode = currNode.sibling;
			}
			else if(currNode.sibling == null)
			{
				currNode.sibling = new dlbNode('$');
			}
		}
		return true;
	}
	
	public int searchPrefix(StringBuilder s)
	{
		dlbNode currNode = rootNode;
		prefix = false;
		word = false;

		//this loop iterates through the dlb, until it reaches the end of the string or a null
		for(int i = 0; i < s.length(); i++)
		{			
			while(currNode.data != s.charAt(i))
			{
				currNode = currNode.sibling;
				if(currNode == null)
				{	
					return 0;
				}
			}
			currNode = currNode.child;
		}

		//if the end was reached, check if there is a sentinal
		//if there is, its a word. If there is anything else, its a prefix
		if(currNode.data == '$')
		{
			word = true;
		}
		if (currNode.data != '$' || currNode.sibling != null)
		{
			prefix = true;
			currNode = currNode.child;
		}
		
		//return the apropriate number
		//1 - prefix, 2 - word, 3 - both
		if (prefix && word) return 3;
		else if (word) return 2;
		else if (prefix) return 1;
		else return 0;	
	}


	public int searchPrefix(StringBuilder s, int start, int end)
	{	
		dlbNode currNode = rootNode;
		prefix = false;
		word = false;

		for(int i = s.charAt(start); i <= s.charAt(end); i++)
		{			
			while(currNode.data != s.charAt(i))
			{
				currNode = currNode.sibling;
				if(currNode == null)
				{	
					return 0;
				}
			}
			currNode = currNode.child;
		}

		if(currNode.data == '$')
		{
			word = true;
		}
		if (currNode.data != '$' || currNode.sibling != null)
		{
			prefix = true;
			currNode = currNode.child;
		}
		
		if (prefix && word) return 3;
		else if (word) return 2;
		else if (prefix) return 1;
		else return 0;	
	}

	private class dlbNode
	{
		//node class for dlb. includes data, child, and sibling variables,
		// as well as two constructors: one for empty nodes, and one for ones with a character
		// the '/' character here denotes an empty node

		private char data;
		private dlbNode child;
		private dlbNode sibling;

		public dlbNode()
		{
			data = '/';
			child = null;
			sibling = null;
		}

		public dlbNode(char c) 
		{
			data = c;
			child = null;
			sibling = null;
		}

	}
} 