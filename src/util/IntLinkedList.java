package util;

public class IntLinkedList {
	
	public Node head = null;
	
	Helper h = new Helper();
	
	public void appendNode(int val) {
		
		Node newNode = new Node(val);
		if(head == null) {
			head = newNode;
			return;
		}
		
		Node curr = head;
		while(curr.next != null) {
			curr = curr.next;
		}
		curr.next = newNode;
		return;
	}
	
	public void prependNode(int val) {
		Node newNode = new Node(val);
		if(head == null) {
			head = newNode;
			return;
		}
		Node temp = head;
		head = newNode;
		head.next = temp;
		return;
	}
	
	public void insertNodesFromString(String nodes) throws Exception {
		if(nodes.toLowerCase().equals("random")) {
			generateSampleLinkedList(10);
			return;
		}
		if(nodes == null || nodes.isEmpty()) throw new Exception("List cannot be empty.");
		
		String[] strVals = nodes.split(" ");
		int[] intVals = new int[strVals.length];
		
		if(intVals.length == 0) throw new Exception("List cannot be empty.");
		
		for(int i = 0; i < strVals.length; i++) {
			intVals[i] = Integer.parseInt(strVals[i]);
		}
		
		insertNodes(intVals);
		return;
	}
	
	public void insertNodes(int[] nodes) {
		/* Works backwards for O(1) time per insertion instead of O(N). */
		for(int i = nodes.length - 1; i >= 0; i--) {
			prependNode(nodes[i]);
		}
		return;
	}

	public Node getNodeAtIndex(int index) {
		if(head == null) return null;
		Node temp = head;
		while(index != 0) {
			index--;
			temp = temp.next;
		}
		return temp;
	}
	
	public void reset() {
		head = null;
		return;
	}
	
	public int length() {
		if(head == null) return 0;
		Node temp = head;
		int length = 0;
		while(temp != null) {
			length++;
			temp = temp.next;
		}
		return length;
	}
	
	public void generateSampleLinkedList(int size) {
		head = null;
		int[] nodes = new int[size];
		for(int i = 0; i < size; i++) {
			nodes[i] = h.random.nextInt(99);
		}
		insertNodes(nodes);
		return;
	}
	
	public void generateSampleLinkedList(int size, int maxVal) throws Exception {
		if(size <= 0) throw new Exception("Illegal bounds, a linked list cannot contain fewer than 1 element");
		head = null;
		int[] nodes = new int[size];
		for(int i = 0; i < size; i++) {
			nodes[i] = h.random.nextInt(maxVal+1);
		}
		insertNodes(nodes);
		return;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		if(head == null) return "List contains no nodes.";
		
		str.append(head.value + " => ");
		if(head.next == null) return str.delete(str.length() - 4, str.length()).toString();
		
		Node curr = head;
		while(curr.next != null) {
			str.append(Integer.toString(curr.next.value) + " => ");
			curr = curr.next;
		}
		return str.delete(str.length() - 4, str.length()).toString();
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
}
