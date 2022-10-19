package MidtermSolutions;

public class TreeNode {

	TreeNode left;
	TreeNode right;
	TreeNode parent;
	int value;

	public TreeNode(int value) {
		this.value = value;
	}

	/**
	 * A demo tree that starts with:
	 * 
	 * root 5 with left 4, right 7
	 * 
	 * Left of 4 is 2
	 * 
	 * Left of 7 is 6
	 * 
	 * @return
	 */
	public static TreeNode demoTree() {
		TreeNode A = new TreeNode(5);
		TreeNode B = new TreeNode(4);
		TreeNode C = new TreeNode(7);
		TreeNode D = new TreeNode(2);
		TreeNode E = new TreeNode(6);
		// @formatter:off
		/* Looks like this:
		 *     5
		 *   4   7
		 * 2    6
		 * 
		 */
		// @formatter:on
		A.left = B;
		B.parent = A;
		B.left = D;
		D.parent = B;
		A.right = C;
		C.parent = A;
		C.left = E;
		E.parent = C;
		return A;
	}

}
