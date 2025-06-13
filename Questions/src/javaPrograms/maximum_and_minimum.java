package javaPrograms;

import java.util.Arrays;
import java.util.List;

public class maximum_and_minimum {

	public static void main(String[] srr)
	{
		
        List<Integer> numbers = Arrays.asList(4, 7, 1, 9, 2);
		
        
        
        Integer orElseThrow = numbers.stream().max(Integer::compareTo).orElseThrow();
        
        Integer orElseThrow2 = numbers.stream().min(Integer::compareTo).orElseThrow();
        
        System.out.println(orElseThrow);
        System.out.println(orElseThrow2);
	}
}
