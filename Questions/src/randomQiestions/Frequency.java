package randomQiestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class Frequency {

	public static void main(String[] str) {

		int num[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 06, 5 };

		HashMap<Character, Integer> frequency = new HashMap<>();

		Optional<Integer> findFirst = Arrays.stream(num).boxed().sorted(Comparator.reverseOrder()).distinct().skip(1)
				.findFirst();

		System.out.println(findFirst.get());

		String name = "Ganesh Sakhare";

		name.chars().mapToObj(ch -> (char) ch).filter(ch-> !Character.isWhitespace(ch)).forEach(ch -> frequency.put(ch, frequency.getOrDefault(ch, 0) + 1));
		
		
		for(Map.Entry<Character, Integer> entry : frequency.entrySet())
		{
			System.out.println(entry.getKey() +"::"+entry.getValue());
		}					
	}
}