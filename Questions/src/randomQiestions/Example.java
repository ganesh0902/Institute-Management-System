package randomQiestions;

import java.util.Arrays;

public class Example {

	public static void main(String[] args) {
		
		int[] num = {1,4,6,8,3,2,9,77,34,22};		
		int target = 22;		
		int index = num.length-1;
		
		//find the index of target element
		for(int i=0;i<num.length;i++)
		{
			if(num[i] == target)
			{
				index = i;
				
				break;
			}
		}
		
		//check target element is already present at last index
		//then shift all the element left hand side
		
		if(index !=-1)
		{
			int temp = num[index];
			for(int i = index;i<num.length-1;i++)
			{
				num[i] = num[i+1];
			}
			num[num.length-1] = temp;
		}
		
		System.out.println(Arrays.toString(num));
	}
}
