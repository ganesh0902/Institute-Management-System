package randomQiestions;

import java.util.Arrays;

public class Reverse {

	public static void main(String[] args) {
		
		int[] num = {12,3,4,5,6,7,8,85,4,3,2};
		          //2,3,4,85,8,7,6,5,4,3,12
		
		int start = 0;
		int end = num.length-1;
		
		while(start < end)
		{
			int temp = num[start];
			num[start] = num[end];
			num[end] = temp;
			start ++;
			end --;
		}
		System.out.println(Arrays.toString(num));
		
	}
}
