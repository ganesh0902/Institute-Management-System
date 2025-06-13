package javaPrograms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class descending_order {

	public static void main(String[] arg)
	{
		
		 int[] arr = {10, 5, 3, 12};
		 		 
		 List<Integer> collect = Arrays.stream(arr)
		 .boxed()
		 .sorted(Comparator.reverseOrder())
		 .collect(Collectors.toList());
		 
		 System.out.println(collect);
	}
}
