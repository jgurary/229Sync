package MidtermSolutions;

public class Node {

	Node next;
	Node previous;
	boolean isVisited = false;
	char value;

	public Node(char value) {
		this.value = value;
	}

	/**
	 * Returns the head of a demo list with a cycle that looks like:
	 * 
	 * A <> B <> C > B
	 * 
	 * @return
	 */
	public static Node buildDemoListBad() {
		Node A = new Node('A');
		Node B = new Node('B');
		Node C = new Node('C');
		A.next = B;
		B.next = C;
		B.previous = A;
		C.next = B;
		C.previous = B;
		// You might update B.previous = C, or not.
		return A;
	}

	/**
	 * Returns the head of a demo list with no cycles
	 * 
	 * @return
	 */
	public static Node buildDemoListGood() {
		Node A = new Node('A');
		Node B = new Node('B');
		Node C = new Node('C');
		A.next = B;
		B.next = C;
		B.previous = A;
		C.previous = B;
		return A;
	}

}
