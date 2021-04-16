package util;

public class IntLinkedList {
	
	public Node head = null;
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		if(head == null) return "List contains no nodes.";
		
		str.append(head.value + " => ");
		if(head.next == null) return str.toString();
		
		Node curr = head;
		while(curr.next != null) {
			str.append(Integer.toString(curr.next.value) + " => ");
			curr = curr.next;
		}
		return str.toString();
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public void appendNode(int val) {
		Node newNode = new Node(val);
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
}
