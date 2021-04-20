package util;

import java.util.Arrays;

import exercises.Main;

public class LinkedListUtil {
	
	/*Extension of Helper, but specific to Linked Lists. Separating because most classes don't need Linked List utility.*/
	
	Helper h = new Helper();
	
	Node[][] hashTable = null;
	
	public Node padZerosLeft(Node linkedList, int numberOfZeros) {
		if(numberOfZeros < 1) return linkedList;
		Node newListHead = new Node(0);
		Node newList = newListHead;
		numberOfZeros--;
		for(int i = numberOfZeros; i > 0; i--) {
			newList.next = new Node(0);
			newList = newList.next;
		}
		newList.next = linkedList;
		return newListHead;
	}

	/* Connects list to list2 at a random point in list2 (some values at the end of list1 may be lost forever). Assumes the heads of these nodes are stored in an external class.*/
	public void intersectListsAtRandomPoint(Node list1, Node list2, int list1Length, int list2Length) {
		if(list1 == null || list2 == null) return;
		int list1PointOfIntersection = h.random.nextInt(list1Length);
		int list2PointOfIntersection = h.random.nextInt(list2Length);
		Node connectorNode = null;
		for(int list1Pos = 0; list1Pos < list1Length; list1Pos++) {
			if(list1Pos == list1PointOfIntersection) {
				break;
			}
			list1 = list1.next;
		}
		for(int list2Pos = 0; list2Pos < list2Length; list2Pos++) {
			if(list2Pos == list2PointOfIntersection) {
				list1.next = list2;
			}
			list2 = list2.next;
		}
		return;
	}
	
	public int getLength(Node list) {
		if(list == null) return 0;
		Node temp = list;
		int length = 0;
		while(temp != null) {
			length++;
			temp = temp.next;
		}
		return length;
	}
	
	public Node goToIndex(Node list, int index) {
		if(list == null) return null;
		while(index > 0) {
			if(list.next == null) return null;
			index--;
			list = list.next;
		}
		return list;
	}
	
	public Node createRandomLoop(Node list) {
		/*Too unsafe to put in IntLinkedList*/
		if(list == null) return null;
		int length = getLength(list);
		int loopStartIndex = h.random.nextInt(length);
		Node loopStart = goToIndex(list, loopStartIndex);
		Node loopEnd = goToIndex(list, length-1);
		loopEnd.next = loopStart;
		return list;
	}
	
	public void printListValueByValue(Node list) {
		Node temp = list;
		System.out.println("Press enter to get next value, or q to exit.");
		while(!Main.reader.nextLine().contentEquals("q")) {
			System.out.print(temp.value + " => ");
			temp = temp.next;
			if(temp == null) {
				System.out.println("The list has ended.");
			}
		}
	}
	
	public void createHashTable(int length) {
		this.hashTable = new Node[length][];
	}
	
	public void insertInHashTable(Node node) {
		int arrIndex = hashFunction(node);
		if(hashTable[arrIndex] == null) {
			hashTable[arrIndex] = new Node[1];
			hashTable[arrIndex][0] = node;
		}else{
			Node[] oldArr = hashTable[arrIndex];
			Node[] newArr = new Node[hashTable[arrIndex].length + 1];
			newArr[0] = node;
			for(int i = 1; i < newArr.length; i++) {
				newArr[i] = oldArr[i-1];
			}
			hashTable[arrIndex] = newArr;
		}
	}
	
	public int hashFunction(Node node) {
		return node.value % hashTable.length;
	}
	
	public boolean nodeExistsInHashTable(Node node) {
		int arrIndex = hashFunction(node);
		if(hashTable[arrIndex] == null) return false;
		for(int i = 0; i < hashTable[arrIndex].length; i++) {
			if(hashTable[arrIndex][i] == node) {
				return true;
			}
		}
		return false;
	}
	
	public void printHashTable() {
		if(hashTable == null) {
			System.out.println("No values.");
		}
		StringBuilder res = new StringBuilder();
		for(int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				res.append(i + ": " + Arrays.toString(hashTable[i]));
			}
		}
		System.out.println(res.toString());
	}

	public void printFirstNValues(Node list, int n) {
		StringBuilder str = new StringBuilder();
		while(n > 0) {
			str.append(list.value + " => ");
			if(list.next == null) {
				System.out.println(str.append("(list ends here)").toString());
				return;
			}
			n--;
			list = list.next;
		}
		System.out.println(str.append(". . .").toString());
	}

}
