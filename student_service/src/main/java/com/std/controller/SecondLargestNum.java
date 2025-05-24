package com.std.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.OptionalInt;

public class SecondLargestNum {

	public static void main(String[] args) {
		
		int[] num = {2,5,6,7,8,3,12,1};
		
		OptionalInt findFirst = Arrays.stream(num).sorted().distinct().skip(1).findFirst();
		
		System.out.println(findFirst);
	}
}
