package MidtermSolutions;

public class Driver {

	public static void main(String[] args) {
		int a = recursivePowA(2, 4);
		System.out.println("(A) 2^4: " + a);

		int b = recursivePowB(2, 4);
		System.out.println("(B) 2^4: " + b);
		System.out.println();

		Node A = Node.buildDemoListBad();
		boolean hasCycle = detectCycleA(A);
		System.out.println("(A) Bad list has Cycle? " + hasCycle);

		A = Node.buildDemoListGood();
		hasCycle = detectCycleA(A);
		System.out.println("(A) Good list has Cycle? " + hasCycle);
		System.out.println();

		A = Node.buildDemoListBad();
		hasCycle = detectCycleB(A);
		System.out.println("(B) Bad list has Cycle? " + hasCycle);

		A = Node.buildDemoListGood();
		hasCycle = detectCycleB(A);
		System.out.println("(B) Good list has Cycle? " + hasCycle);
		System.out.println();

		TreeNode demoTreeRoot = TreeNode.demoTree();
		int range = getRange(demoTreeRoot);
		System.out.println("7-2 = range " + range);
		System.out.println();

		boolean exists = existsA(demoTreeRoot, 4);
		System.out.println("(A) 4 exists?  " + exists);
		exists = existsA(demoTreeRoot, 2);
		System.out.println("(A) 2 exists?  " + exists);
		exists = existsA(demoTreeRoot, 9);
		System.out.println("(A) 9 exists?  " + exists);

		System.out.println();
		exists = existsB(demoTreeRoot, 4);
		System.out.println("(B) 4 exists?  " + exists);
		exists = existsB(demoTreeRoot, 2);
		System.out.println("(B) 2 exists?  " + exists);
		exists = existsB(demoTreeRoot, 9);
		System.out.println("(B) 9 exists?  " + exists);
		System.out.println();

		System.out.println("Before delete");
		System.out.println("root  " + demoTreeRoot.value);
		System.out.println("root.left  " + demoTreeRoot.left.value);
		System.out.println("root.right  " + demoTreeRoot.right.value);
		System.out.println("root.left.left " + demoTreeRoot.left.left);
		System.out.println("root.right.left  " + demoTreeRoot.right.left);
		deleteLeaves(demoTreeRoot);
		System.out.println("After delete");
		System.out.println("root  " + demoTreeRoot.value);
		System.out.println("root.left  " + demoTreeRoot.left.value);
		System.out.println("root.right  " + demoTreeRoot.right.value);
		System.out.println("root.left.left (was leaf, should be null) " + demoTreeRoot.left.left);
		System.out
				.println("root.right.left  (was leaf, should be null) " + demoTreeRoot.right.left);
	}

	static int total = 1;

	/**
	 * This version requires a global variable, which is fine Most people will
	 * probably find this easier to conceptualize
	 * 
	 * @param base
	 * @param pow
	 * @return
	 */
	public static int recursivePowA(int base, int pow) {
		if (pow == 0) {
			return base;
		}
		pow--;
		total = total * base;
		recursivePowA(base, pow);
		return total;
	}

	/**
	 * This is the "real" recursive solution.
	 * 
	 * @param base
	 * @param pow
	 * @return
	 */
	public static int recursivePowB(int base, int pow) {
		if (pow != 0) {
			return (base * recursivePowB(base, pow - 1));
		} else {
			return 1;
		}
	}

	/**
	 * This version uses an extra variable. We set it to true on each Node as we
	 * visit them. If we run into one that was already set to true, we have a cycle.
	 * Technically you should reset them all to false when you're done, or it only
	 * works once!
	 * 
	 * It's O(N) only once, since it also costs N to reset the list, it's more like
	 * O(N) twice.
	 * 
	 * @param start
	 * @return
	 */
	public static boolean detectCycleA(Node start) {
		Node curr = start;
		while (curr != null) {
			if (curr.isVisited) {
				return true;
			}
			curr.isVisited = true;
			curr = curr.next;
		}
		return false;
	}

	/**
	 * The two-pointer solution is O(N), and it's popular online if you search for
	 * this kind of problem. Also probably the best answer to give to this question
	 * in an interview setting.
	 * 
	 * The fast pointer simply goes through the list twice as quickly, if there's a
	 * loop the two pointers will collide at some point
	 * 
	 * @param start
	 * @return
	 */
	public static boolean detectCycleB(Node start) {
		Node slow = start;
		Node fast = start;
		while (slow != null && fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}

	public static int getMin(TreeNode n) {
		while (n.left != null) {
			n = n.left;
		}
		return n.value;
	}

	public static int getMax(TreeNode n) {
		while (n.right != null) {
			n = n.right;
		}
		return n.value;
	}

	public static int getRange(TreeNode n) {
		return getMax(n) - getMin(n);
	}

	/**
	 * Recursive solution. Looks pretty much the same as the one with a while loop.
	 * 
	 * @param n
	 * @param val
	 * @return
	 */
	public static boolean existsA(TreeNode n, int val) {
		if (n.value == val) {
			return true;
		}
		if (n.value > val && n.left != null) {
			return existsA(n.left, val);
		} else if (n.value < val && n.right != null) {
			return existsA(n.right, val);
		} else {
			return false;
		}
	}

	/**
	 * Non-recursive.
	 * 
	 * @param n
	 * @param value
	 * @return
	 */
	public static boolean existsB(TreeNode n, int value) {
		TreeNode curr = n;
		while (curr != null) {
			if (value < curr.value) {
				curr = curr.left;
			} else if (value > curr.value) {
				curr = curr.right;
			} else if (value == curr.value) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Here's the exhaustive solution if you didn't figure out any tricks.
	 * 
	 * The easiest "trick" is to traverse the tree, add the Nodes to a list in
	 * order, all Nodes at size/2 or higher are leaves, remove them.
	 * 
	 * @param n
	 */
	public static void deleteLeaves(TreeNode n) {
		if (n.right == null && n.left == null) {
			System.out.println("Found leaf: " + n.value + " with parent " + n.parent);
			// Figure out which of the parent's children it is (left or right)
			if (n.parent.left != null && n.parent.left == n) {
				n.parent.left = null;
			} else {
				n.parent.right = null;
			}
		}

		if (n.left != null) {
			deleteLeaves(n.left);
		}
		if (n.right != null) {
			deleteLeaves(n.right);
		}

		return;
	}

}
