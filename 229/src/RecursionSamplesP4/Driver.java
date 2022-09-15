package RecursionSamplesP4;

public class Driver {

	public static void main(String[] args) {
		// count(10);
		// The fib numbers are 1 1 2 3 5 8 13 21 34 55
		int fibby = fib(1, 1, 0);
		System.out.println("fibby: " + fibby);
		int nth = 5;
		int n = nthFibNumber(nth, 0);
		System.out.println("The " + nth + "th fib number is: " + n);
	}

	/**
	 * By counting to 10 with recursion we can watch our recursive calls get pushed
	 * and popped onto the execution stack. You can count forwards or backwards
	 * depending on if you are printing before the next generation is pushed or
	 * after it's popped!
	 * 
	 * If the recursive statement is the last statement we call this "tail"
	 * recursion. The alternative is usually "recursion" but sometimes people will
	 * call it "non-tail"
	 * 
	 * This function can easily be "tail" recursion: just make return count(n-1) the
	 * last line.
	 * 
	 * @param n
	 * @return
	 */
	public static int count(int n) {
		if (n == 0) {
			return n;
		}
		System.out.println("pushed another count on the stack at n=" + n);
		int k = count(n - 1);
		System.out.println("popped a count off the stack at n=" + n);
		return k;
	}

	/**
	 * Getting the nth fib number is another interesting application of recursion:
	 * the depth parameter here is too demonstrate how deep the stack depth has
	 * gone, e.g. how many layers of functions are on the stack. You don't need it
	 * for anything functionally, it's just for demonstration
	 * 
	 * @param n
	 * @param depth
	 * @return
	 */
	public static int nthFibNumber(int n, int depth) {
		if (n <= 1) {
			return n;
		}
		System.out.println("sum of " + (n - 1) + " and " + (n - 2) + " = " + (n - 1 + n - 2)
				+ " at depth " + depth);

		int sum = nthFibNumber(n - 1, depth + 1) + nthFibNumber(n - 2, depth + 1);
		return sum;
	}

	/**
	 * Printing fib numbers up to some stopping point can easily be non without
	 * recursion, but here's an example to do it with recursion anyways.
	 * 
	 * This algorithm digs deeper than the previous one, but has way fewer function
	 * calls. Just try to run the other function for a depth of 10 or more!
	 * 
	 * To get the nth fib number, we could just have the stopping condition change
	 * to stop at a certain depth instead of a numerical threshold
	 *
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int fib(int a, int b, int depth) {
		int c = a + b;
		System.out.println("a " + a + " b " + b + " c " + c + " depth " + depth);
		a = b;
		b = c;
		if (c < 1000) {
			c = fib(a, b, depth + 1);
		}
		return c;
	}

	/**
	 * This gives us the nth fib number, starting at depth (so for the 15th fib
	 * number, start with depth = 1 or depth = 0, depending on what you consider to
	 * be the "15th" number (some people include a 0 at the start of the sequence
	 * too, which makes it more annoying, but that's just semantics)
	 * 
	 * Escaping at some max depth is a common recursive tool and is often a lot
	 * easier than determining an escape condition otherwise
	 * 
	 * @param a
	 * @param b
	 * @param depth
	 * @param nth
	 * @return
	 */
	public static int fibStopAtNth(int a, int b, int depth, int nth) {
		int c = a + b;
		System.out.println("a " + a + " b " + b + " c " + c + " depth " + depth);
		a = b;
		b = c;
		if (depth <= nth - 1) {
			c = fibStopAtNth(a, b, depth + 1, nth);
		}
		return c;
	}

}
