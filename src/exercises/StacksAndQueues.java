package exercises;

import util.Helper;

public class StacksAndQueues {

	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();

	public static void RunTests() {
		String input = "";
		String input2 = "";
		
		try {
			System.out.println("Run which code?\n(1)Three Stacks in One Array (text only)\n");
			int functionNo = Main.reader.nextInt();
			Main.reader.nextLine(); //consume \n
			switch(functionNo) {
				case(1):
					threeStacksOneArray();
					return;
				default:
					break;
			}
		}catch(Exception e) {
			
		}
		System.out.println("Not yet implemented.");
		return;
	}

	private static void threeStacksOneArray() {
		System.out.println("The question asks how you might go about implementing three stacks in a single array.\n"
				+ "Obviously, it would be very inefficient to partition the array, since");
	}
}
