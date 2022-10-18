package HW4HeapSnekStarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HeapTest {

	@Test
	void heap_happy_path() {
		Heap heap = new Heap(100);

		heap.insert(new Node(10));
		heap.insert(new Node(20));
		heap.insert(new Node(5));
		heap.insert(new Node(3));
		heap.insert(new Node(25));

		// Smallest value is the root
		assertEquals(heap.peekMin(), 3);

		// Remove smallest values in order...
		Node removed = heap.getMin();
		assertEquals(removed.value, 3);
		removed = heap.getMin();
		assertEquals(removed.value, 5);
		removed = heap.getMin();
		assertEquals(removed.value, 10);
		removed = heap.getMin();
		assertEquals(removed.value, 20);
		removed = heap.getMin();
		assertEquals(removed.value, 25);

		heap.insert(new Node(7));
		heap.insert(new Node(9));
		Node two = new Node(2);
		heap.insert(two);

		// Sanity check the size is still accurate
		assertEquals(heap.size, 3);

		// Average is accurate (7+9+2)/3=6
		assertEquals(heap.getAverage(), 6);

		heap.insert(new Node(3));
		assertEquals(heap.peekMin(), 2);

		// TODO write a test that shows that the minimum changes when the value of 2 is
		// tripled (e.g., when your "rainbow" function runs and Node "two" is selected)
	}

	@Test
	void heap_exceptions() {
		Heap heap = new Heap(5);
		heap.insert(new Node(10));
		heap.insert(new Node(20));

		// Null index
		assertEquals(heap.peekIndex(4), -1);

		heap.insert(new Node(5));
		heap.insert(new Node(3));
		heap.insert(new Node(25));

		// Out of bounds
		assertEquals(heap.peekIndex(12), -1);

		// Insert when full, does nothing
		assertEquals(heap.size, 5);
		heap.insert(new Node(42));
		assertEquals(heap.size, 5);

		// Remove when empty, does nothing, returns null
		heap.getMin();
		heap.getMin();
		heap.getMin();
		heap.getMin();
		heap.getMin();
		heap.getMin();
		Node n = heap.getMin();
		assertEquals(n, null);
		assertEquals(heap.size, 0);

		heap.insert(new Node(5));
		assertEquals(heap.size, 1);

	}

}
