package BigOScratchSheet;

public class Driver {

	public static void main(String[] args) {

		Student students[] = new Student[10];
		int sum = 0;

		// Worst case? Average?
		// What is the performance in terms of how many items we are dealing with?

		// Constant time
		// Administering an exam
		// embarssingly parralleisizeable
		// Automated grading
		// Accessing a value in an array by index arr[5] = arr[9999999]
		// O(1) or O(k)
		// one second vs one million years, same thing = O(1)
		// Add two numbers together 4+5 vs 45634563 + 745675467

		// Log time O(logn)
		// Find a word in the dictionary (binary search)

		// Linear time
		// Grading the exam (some time * n students)
		// Sum of all the phone numbers of everyone in the classroom
//		for (int i = 0; i < students.length; i++) {
//			sum += students[i].phoneNumber;
//		}
		// Sum of all the phone numbers of everyone in the classroom and their
		// biological parents
//		for (int i = 0; i < students.length; i++) {
//			sum += students[i].phoneNumber;
//			sum += students[i].mothersNumber;
//			sum += students[i].fathersNumber;
//		}
		// O(N) vs O(3N) which is just = O(N)
		// Let's get the sum of phone number for all classrooms at the university
		// Classrooms > number of students
//		for (int k = 0; k < classrooms.length; k++) {
//			for (int i = 0; i < students.length; i++) {
//				sum += students[i].phoneNumber;
//				sum += students[i].mothersNumber;
//				sum += students[i].fathersNumber;
//			}
//		}
		// this is actually O(N^2), which is quadratic

		// N Log N time O(nlogn)
		// Find n words in the dictionary (doing binary search n times)

		// Quadratic Time
		// For every-x-find-every-y
		// Sometimes mistakenly called exponential, but exponential time is actually
		// O(C^N)
		// If you had three nested loops, like for{ for { for -> O(N^3)

		// Factorial Time O(n!)

	}

}
