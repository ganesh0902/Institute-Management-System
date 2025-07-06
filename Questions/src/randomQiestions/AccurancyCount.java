package randomQiestions;
import java.util.HashMap;
import java.util.Map;

public class AccurancyCount {
	
	public static void main(String[] args) {	
		String name = "Ganesh Sakhare";
		
		HashMap<Character,Integer> count = new HashMap<>();
		
		name.chars().mapToObj(c-> (char)c).filter(c->!Character.isWhitespace(c)).forEach(ch-> count.put(ch, count.getOrDefault(ch,0)+1));
		
		for(Map.Entry<Character, Integer> entry : count.entrySet())
		{
			System.out.println(entry.getKey()+" "+ entry.getValue());
		}
	}
}