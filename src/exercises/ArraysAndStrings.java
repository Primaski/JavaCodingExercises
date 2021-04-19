package exercises;
import java.lang.reflect.Array;
import java.util.Arrays;
import util.Helper;

public class ArraysAndStrings{
	
	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();
	
	public static void RunTests() {
		String result= "NULL";
		String input = "";
		String input2 = "";
		
		try {
			
			System.out.println("Run which code?\n(1)All Characters Unique\n(2)String Permutation\n(3)Palindrome Permutation\n(4)One Edit Away\n" +
			"(5)String Compression\n(6)Quarter Turn Matrix\n(7)Zero Cross Matrix\n(8)String Rotation");
			int functionNo = Main.reader.nextInt();
			Main.reader.nextLine(); //consume \n
			
			switch(functionNo) {
				case(1):
					System.out.println("Determines if all characters in string are unique (Input: 1 string)");
					input = Main.reader.nextLine();
					result = Boolean.toString(allCharactersUnique(input));
					break;
				case(2):
					System.out.println("Determines whether one string is a permutation of another (Input: 2 strings)");
					input = Main.reader.nextLine();
					input2 = Main.reader.nextLine();
					result = Boolean.toString(isPermutation(input,input2));
					break;
				case(3):
					System.out.println("Determines whether your string can be permuted into a palindrome");
					input = Main.reader.nextLine();
					result = Boolean.toString(palindromePermutation(input));
					break;
				case(4):
					System.out.println("Determines whether your first string can be modified into your second via a single insertion, deletion or replacement.");
					input = Main.reader.nextLine();
					input2 = Main.reader.nextLine();
					result = Boolean.toString(isOneEditAway(input,input2));
					break;
				case(5):
					System.out.println("Type in a string to be compressed. Consecutive letter reoccurrences will be marked with numbers instead (ex: rrr => r3)");
					input = Main.reader.nextLine();
					result = stringCompression(input);
					break;
				case(6):
					System.out.println("Rotates a two dimensional NxN matrix 90 degrees (using an in-place algorithm).");
					int[][] matrix = h.twodMatrixIntInput(true);
					result = (quarterTurnMatrix(matrix));
					break;
				case(7):
					System.out.println("For any MxN matrix, generates a matrix where a single element being set to 0 results in the corresponding row and column's entries to be set to 0 as well. Limit 64x64 in dimensions.");		 
					int[][] matrix2 = h.twodMatrixIntInput();
					result = zeroCrossMatrix(matrix2);
					break;
				case(8):
					System.out.println("Determines if one string is a rotation of another string (for example, \"eckersch\" is a rotation of \"checkers\", but \"cehckers\" is not).");
					input = Main.reader.nextLine();
					input2 = Main.reader.nextLine();
					result = Boolean.toString(isStringRotation(input,input2));
					break;
				default:
					System.out.println("Unrecognized command.");
					return;
			}
			
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
		 * My solution was very similar to the first proposed ideal solution, and both had equal run times, just 
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
					bigIndex++; //make this new permanent index going forward.
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
		/* Runtime is O(n) since it only traverses the string once and uses StringBuilders. Approach was basically the same as optimal
		 * solution, however my code is a bit longer than necessary, and also a further optimization can be made by premeditating the
		 * size of the StringBuilder to prevent it from doubling its capacity right before the final addition.*/
	}
	
	/* Returns a N-by-N matrix rotated clockwise 90-degrees. THIS ALGORITHM IS REQUIRED TO BE IN-PLACE. Returns it in string form.*/
	private static String quarterTurnMatrix(int[][] matrix) throws Exception {
		/*Transposing the matrix will almost put everything in the right place, columns just need to be mirrored over the center column.*/
		if(matrix.length != matrix[0].length) {
			throw new Exception("Matrix must have same number of rows and columns.");
		}
		
		matrix = transposeMatrix(matrix);
		
		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[0].length / 2; col++) {
				int temp = matrix[row][col];
				matrix[row][col] = matrix[row][matrix[0].length - col - 1];
				matrix[row][matrix[0].length - col - 1] = temp; 
			}
		}
		
