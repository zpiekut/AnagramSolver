import java.util.*;
import java.io.*;

public class test
{

	public static void main(String [] args)
	{
		ArrayList<String> List = new ArrayList<String>();

		String s = "This is a string";
		List.add(s);

		System.out.println(s + " before being deleted");
		for(String item : List)
    	{
    		System.out.println(item + " from an ArrayList");
    	}
		
		s = "";

		System.out.println(s + " after being deleted");
		for(String item : List)
    	{
    		System.out.println(item + " from an ArrayList");
    	}

	}

}