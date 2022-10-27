package UnitTestingSampler;

public class Sorts {

	void selectionSort(int arr[]) {
		int n = arr.length;
		//@formatter:off
		/*
		 * 1 2 4 3 6 4
		 *   i     ^
		 *           j
		 *       m
		 * 
		 */
		//@formatter:on

		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n - 1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[min_idx]) {
					min_idx = j;
				}
			}

			// Swap the found minimum element with the first
			// element
			int temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}

	void selectionSortItems(Item arr[]) {
		int n = arr.length;

		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n - 1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j].value < arr[min_idx].value) {
					min_idx = j;
				}
			}

			// Swap the found minimum element with the first
			// element
			Item temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}

	void selectionSortFloats(float arr[]) {
		int n = arr.length;
		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n - 1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[min_idx]) {
					min_idx = j;
				}
			}

			// Swap the found minimum element with the first
			// element
			float temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}

	void merge(int arr[], int left, int m, int right) {
		//@formatter:off
		/*
		 * 5 8 2 9 4 1 3
		 * 0 1 3 4 5 6 7 < indexes
		 * merge(arr[], 0, 1, 3)
		 * 
		 * 5 8 2 9
		 * 0 1 2 3 < indexes
		 * l m   r
		 * 
		 * n1: 2
		 * n2: 2
		 * 
		 * L: {5 8}
		 *          i
		 *     
		 * R: {2 9}
		 *       j
		 *     
		 * 2 5 8 9 4 1 3
		 *       k
		 */
		//@formatter:on
		// Find sizes of two subarrays to be merged
		int n1 = m - left + 1;
		int n2 = right - m;
		/* Create temp arrays */
		int L[] = new int[n1];
		int R[] = new int[n2];
		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[left + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		/* Merge the temp arrays */
		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = left;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}
		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	// Main function that sorts arr[l..r] using
	// merge()
	void mergeSort(int arr[], int l, int r) {
		//@formatter:off
		/*
		 * 8 5 2 9 4 1 3
		 * l     m     r
		 * 
		 * first half:
		 * 8 5 2 9
		 * l m	 r
		 * 
		 * second half:
		 * 4 1 3
		 * 
		 * recursion from first half:
		 * 'first half:
		 * 8 5
		 * l r
		 * m
		 * 
		 * 'second half:
		 * 2 9
		 * 
		 * 5 8 2 9 4 1 3
		 * 0 1 3 4 5 6 7 < indexes
		 * merge(arr[], 0, 1, 3)
		 */
		//@formatter:on
		if (l < r) {
			// Find the middle point
			int m = l + (r - l) / 2;

			// Sort first and second halves
			mergeSort(arr, l, m);
			mergeSort(arr, m + 1, r);

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}

	void mergeItems(Item arr[], int left, int m, int right) {
		int n1 = m - left + 1;
		int n2 = right - m;
		/* Create temp arrays */
		Item L[] = new Item[n1];
		Item R[] = new Item[n2];
		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[left + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		/* Merge the temp arrays */
		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = left;
		while (i < n1 && j < n2) {
			if (L[i].value <= R[j].value) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}
		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	void mergeSortItems(Item arr[], int l, int r) {
		if (l < r) {
			// Find the middle point
			int m = l + (r - l) / 2;

			// Sort first and second halves
			mergeSortItems(arr, l, m);
			mergeSortItems(arr, m + 1, r);

			// Merge the sorted halves
			mergeItems(arr, l, m, r);
		}
	}

}
