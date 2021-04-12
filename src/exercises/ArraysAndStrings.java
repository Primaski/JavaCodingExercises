package exercises;
import java.lang.reflect.Array;
import java.util.Scanner;

public class ArraysAndStrings{
	
	int opCount = 0;
	private static Helper h = new Helper();
	
	public static void RunTests() {
		Scanner reader = new Scanner(System.in);
		String result= "NULL";
		String input = "";
		String input2 = "";
		
		try {
			
			/* Test methods */
			
			/*System.out.println("Determines if all characters in string are unique (Input: 1 string)");
			/input = reader.nextLine();	
			/result = Boolean.toString(allCharactersUnique(input));*/
			
			/*System.out.println("Determines whether one string is a permutation of another (Input: 2 strings)");
			input = reader.nextLine();
			input2 = reader.nextLine();
			result = Boolean.toString(isPermutation(input,input2));*/
			
			System.out.println("Determines whether your string can be permuted into a palindrome");
			input = reader.nextLine();
			result = Boolean.toString(palindromePermutation(input));
		
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println(result);
		return;
	}
	
	/* Determine if all characters are unique. Assumes ASCII, and case sensitivity.*/
	private static boolean allCharactersUnique(String str) throws Exception {
		h.isAscii(str);
		/* My attempt - bit vectors */
		long[] usedChars = new long[]{0,0,0,0};
		for(int i = 0; i < str.length(); i++) {
			int charIndex = str.charAt(i); //ex: 'C' = 67, 0100 0010
			byte arrIndex = (byte) (charIndex / 64); //index 67 would go in usedChars[1]
			long mask = 1 << (charIndex % 64); // 1 << (67 % 64) = 3, mask to ...0000 1000 (local index 3) 
			
			//if overlap exits, then duplicate
			if((usedChars[arrIndex] & mask) > 0) return false;
			
			usedChars[arrIndex] |= mask;
		}
		return true;
		/* Optimal code also used bit vectors, but assumed only 'A' -> 'z'. Runtime O(n) */
	}
	
	/* Determine if one string is a permutation of another. Assumes ASCII, and case sensitivity.*/
	@SuppressWarnings("unchecked")
	private static boolean isPermutation(String str1, String str2) throws Exception {
		h.isAscii(str1);
		h.isAscii(str2);
		
		if(str1.length() != str2.length()) return false;
				
		char[] sorted1 = h.mergeSort(str1.toCharArray());
		char[] sorted2 = h.mergeSort(str2.toCharArray());
		for(int i = 0; i < str1.length(); i++) {
			if(sorted1[i] != sorted2[i]) return false;
		}
		return true;
		/* My code was not optimal since it takes O(nlog(n)) runtime, and can be cut down to O(n) [albeit with higher space complexity].
		 * Optimal solution: create array of 256 initialized to 0, and increment each character as they come up for str1. Iterate through
		 * str2, and decrement. If any array value ever hits a negative, return false. If there is no termination, then there are no
		 * negative values, and thus also no non-zero positive values (since str are same length). */
	}
	
	/* Determine if string can be permuted in any manner to make a palindrome. Assumes ASCII, and case sensitivity.*/
	private static boolean palindromePermutation(String str) throws Exception {
		h.isAscii(str);
		
		int[] charCount = new int[256];
		char[] str1 = str.toCharArray();
		
		for(char c : str1) {
			charCount[(int)(c)]++;
		}
		
		boolean odd = false; //every character must appear 2N times, except a potential middle character "aaeiou|u|uoieaa", u = 3. if two, then it's not a palindrome.
		for(int curr : charCount) {
			if((curr % 2) != 0) {
				if(odd) {
					return false;
				}
				odd = true; //if any other char has odd parity, it cannot be a palindrome
			}
		}
		return true;
		/* My code was very good and similar to the first solution, O(n) runtime. However, this can actually be even further
		 * optimized (only if an additional assumption is made, and you could reduce ASCII to 32 or 64 bits) by again using bit vectors. 
		 * 0 for even, 1 for odd. Flip it on and off like a switch. Coincidentally, it perfectly fits the confines of the problem - 
		 * because subtracting one when there are either zero or one 1's in the vector, results in a bitwise AND cast that always equals 0,
		 * but will always equal 1 in all other cases:
		 * 0000 0100 & 0000 0011 = 0 (one odd-parity chars: acceptable)
		 * 0000 0000 & 1111 1111 = 0 (zero odd-parity chars: acceptable)
		 * 0011 0000 & 0010 1111 = 10 0000 (two odd-parity chars: unacceptable) 
		 * 1111 1111 & 1111 1110 = 1111 1110 (seven odd-parity chars: unacceptable)
		 * */
	}
	

}
