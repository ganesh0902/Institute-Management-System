package javaPrograms;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class non_repeated_element {

	public static void main(String[] arr)
	{
		
		int[] ar = {1, 2, 3, 4, 2, 3, 5};
				
		Optional<Integer> findFirst = Arrays.stream(ar)
		.boxed()
		.collect(Collectors.groupingBy(e-> e,LinkedHashMap::new, Collectors.counting()))
		.entrySet()
		.stream()
		.filter(e->e.getValue() ==1)
		.map(e-> e.getKey())
		.findFirst();
		
		System.out.println(findFirst.get());
		
	}
}
