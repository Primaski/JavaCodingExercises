package exercises;

import util.Helper;
import util.IntLinkedList;
import util.Node;

public class LinkedLists {
	
	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();
	private static IntLinkedList ill = new IntLinkedList();
	
	
	public static void RunTests() {
		String input = "";
		String input2 = "";
		
		try {
			
			System.out.println("Run which code?\n(1)Remove Duplicates\n(2)Return Kth to Last Element\n(3)Delete middle node\n(4)Partition nodes");
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
	}
	
	
	//repetitive code
	private static void getUserLinkedList() throws Exception {
		System.out.print("List ('random' for random): ");
		String x = Main.reader.nextLine();
		ill.insertNodesFromString(x);
		System.out.println("Before: " + ill.toString());
		return;
	}
}
