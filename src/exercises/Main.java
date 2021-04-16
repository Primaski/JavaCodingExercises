package exercises;

import java.util.Scanner;

public class Main {
	
	public static final Scanner reader = new Scanner(System.in);

	public static void main(String[] args) {
		while(true) {
			System.out.println("Which set of exercises?\n(1)Arrays and Strings\n(2)Linked Lists");
			int category = reader.nextInt();
			reader.nextLine(); //consume \n
		
			switch(category) {
				case(1):
					exercises.ArraysAndStrings.RunTests();
					reader.nextLine();
					break;
				case(2):
					exercises.LinkedLists.RunTests();
					reader.nextLine();
					break;
				default:
					System.out.println("Unknown category. Terminating.");
					return;
			}
		}
	}
	
}
