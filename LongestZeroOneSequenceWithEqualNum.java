/**
 * Practiced on 7/21/2013
 * 
 * Problem: Given an array that contains only 0 or 1. Find the longest subsequence/subarray
 *          that have equal number of 0's and 1's.
 *          For example: "1001010", longest subsequence: "101010", longest subarray: "1010"
 */

import java.util.Stack;

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
		int[] test9 = {1,1,1,1};
		int[] test10 = {1,0,1,0,0,0,1,0,1,0,1,0,1};
		int[] test11 = {1,0,1,1,0,0};
		
		System.out.println("Test subsequence algorithm:");
		test.calculate_subsequence(test1);
		test.calculate_subsequence(test2);
		test.calculate_subsequence(test3);
		test.calculate_subsequence(test4);
		test.calculate_subsequence(test5);
		test.calculate_subsequence(test6);
		test.calculate_subsequence(test7);
		test.calculate_subsequence(test8);
		test.calculate_subsequence(test9);
		test.calculate_subsequence(test10);
		test.calculate_subsequence(test11);
		
		System.out.println("\nTest subarray algorithm:");
		test.calculate_subarray(test1);
		test.calculate_subarray(test2);
		test.calculate_subarray(test3);
		test.calculate_subarray(test4);
		test.calculate_subarray(test5);
		test.calculate_subarray(test6);
		test.calculate_subarray(test7);
		test.calculate_subarray(test8);
		test.calculate_subarray(test9);
		test.calculate_subarray(test10);
		test.calculate_subarray(test11);
	}
	
	// Use stack. Keep pushing until current digit is different to the stack's peek.
	// Be careful of the case "101100", the "10" detected in the first round needs to
	// be merged in "1100" that is detected in the second round.
	public void calculate_subarray(int[] array) {
		Stack<Integer> stack = new Stack<Integer>();
		int max_length = -1, length = -1, new_length = -1, start = 0, end = -1, new_start = -1, new_end = -1;
		int opt_start = -1, opt_end = -1;
		stack.push(array[0]);
		int i = 1;
		while (i < array.length) {
			if (stack.empty() || array[i] == stack.peek()) {
				stack.push(array[i]);
				++i;
			} else {
				new_length = 0;
				while (!stack.empty() && i < array.length && array[i] != stack.peek()) {
					stack.pop();
					new_length += 2;
					++i;
				}
				new_end = i-1;
				new_start = new_end - new_length + 1;
				if (end == new_start-1) { // in case of "101100", new_start=2, new_end=5, start=0, end=1,
					end = new_end;
					length += new_length;
				} else {
					start = new_start;
					end = new_end;
					length = new_length;
				}
				if (max_length < length) {
					opt_start = start;
					opt_end = end;
					max_length = length;
				}
			}
		}
		if (opt_start == opt_end) {
			System.out.println("No solution");
			return;
		}
		for (int j = opt_start; j <= opt_end; ++j) {
			System.out.print(array[j]);
		}
		System.out.println();
	}
	
	// Just count how many 0's and 1's are there. 
	public void calculate_subsequence(int[] array) {
		int count_zero = 0;
		int count_one = 0;
		for (int digit : array) {
			if (digit == 0) {
				++count_zero;
			} else {
				++count_one;
			}
		}
		if (count_zero == 0 || count_one == 0) {
			System.out.println("No solution");
			return;
		}
		int count = Math.min(count_zero, count_one);
		count_zero = 0; count_one = 0;
		for (int digit : array) {
			if ((digit == 0 && count_zero < count) || 
				(digit == 1 && count_one < count)){
				System.out.print(digit);
				if (digit == 0) {
					++count_zero;
				} else {
					++count_one;
				}	
		    }
		}
		System.out.println();
	}
}
