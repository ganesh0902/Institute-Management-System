package randomQiestions;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Example2 {

	public static void main(String[] args) {
		
		int num[] = {1,2,5,6,7,8,99,33,21};
		
		 Optional<Integer> findFirst = Arrays.stream(num).boxed().sorted(Comparator.reverseOrder()).distinct().skip(1).findFirst();
		
		System.out.println(findFirst.get());
	}
}