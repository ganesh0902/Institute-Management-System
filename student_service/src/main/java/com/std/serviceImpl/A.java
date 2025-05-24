package com.std.serviceImpl;

public class A {

	public void show() {
		System.out.println("Static method called");
	}

	public static void main(String[] args) {
				
		//
		String name = "Ganesh Sakhare";
		
		int accurance=0;
		int index = 0;				
	
		for(int j=name.length()-1;j>0;j--)
		{
			if(name.charAt(j) == 'e')
			{
				accurance = accurance +1;
				index = j;
			}
		}		
	System.out.println(accurance);
	System.out.println(index);
	
	String original = "edcba";
	  
	  //abcde
	  
	int j=0;
	   char temp=0;   
	     char[] chars = original.toCharArray();			     
	     for (int i = 0; i <chars.length; i++) {
	         for ( j = 0; j < chars.length; j++) {
	         if(chars[j]>chars[i]){
	             temp=chars[i];
	             chars[i]=chars[j];
	              chars[j]=temp;
	          }
	     }
	  }
	    for(int k=0;k<chars.length;k++){
	    System.out.println(chars[k]);
	   }
	}
}
