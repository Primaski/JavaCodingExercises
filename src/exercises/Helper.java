package exercises;

import java.util.Arrays;
import java.util.Scanner;

public class Helper{

	/* Throws exception if not ASCII. */
	public boolean isAscii(String str) throws Exception {
		if(!str.matches("^\\p{ASCII}*$")) {
			throw new Exception("The function called requires ASCII, and at least one non-ASCII character was passed in.");
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public int[] mergeSort(int[] arr) {
		
		if(arr.length < 2) return arr;
		
		int midpoint = arr.length / 2;
		int[] left = new int[midpoint];
		int[] right = new int[arr.length - midpoint];
		
		for(int i = 0; i < midpoint; i++) {
			left[i] = arr[i];
		}
		for(int i = 0; i < (arr.length - midpoint); i++) {
			right[i] = arr[midpoint+i];
		}
		
		left = mergeSort(left);
		right = mergeSort(right);
		return merge(left,right);
	}
	
	private int[] merge(int[] left, int[] right) {
		
		int[] res = new int[left.length + right.length];
		int l, r, pon; //pointers
		l = r = pon = 0;
		
		while(l < left.length || r < right.length) {
			if(l < left.length && r < right.length) {
				if(left[l] > right[r]) {
					res[pon++] = right[r++];
				} else {
					res[pon++] = left[l++];
				}
			}else if(l < left.length) {
				res[pon++] = left[l++];
			}else {
				res[pon++] = right[r++];
			}
		}
		return res;
		
	}
	
	public char[] mergeSort(char[] arr) {
		int[] intArr = new int[arr.length];
		for(int i = 0; i < arr.length; i++) {
			intArr[i] = (int)(arr[i]);
		}
		intArr = mergeSort(intArr);
		for(int i = 0; i < arr.length; i++) {
			arr[i] = (char)(intArr[i]);
		}
		return arr;
	}
	
	public static void printArray(String[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	public int[][] twodmatrixIntInput() {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number of rows.");
	       int rows=sc.nextInt();
	       System.out.println("Enter the number of columns.");
	       int columns=sc.nextInt();
	       
	       System.out.println("Enter array elements (integers):");    
	       int arr[][]=new int[rows][columns];	        
	          
	        for(int i=0; i<rows;i++){            
	            for(int j=0; j<columns;j++){
	            	System.out.print("[" + Integer.toString(rows) + ", " + Integer.toString(columns) + "]");
	                arr[i][j]=sc.nextInt();
	            }
	         }
		return arr;
	}
}
