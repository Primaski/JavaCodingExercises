package exercises;

import util.Helper;
import util.IntLinkedList;
import util.Node;

public class LinkedLists {
	
	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();
	private static IntLinkedList ill = new IntLinkedList();
	private static IntLinkedList ill2 = new IntLinkedList(); //required for problem 5
	private static IntLinkedList ill3 = new IntLinkedList(); //required for problem 5
	
	
	public static void RunTests() {
		String input = "";
		String input2 = "";
		
		try {
			
			System.out.println("Run which code?\n(1)Remove Duplicates\n(2)Return Kth to Last Element\n(3)Delete middle node\n(4)Partition nodes\n(5)Sum two lists\n(6)Is Palindrome");
			int functionNo = Main.reader.nextInt();
			Main.reader.nextLine(); //consume \n
				
			switch(functionNo) {
				case(1):
					System.out.println("Removes duplicates from an unsorted linked list. Insert int values of list separated by a space (or type 'random').");
					getUserLinkedList();
					removeDuplicates();
					break;
				case(2):
					System.out.println("Given a linked list, returns the kth to last element.");
					getUserLinkedList();
					System.out.print("k: ");
					input = Main.reader.nextLine();
					int k = Integer.parseInt(input);
					System.out.println(returnKthToLast(k));
					return;
				case(3):
					System.out.println("Deletes a node at a randomly specified index, given ONLY access to that one particular node. No user input.");
					ill.generateSampleLinkedList(10);
					System.out.println("Before: " + ill.toString());
					int index = h.random.nextInt(9) + 1; //can't resolve to index 0 or 9
					Node randomNode = ill.getNodeAtIndex(index);
					System.out.println("Deleting node at index " + index + ".");
					deleteMiddleNode(randomNode);
					break;
				case(4):
					System.out.println("Given a user-specified linked list ('random' for random) and a some value x, partitions elements whereby all nodes with values less than x appear before the rest.");
					getUserLinkedList();
					System.out.print("Partition value (x): ");
					input2 = Main.reader.nextLine();
					int value = Integer.parseInt(input2);
					partition(value);
					break;
				case(5):
					System.out.println("Given two randomly-specified single-digit-valued linked lists, will compile a single number out of each of them, and then create a third summed linked list.");
					System.out.print("Length of first linked list (1+): ");
					int length1 = Main.reader.nextInt();
					System.out.print("Length of second linked list (1+): ");
					int length2 = Main.reader.nextInt();
					ill.generateSampleLinkedList(length1, 9);
					ill2.generateSampleLinkedList(length2, 9);
					System.out.print("Interpret values in reverse order? (true/false): ");
					boolean invert = Main.reader.nextBoolean();
					Main.reader.nextLine(); //eats remainder
					System.out.println("First linked list: " + ill.toString() + "\nSecond linked list: " + ill2.toString());
					sumTwoLists(invert);
					System.out.println("Result: " + ill3.toString());
					ill.reset();
					ill2.reset();
					ill3.reset();
					return;
				case(6):
					System.out.println("Given a linked list, determines whether it is a palindrome.");
					getUserLinkedList();
					ill.print();
					System.out.println(Boolean.toString(isPalindrome(ill.head)));
					ill.reset();
					return;
				case(7):
					System.out.println("Given two linked lists, tell if any of their nodes intersect by REFERENCE.");
					System.out.println("Not yet implemented");
					break;
				default:
					System.out.println("Function does not exist.");
					return;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("After: " + ill.toString());
		ill.reset();
		return;
	}


	/*Removes duplicate values from linked list. REQUIREMENT: No buffer allowed.*/
	private static void removeDuplicates() throws Exception {		
		//Given our restraint on a buffer, we will use the runner technique to tackle this somewhat iteratively.
		Node slowNode = ill.head;
		Node fastNode = ill.head; //fastNode will always have to evaluate the NEXT node, so as to reassign references correctly
				
		//the first part of this statement is necessary because slowNode will equal the final node at the very end, meaning ".next.next" will not exist
		while(slowNode != null) { 
			while(fastNode.next != null) {
				if(slowNode.value == fastNode.next.value) {
					fastNode.next = fastNode.next.next; //pass over the undesired node, bridge the gap
				}else{ //need an else so that the following node does not skip evaluation
					fastNode = fastNode.next; //advance while keeping slowNode in same place. That way, every pair of two nodes are compared
				}
			}
			slowNode = slowNode.next;
			fastNode = slowNode;
		}
		return;
		
		/* By restraint of a lack of a buffer, my code is forced to run in O(n^2) time, as opposed to the optimal O(n) time.
		 * It proved to be much more challenging than expected. Upon seeing the optimal code, I noticed redundancies in my code
		 * that I was able to remove (like a pointless if statement in null-checking fastNode.next.next to set a value to null), but
		 * my code ran exactly the same conceptually as the book solution. */
	}
	
	/* For a linked list where head index = 0, head.next index = 1 etc... return the kth-to-last element.*/
	private static String returnKthToLast(int k) throws Exception {
		if(ill.head == null) return "List cannot be empty.";
		if(k <= 0) return "K must be larger than 0";
		
		/* Since it is a singly linked list, we will have a slow node k elements behind the fast node. Once the main node reaches the end of
		 * the list, the runner node will return its value. */
		Node slowNode = null;
		Node fastNode = ill.head;
		
		while(fastNode != null) {
			fastNode = fastNode.next;
			k--;
			if(k == 0) slowNode = ill.head;
			if(k < 0) slowNode = slowNode.next;
		}
		
		return (slowNode != null) ? Integer.toString(slowNode.value) : "K cannot be larger than the number of elements in the list."; 
		
		/* This code will run in O(n) time, since the maximum number of Nodes visited is N, and all other operations are O(1).
		 * My code utilized the same general approach as their iterative one (as opposed to the recursive one), yet they implemented a
		 * second loop to separate the insertion of slowNode from its iteration. */
	}
	
	/* Given a Node that's not the head nor the tail, this function "deletes" it from the list, given only that node. */
	private static boolean deleteMiddleNode(Node node) {
		//Given we only have access to the next node, we can pull data from it and simply skip over it, in effect, replacing the current node
		if(node == null || node.next == null) return false; //not a middle node
		node.value = node.next.value;
		node.next = node.next.next;
		return true;
		
		/* Code runs in constant time. Approach was the same, I believe it's the only possible approach given this limited data. */
	}
	
	/* Given some value, all elements BELOW this value will be moved before all elements ABOVE/AT this value. May not be in order.*/
	private static void partition(int value) throws Exception {
		
		//creating two separate sets of new nodes, combining at the end
		Node headLessThan, headGreaterThan, currLessThan, currGreaterThan;
		headLessThan = headGreaterThan = currLessThan = currGreaterThan = null;
		
		while(ill.head != null) {
			Node nextNode = ill.head.next; //save so it can be deleted and prevents an infinite loop
			ill.head.next = null;
			if(ill.head.value < value) {
				if(headLessThan == null) { 	//initial assignment, should only be run once
					headLessThan = ill.head;
					currLessThan = headLessThan;
				}else {
					currLessThan.next = ill.head;
					currLessThan = ill.head;
				}
			}else{
				if(headGreaterThan == null) { //initial assignment, should only be run once
					headGreaterThan = ill.head;
					currGreaterThan = headGreaterThan;
				}else {
					currGreaterThan.next = ill.head;
					currGreaterThan = ill.head;
				}
			}
			ill.head = nextNode;
		}
		
		ill.head = (headLessThan == null) ? headGreaterThan : headLessThan;
		if(currLessThan != null) currLessThan.next = headGreaterThan; //if headGreaterThan == null, this assignment will work still
		return;
		/* I was shocked to find that my implementation was very similar to one of the proposed solutions in the book. My solution 
		 * runs in O(n) time, and essentially incrementally destroys the original list, while rebuilding two lists: 
		 * one list for nodes less than the value, and one greater than or equal to - then merges them. A less clunky approach
		 * (with the same runtime) would have been to set the original head as the "center" node, and prepend nodes less than the
		 * value, and append those greater.*/
	}
	
	/* Given two linked lists comprised of only single-digit nodes, it will compile the full number of each of them incrementally 
	 * (ones place, tens place, hundreds place...), and then sum the overall "numerical value" of the two lists in a new list.
	 * (example: 1 -> 6 -> 5 and 2 -> 7 -> 8 yielding 4 -> 4 -> 3). Takes from ill1/ill2, stores result in ill3. */
	private static void sumTwoLists(boolean storedInReverseOrder) throws Exception {
		if(ill.head == null || ill2.head == null) throw new Exception("Both lists require elements.");
		if(storedInReverseOrder) {
			getSumBackwards(ill.head, ill2.head, 0); //comment in getSumBackwards
			return;
		}
		
		if(ill.length() > ill2.length()) {
			ill2.head = h.padZerosLeft(ill2.head, ill.length()-ill2.length());
		}else if(ill2.length() > ill.length()) {
			ill.head = h.padZerosLeft(ill.head, ill2.length()-ill.length());
		}
		int lastDigitRemainder = getSumForwards(ill.head, ill2.head); //comment in getSumForwards
		if(lastDigitRemainder != 0) ill3.prependNode(lastDigitRemainder);
		return;
	}
	
	private static void getSumBackwards(Node l1, Node l2, int remainder) {
		int total = (l1 == null ? 0 : l1.value) + (l2 == null ? 0 : l2.value) + remainder;
		int nodeVal = total % 10;
		int remain = total / 10;
		
		if(l1 == null && l2 == null) { //BASE CASE
			if(remain == 0) return;
			ill3.prependNode(remain);
			return;
		}
		
		l1 = (l1 == null ? null : l1.next);
		l2 = (l2 == null ? null : l2.next);
		getSumBackwards(l1, l2, remain);
		ill3.prependNode(nodeVal);
		return;
		/* This function required heavy thought over a long period of time. It's not how I'm normally used to implementing recursive
		 * functions, since the base case occurs in the middle, and the quirk of the remainder being passed forward through arguments instead of
		 * backward through a return value. This runs very similar to the proposed solution, though is written very differently. 
		 * Runs in O(max(length(l1),length(l2)) time. */
	}	
	
	private static int getSumForwards(Node l1, Node l2) {
		//these two cases should happen at same time due to padding
		int remainder = 0;
		if(l1.next != null || l2.next != null) remainder = getSumForwards(l1.next, l2.next); //get as deep as possible
		int total = l1.value + l2.value + remainder;		
		int nodeVal = total % 10;
		int remain = total / 10;
		ill3.prependNode(nodeVal);
		return remain;
		/* This function was much simpler once the general idea was down. The idea was to recurse to the end of the list and use
		 * return values to carry remainders - all the while, adding to the result list. The code for this section was significantly shorter
		 * than the book's, however, that also lies in part due to my implementation, where the result list in stored in another class.
		 * There were a few implementation differences, but the padding zero idea remained the same. Runs in O(max(length(l1),length(l2))) time. */
	}
	
	/* Given a linked list, determines whether the list is a palindrome. */
	private static boolean isPalindrome(Node list) {
		if(list == null || list.next == null) return true;
		if(isPalindromeHelper(list, list) != null) return true;
		return false;
		/* My code ended up vastly different from any solutions in the book. The only recursive solution in the book utilized a stack operating 
		 * on the second half of the list. Both pieces of code have a runtime of O(n) and require an external data structure, but I feel as if
		 * my recursive implementation is cleaner and shorter, plus makes clever use of the fact of using NULL data to double as a boolean. */
	}
	
	private static Node isPalindromeHelper(Node list, Node queue) {
		/*RETURNING NULL SIGNALS THAT THE STRING IS NOT A PALINDROME*/
		Node nextInQueue = queue;
		if(list.next != null) {
			nextInQueue = isPalindromeHelper(list.next, queue);
			if(nextInQueue == null) return null; //was not successful
		}
		
		if(list.value == nextInQueue.value) {
			//if it's null, we've reached the end successfully. send back a non-null node to report the good news.
			return (nextInQueue.next == null) ? new Node(1) : nextInQueue.next;
		}
		return null; //null works as a boolean to inform that it is not a palindrome
	}
	
	/* Determines whether two lists intersect at any node by REFERENCE. */
	private static boolean doListsIntersect(Node list1, Node list2) {
		return false;
	}

	
	/************************/
	//repetitive code
	private static void getUserLinkedList() throws Exception {
		System.out.print("List ('random' for random): ");
		String x = Main.reader.nextLine();
		ill.insertNodesFromString(x);
		System.out.println("Before: " + ill.toString());
		return;
	}
}
