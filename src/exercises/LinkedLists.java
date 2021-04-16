package exercises;

import util.Helper;
import util.IntLinkedList;
import util.Node;

public class LinkedLists {
	
	private int opCount = 0; //add where applicable to count operations
	private static Helper h = new Helper();
	private static IntLinkedList ill = new IntLinkedList();
	
	
	public static void RunTests() {
		String result= "NULL";
		String input = "";
		String input2 = "";
		
		try {
			
			System.out.println("Run which code?\n(1)Remove Duplicates\n(2)Return Kth to Last Element\n(3)Delete middle node");
			int functionNo = Main.reader.nextInt();
			Main.reader.nextLine(); //consume \n
				
			switch(functionNo) {
				case(1):
					System.out.println("Removes duplicates from an unsorted linked list. Insert int values of list, separated by a space.");
					input = Main.reader.nextLine();
					removeDuplicates(input);
					break;
				case(2):
					System.out.println("Given a linked list, returns the kth to last element, where k is specified by the user. Please first input the int values of the list, enter, and then the desired value for k.");
					input = Main.reader.nextLine();
					input2 = Main.reader.nextLine();
					int k = Integer.parseInt(input2);
					System.out.println(returnKthToLast(input, k));
					return;
				case(3):
					System.out.println("Deletes a node at a randomly specified index, given ONLY access to that one particular node. No user input.");
					ill.generateSampleLinkedList(10);
					System.out.println("List before:\n" + ill.toString());
					int index = h.random.nextInt(9) + 1; //can't resolve to index 0 or 9
					Node randomNode = ill.getNodeAtIndex(index);
					System.out.println("Deleting node at index " + index + ".");
					deleteMiddleNode(randomNode);
					System.out.println("List after:");
					break;
				case(4):
					/*System.out.println("Given a linked list and a some value x, partitions elements whereby all nodes with values less than x appear before the rest.");
					input = Main.reader.nextLine();
					input2 = Main.reader.nextLine();
					int value = Integer.parseInt(input2);
					partition(input, value);
					break;*/
				default:
					System.out.println("Function does not exist.");
					return;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
			
		ill.print();
		ill.reset();
		return;
	}


	/*Removes duplicate values from linked list. REQUIREMENT: No buffer allowed.*/
	private static void removeDuplicates(String list) throws Exception {
		ill.insertNodesFromString(list);
		if(ill.head.next == null) return;
		
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
	private static String returnKthToLast(String list, int k) throws Exception {
		ill.insertNodesFromString(list);
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
	private static void partition(String list, int value) {
		//TODO
		return;
	}
}
