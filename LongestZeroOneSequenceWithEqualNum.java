/**
 * Problem: Given an array that contains only 0 or 1. Find the longest subsequence
 *          that have equal number of 0's and 1's.
 */

public class LongestZeroOneSequenceWithEqualNum {

	public static void main(String[] args) {
		LongestZeroOneSequenceWithEqualNum test = new LongestZeroOneSequenceWithEqualNum();
		
		int[] test1 = {1,0,1,0,1,0,1,0};
		int[] test2 = {1,1,0,1,0,0,0};
		int[] test3 = {1,1,1,1,0,0,0,0};
		int[] test4 = {1,0,0,0,1,0};
		int[] test5 = {1,0,1,0,0,1,0};
		int[] test6 = {1,0};
		int[] test7 = {1};
		int[] test8 = {1,1,0,1,0};
		
		test.calculate(test1);
		test.calculate(test2);
		test.calculate(test3);
		test.calculate(test4);
		test.calculate(test5);
		test.calculate(test6);
		test.calculate(test7);
		test.calculate(test8);
	}
	
	public void calculate(int[] array) {
		int count_zero = 0;
		int count_one = 0;
		for (int digit : array) {
			if (digit == 0) {
				++count_zero;
			} else {
				++count_one;
			}
		}
		int count = Math.min(count_zero, count_one);
		count_zero = 0; count_one = 0;
		for (int digit : array) {
			if (digit == 0 && count_zero < count) {
				System.out.print(digit + " ");
		    	++count_zero;
		    } else if (digit == 1 && count_one < count){
		    	System.out.print(digit + " ");
		    	++count_one;
		    }
		}
		System.out.println();
	}
}
