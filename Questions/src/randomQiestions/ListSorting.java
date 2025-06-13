package randomQiestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

class Sorting {
	public static void upperCase() {
		List<String> list = Arrays.asList("Ganesh", "Shubham", "Ajay", "Akash");

		List<String> lists = list.stream().map(std -> std.toUpperCase()).collect(Collectors.toList());

		System.out.println(lists);
	}

	public static void Length() {
		List<String> list = Arrays.asList("Ganesh", "Shubham", "Ajay", "Akash");

		int count = Math.toIntExact(list.stream().filter(std -> std.length() > 5).count());

		System.out.println(count);

	}

	public static void removeString() {
		List<String> list = Arrays.asList("Ganesh", "Shubham", "Ajay", "Akash");

		List<String> collect = list.stream().filter(str -> !str.startsWith("G")).collect(Collectors.toList());
		System.out.println(collect);
	}

	public static void lengthOfEachString() {
		List<String> names = Arrays.asList("Ganesh", "Tushar", "Ajay", "Pawan");

		Map<String, Integer> collect = names.stream().collect(Collectors.toMap(str -> str, str -> str.length()));

		for (Map.Entry<String, Integer> entry : collect.entrySet()) {
			System.out.println(entry.getValue() + " " + entry.getKey());
		}
	}

	public static void StringLength() {
		List<String> names = Arrays.asList("Ganesh", "Tushar", "Ajay", "Pawan");
		List<String> collect = names.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
		collect.forEach(str -> System.out.println(str));
	}

	public static void secondHighestSalary() {
		List<Integer> nums = Arrays.asList(10, 20, 300, 43, 22, 1, 55, 22, 332, 10);

		Optional<Integer> findFirst = nums.stream().sorted(Comparator.reverseOrder()).distinct().skip(1).findFirst();

		System.out.println(findFirst.get());
	}

	public static void secondLargest() {
		// Find Second Largest Number in an Array

		int nums[] = { 29, 32, 45, 23, 12, 78, 98, 23, 45, 32, 45, 43 };

		 Optional<Integer> num = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder())
				.distinct().skip(1).findFirst();
		System.out.println(num.get());
	}

	public static Character FindFirstNonRepeated() {
		// Find the First Non-Repeated Character in a String

		String name = "swiss";
		
		HashMap<Character, Integer> nums = new HashMap<>();

		name.chars()
		.mapToObj(ch->(char) ch)
		.forEach(ch1->nums.put(ch1,nums.getOrDefault(ch1, 0)+1));				
				
		for(Map.Entry<Character, Integer> entry : nums.entrySet())
		{
			if(entry.getValue()==1)
			{				
				return entry.getKey();
			}
		}
		return null;
	}
}

public class ListSorting {

	public static void main(String[] str) {

//		Sorting2.upperCase();
//		Sorting2.Length();
//		Sorting2.removeString();
//		Sorting2.lengthOfEachString();
//		Sorting2.StringLength();
//		Sorting2.secondHighestSalary();
//		Sorting2.secondLargest();
//		
//		System.out.println(Sorting2.FindFirstNonRepeated());

	}
}