		return h.matrixToString(matrix);
		
		/* The optimal solution used a different method than mine, and while both of our efficiencies were O(n^2), theirs required only one swap function,
		 * while mine required 2 swap functions. Their methodology involved rotating each of the four corners, and then each adjacent point, working
		 * inwards. Mine involved a simple transposition and then a column mirroring. While their methodology is more efficient, I would argue mine was
		 * much easier to follow and read.*/
	}
	
	private static int[][] transposeMatrix(int[][] matrix){
		for(int row = 0; row < matrix.length; row++) {
			for(int col = row; col < matrix[0].length; col++) {
				/*using temporary variables to swap values as to keep space allocation O(1)
				 *col = row because for each iteration K of the outer loop, we've already swapped the first K values, repeating just undoes the process.*/
				int temp = matrix[row][col];
				matrix[row][col] = matrix[col][row];
				matrix[col][row] = temp;
			}
		}
		return matrix;
	}
	
	/* Returns an M-by-N matrix whereby each row or column containing a zero is entirely set to 0. Limited capacity 64x64. */
	private static String zeroCrossMatrix(int[][] matrix) throws Exception {

		int m = matrix.length;
		int n = matrix[0].length;
		if(m > 64 || n > 64) {
			/* We could create an array of longs to lift this restriction, but it adds a bunch of overhead that makes the code difficult to
			 * read and to test. For simplicity's sake, I will assume 64x64 is the largest possible array to be passed in.*/
			throw new Exception("Matrix must be bounded to 64 values on any given axis.");
		}
		
		/*using bit vectors again, like in allCharactersUnique*/
		long rowsToZeroify = 0;
		long colsToZeroify = 0;
		
		for(int row = 0; row < m; row++) {
			for(int col = 0; col < n; col++) {
				if(matrix[row][col] == 0) {
					long rowMask = 1 << row;
					rowsToZeroify |= rowMask;
					long colMask = 1 << col;
					colsToZeroify |= colMask;
				}
			}
		}
		
		for(int rowIndex = 0; rowIndex < m; rowIndex++) {
			if(((rowsToZeroify >> rowIndex) & 1) == 1) { //if row contains a 0...
				matrix = zeroCrossMatrixSetZeros(matrix, rowIndex, true);
			}
		}
		
		for(int colIndex = 0; colIndex < n; colIndex++) {
			if(((colsToZeroify >> colIndex) & 1) == 1) { //if column contains a 0...
				matrix = zeroCrossMatrixSetZeros(matrix, colIndex, false);
			}
		}
		
		return h.matrixToString(matrix);
		
		/* More or less the same approach as the book's solution. Runtime is O(m*n), and space 
		 * complexity is O(m+n). The book's initial solution gives O(m*n), but later finds a way
		 * to utilize the array's first column and row as a sort of memory to reduce space
		 * complexity to O(1). I strongly prefer my method, however, as the code is far easier to follow,
		 * and a space complexity of only O(m+n) definitely justifies it. */
	}
	
	private static int[][] zeroCrossMatrixSetZeros(int[][] input, int index, boolean isRow){
		if(isRow) {
			for(int i = 0; i < input.length; i++) {
				input[index][i] = 0;
			}
		}else {
			for(int j = 0; j < input[0].length; j++) {
				input[j][index] = 0;
			}
		}
		return input;
	}
	
	/* Determines whether a string can be "rotated" and form another string. */
	private static boolean isStringRotation(String str1, String str2) throws Exception {
		h.isAscii(str1);
		h.isAscii(str2);
		
		if(str1.length() != str2.length()) return false;
		str1 += str1; //if it's truly a rotation, str2 will be contained within str1+str1 (ex: llohe|llohe contains "hello")	
		return str1.contains(str2);
	}
	
}
