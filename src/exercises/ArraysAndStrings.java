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
			
			/*System.out.println("Determines whether your string can be permuted into a palindrome");
			input = reader.nextLine();
			result = Boolean.toString(palindromePermutation(input));*/
			
			/*System.out.println("Determines whether your first string can be modified into your second via a single insertion, deletion or replacement.");
			input = reader.nextLine();
			input2 = reader.nextLine();
			result = Boolean.toString(isOneEditAway(input,input2));*/
			
			System.out.println("Type in a string to be compressed. Consecutive letter reoccurrences will be marked with numbers instead (ex: rrr => r3)");
			 input = reader.nextLine();
			 result = stringCompression(input);
			 
			 /*System.out.println("Rotates a two dimensional matrix 90 degrees.");
			 int[][] specialInput = h.twodmatrixIntInput();
			 result = (quarterTurnMatrix(specialInput));*/
			 
			 
			
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
		/* Used bit vectors just like the optimal code approach, however, they assumed only 'A' -> 'z' - while
		 * mine appears a bit more complex since I assumed the entirety of ASCII. Runtime O(n) */
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
	
	/* Determine if you can edit String 'orig' with a single insertion, removal, or replacement, to end up with String 'edited' */
	private static boolean isOneEditAway(String orig, String edited) throws Exception {
		h.isAscii(orig);
		h.isAscii(edited);	
		int strSizeDiff = orig.length() - edited.length();
		
		if(strSizeDiff < -1 || strSizeDiff > 1) { 
			return false; //cannot have a length discrepancy of 2+ without 2+ insertions
		}else if(strSizeDiff == -1) { 
			return isOneEditAwayHelper(edited, orig); //edited is bigger, there may be an insertion
		}else if(strSizeDiff == 1) { 
			return isOneEditAwayHelper(orig, edited); //orig is bigger, there may be a deletion
		}
		
		//strings are same length, there may be a replacement 
		boolean editFound = false;
		for(int i = 0; i < orig.length(); i++) {
			if(orig.charAt(i) != edited.charAt(i)) {
				if(editFound) { 
					return false; //this would be the second discrepancy
				}else {
					editFound = true;
				}
			}
		}
		return true;
		/* Runtime of my algorithm is O(n), as it only traverses the string once, regardless of case.
		 * My solution was very similar to the first proposed ideal solution, and both had equal runtimes, just 
		 * different methods of structuring.
		 * */
	}
	
	//helper method: used in case of two unequal sized arrays - implies insertion or removal
	private static boolean isOneEditAwayHelper(String big, String small) {
		boolean shiftFound = false;
		
		for(int smallIndex = 0, bigIndex = 0; smallIndex < small.length(); smallIndex++, bigIndex++) {
			if(small.charAt(smallIndex) != big.charAt(bigIndex)) {
				if(shiftFound) {
					return false; //can't be two discrepancies
				}
				if(small.charAt(smallIndex) != big.charAt(bigIndex+1)) {
					return false; //couldn't have been a single insertion
				}else {
					bigIndex++; //make this new permanent index going forward
					shiftFound = true;
				}
			}
		}
		return true;
	}
	
	/* Returns a more or equally compact version of the string, by marking consecutive letter reoccurrences with numbers. */
	private static String stringCompression(String str) throws Exception {
		h.isAscii(str);
		StringBuilder newStr = new StringBuilder();
		char currChar, nextChar; 
		currChar = nextChar = '\0';
		int repLength = 1;
		
		for(int i = 0; i < str.length() - 1; i++) {
			currChar = str.charAt(i);
			nextChar = str.charAt(i+1);
			if(currChar == nextChar) {
				repLength++; //will go from 1 -> 2 if this is the start of a new chain
			}else if(repLength > 1) {
				//characters are NOT equal, but repetition longer than one was recorded. add the compressed string
				newStr.append(currChar + Integer.toString(repLength));
				repLength = 1;
			}else {
				newStr.append(currChar); //no repetition has been detected, so we just append as normal, as "a1" is longer than "a"
			}
		}
		
		//accounting for last character
		if(repLength > 1) {
			return newStr.append(nextChar + Integer.toString(repLength)).toString();
		}
		return newStr.append(nextChar).toString();
		/*Runtime is O(n) since it only traverses the string once and uses StringBuilders. Approach was basically the same as optimal
		 * solution, however my code is a bit longer than necessary, and also a further optimization can be made by premeditating the
		 * size of the StringBuilder to prevent it from doubling its capacity right before the final addition.*/
	}
}
