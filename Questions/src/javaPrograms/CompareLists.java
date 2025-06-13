package javaPrograms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompareLists {

	public static void main(String[] args) {
		 List<String> l1 = Arrays.asList("a", "b", "c");
	     List<String> l2 = Arrays.asList("c", "a", "b");
	        	        	    
	     boolean equals = l1.stream().sorted().collect(Collectors.toList())
	     .equals(l2.stream().sorted().collect(Collectors.toList()));
	     
	     System.out.println(equals);
	}
}
