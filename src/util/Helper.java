package util;

import java.util.Arrays;
import java.util.Random;

import exercises.Main;

public class Helper{

	public Random random = new Random();
	
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

	public int[][] twodMatrixIntInput() throws Exception{
		return twodMatrixIntInput(false);
	}
	
	public int[][] twodMatrixIntInput(boolean forceRowColumnToBeEqual) throws Exception {
		System.out.println("Enter the number of rows.");
	       int rows = Main.reader.nextInt();
	       int columns = rows;
	       if(!forceRowColumnToBeEqual) {
	    	   	System.out.println("Enter the number of columns.");
	       		columns = Main.reader.nextInt();
	       }
	       if(rows < 1 || columns < 1) {
	    	   throw new Exception("Incorrectly formatted matrix.");
	       }
	       
	       System.out.println("Enter array elements (integers):");    
	       int arr[][] = new int[rows][columns];	        
	          
	        for(int i = 0; i < rows; i++){            
	            for(int j = 0; j < columns; j++){
	            	System.out.print("[" + Integer.toString(i) + ", " + Integer.toString(j) + "]: ");
	                arr[i][j] = Main.reader.nextInt();
	            }
	         }
	        System.out.println();
		return arr;
	}
	
	public String matrixToString(int[][] matrix){
		return Arrays.deepToString(matrix).replace("], ", "]\n");
	}
	
}
