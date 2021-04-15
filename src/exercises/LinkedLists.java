package exercises;

import util.Helper;

public class LinkedLists {
	
	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();
	
	
	public static void RunTests() {
		String result= "NULL";
		String input = "";
		String input2 = "";
		
		try {
			
			System.out.println("Run which code?");
			int functionNo = Main.reader.nextInt();
			Main.reader.nextLine(); //consume \n
				
			switch(functionNo) {
				default:
					System.out.println("Function does not exist.");
					return;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
			
		//System.out.println(result);
		//return;
	}
}
