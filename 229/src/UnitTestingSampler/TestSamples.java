package UnitTestingSampler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TestSamples {

	@Test
	void array_sort_test() {
		int arr[] = { 3, 6, 5, 2, 1, 9, 4 };
		int expected[] = { 1, 2, 3, 4, 5, 6, 9 };
		Arrays.sort(arr);
		assertArrayEquals(expected, arr);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], arr[i]);
			assert (expected[i] == arr[i]);
		}

		String strings[] = { "cake", "Cake", "Bake", "Snake" };
		String expectedStrings[] = { "Bake", "Cake", "Snake", "cake" };
		Arrays.parallelSort(strings);
		assertArrayEquals(expectedStrings, strings);
		for (int i = 0; i < expectedStrings.length; i++) {
			assert (expectedStrings[i] == strings[i]);
		}
	}

	@Test
	void array_string_objects_sort_test() {
		String cake = "cake";
		String alsoCake = "cake";
		String upperCake = cake;
		upperCake = "Cake";
		String strings[] = { cake, upperCake, "Bake", "Snake" };
		String expectedStrings[] = { "Bake", "Cake", "Snake", alsoCake };
		Arrays.parallelSort(strings);
		assertArrayEquals(expectedStrings, strings);
		for (int i = 0; i < expectedStrings.length; i++) {
			assert (expectedStrings[i] == strings[i]);
		}
	}

	@Test
	void divide_by_zero_exception() {
		String s = "sandwhich";
		boolean succeeded = false;
		try {
			int a = s.length() / 0;
			succeeded = true;
		} catch (Exception e) {
			assert (true);
		}
		assert (!succeeded);

		Exception exception = assertThrows(Exception.class, () -> {
			int a = s.length() / 0;
		});
		assertEquals("/ by zero", exception.getMessage());
	}

	@Test
	void string_upper_lower_test_happy_path() {
		String s = "sandwhich";
		String expected = "SANDWHICH";
		String actual = s.toUpperCase();
		assertEquals(expected, actual);

		expected = s;
		actual = s.toLowerCase();
		assertEquals(expected, actual);
	}

	@Test
	void string_contains_test() {
		String s = "sandwhich";
		String toCheck = "sand";
		assert (s.contains(toCheck));
		toCheck = "which";
		assert (s.contains(toCheck));
		toCheck = "SAND";
		assert (!s.contains(toCheck));
		toCheck = "asdfsdfsdf";
		assert (!s.contains(toCheck));

		// Every String contains the empty string ("")
		toCheck = "";
		assert (s.contains(toCheck));

		Exception exception = assertThrows(java.lang.NullPointerException.class, () -> {
			String check = null;
			String s2 = "sandwhich";
			boolean contains = s2.contains(check);
		});
	}

	@Test
	void string_out_of_bounds() {
		int toTest = 15;
		Exception exception = assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {
			String s = "sandwhich";
			char c = s.charAt(toTest);
		});
		assertEquals("String index out of range: " + toTest, exception.getMessage());
	}

	@Test
	void selection_sort_happy_path() {
		int[] toSort = { 7, 4, 12, 8, 9, 2, 4 };
		int[] expected = { 2, 4, 4, 7, 8, 9, 12 };
		Sorts sorter = new Sorts();
		sorter.selectionSort(toSort);
		assertArrayEquals(toSort, expected);
	}

	@Test
	void selection_sort_floats_happy_path() {
		float[] toSort = { 2.021f, 1.08f, .1f, 0f };
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] += .00001;
		}
		float[] expected = { 0.00001f, .10001f, 1.08001f, 2.02101f };
		Sorts sorter = new Sorts();
		sorter.selectionSortFloats(toSort);
		for (int i = 0; i < toSort.length; i++) {
			assertEquals(expected[i], toSort[i], .0001f);
		}
	}

	@Test
	void selection_sort_negative_numbers() {
		int[] toSort = { 0, 1, -5, -9, -7, 2 };
		int[] expected = { -9, -7, -5, 0, 1, 2 };
		Sorts sorter = new Sorts();
		sorter.selectionSort(toSort);
		assertArrayEquals(toSort, expected);
	}

	@Test
	void selection_sort_empty() {
		int[] toSort = {};
		int[] expected = {};
		Sorts sorter = new Sorts();
		sorter.selectionSort(toSort);
		assertArrayEquals(toSort, expected);
	}

	@Test
	void merge_sort_happy_path() {
		int[] toSort = { 3, 7, 2, 1, 9, 9, 5 };
		int[] expected = { 1, 2, 3, 5, 7, 9, 9 };
		Sorts sorter = new Sorts();
		sorter.mergeSort(toSort, 0, toSort.length - 1);
		assertArrayEquals(toSort, expected);
	}

	@Test
	void merge_sort_partial_sort() {
		int[] toSort = { 3, 7, 2, 1, 9, 9, 5 };
		int[] expected = { 1, 2, 3, 7, 9, 9, 5 };
		Sorts sorter = new Sorts();
		sorter.mergeSort(toSort, 0, toSort.length - 3);
		assertArrayEquals(toSort, expected);

		int[] toSortLeft = { 3, 7, 2, 1, 9, 9, 5 };
		int[] expectedLeft = { 3, 7, 1, 2, 5, 9, 9 };
		sorter.mergeSort(toSortLeft, 2, toSortLeft.length - 1);
		assertArrayEquals(toSortLeft, expectedLeft);
	}

	@Test
	void merge_sort_edge_cases() {
		int[] toSort = {};
		int[] expected = {};
		Sorts sorter = new Sorts();
		sorter.mergeSort(toSort, 0, toSort.length - 1);
		assertArrayEquals(toSort, expected);

		int[] oneItem = { 5 };
		int[] expectedOneItem = { 5 };
		sorter.mergeSort(oneItem, 2, oneItem.length - 1);
		assertArrayEquals(oneItem, expectedOneItem);
	}

	@Test
	void merge_sort_items_is_stable() {
		Item A = new Item(5);
		Item B = new Item(5);
		Item C = new Item(5);
		Item D = new Item(5);
		Item E = new Item(1);
		Item[] toSort = { A, B, C, D, E };
		Item[] expected = { E, A, B, C, D };
		Sorts sorter = new Sorts();
		sorter.mergeSortItems(toSort, 0, toSort.length - 1);
		assertArrayEquals(toSort, expected);
	}

	@Test
	void selection_sort_not_stable() {
		Item A = new Item(5);
		Item B = new Item(5);
		Item C = new Item(5);
		Item D = new Item(5);
		Item E = new Item(1);
		Item[] toSort = { A, B, C, D, E };
		for (int i = 0; i < toSort.length; i++) {
			System.out.println(i + " " + toSort[i].color.getBlue());
		}
		Item[] expected = { E, B, C, D, A };
		Sorts sorter = new Sorts();
		sorter.selectionSortItems(toSort);
		for (int i = 0; i < toSort.length; i++) {
			System.out.println(i + " " + toSort[i].color.getBlue());
		}
		assertArrayEquals(toSort, expected);
	}

}
