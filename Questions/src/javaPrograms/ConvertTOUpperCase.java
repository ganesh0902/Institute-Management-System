package javaPrograms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertTOUpperCase {

	public static void main(String[] args) {
		
        List<String> names = Arrays.asList("john", "smith", "alice");

		List<String> collect = names.stream().map(String::toUpperCase).collect(Collectors.toList());

		
		
		System.out.println(collect);
	}
}
