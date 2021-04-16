package exercises;

import util.Helper;
import util.IntLinkedList;

public class LinkedLists {
	
	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();
	private static IntLinkedList ill = new IntLinkedList();
	
	
	public static void RunTests() {
		String result= "NULL";
		String input = "";
		String input2 = "";
		
		try {
			
			System.out.println("Run which code?\n(1)Remove Duplicates");
			int functionNo = Main.reader.nextInt();
			Main.reader.nextLine(); //consume \n
				
			switch(functionNo) {
				case(1):
					System.out.println("Removes duplicates from an unsorted linked list. Insert int values of array, separated by a space.");
					input = Main.reader.nextLine();
					result = removeDuplicates(input);
					break;
				default:
					System.out.println("Function does not exist.");
					return;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
			
		System.out.println(result);
		return;
	}


	private static String removeDuplicates(String input) throws Exception {
		ill.insertNodesFromString(input);
		ill.print();
		return "";
	}
}
