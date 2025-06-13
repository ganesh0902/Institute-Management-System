package javaPrograms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindDuplicate {

	public static void main(String[] str)
	{
		int[] arr = {1, 2, 3, 4, 2, 3, 5};
					
		 List<Integer> collect = Arrays.stream(arr)
		.boxed()
		.collect(Collectors.groupingBy(e->e, Collectors.counting()))
		.entrySet()
		.stream()
		.filter(e->e.getValue() > 1)
		.map(e->e.getKey())
		.collect(Collectors.toList());
		
		System.out.println(collect);
		
		
	}
}
