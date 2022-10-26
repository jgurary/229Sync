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

}
