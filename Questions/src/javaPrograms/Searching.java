package javaPrograms;

import java.util.Arrays;

public class Searching {

	public static void main(String[] args) {
		
		int arr[] = {1,4,7,89,3,2,99,22,33,23,43};
		int value =0;
		int index = arr.length -1;
		int target = 1;
		//find the target element and shift at end of arr
		//step
		//find target number index and value
		//shift all the element left side from index
		
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i] == target)
			{
				index =i;
			}
		}			
		
		if(index !=-1)
		{
			value = arr[index];
			
			for(int i=index;i<arr.length-1;i++)
			{
				arr[i]= arr[i+1];
			}
		}
		
		arr[arr.length-1] = value;
		
		System.out.println(Arrays.toString(arr));
		
	}
}