package javaPrograms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplication {

	public static void main(String[] arg)
	{
		
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 1, 4);
        
        List<Integer> collect = list.stream()
        .distinct()
        .collect(Collectors.toList());
        
        
        System.out.println(collect);
	}
}
