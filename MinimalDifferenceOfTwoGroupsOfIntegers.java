/**
 * Practiced on 4/15/2013
 * 
 * Given an array of integers int[] array, separate it into two groups, such that the difference
 * between the summation of the integers in each group is minimized. Report the (absolute value of the) 
 * minimal difference.
 * 
 * For example, {1, 2, 3, 4} gives result 0, 
 * 				{2, 3, 5, 12, 13} gives result 1 (5, 13 as a group and the others as another group)
 * 				{12, 30, 30, 32, 42, 49} gives result 9 (30, 30, 42 as a group and the others as another group)
 * 				{-12, -1, 3} gives result 8 (-12, 3 as a group, and -1 as another group).
 * 
 * =========================================================
 * DP is applied.
 * 
 * Let dp[i][j] represents the sum of group which gives the smaller summation, when its difference
 * with the sum of the other group has absolute value j, when considering the first i numbers.
 * 
 * If dp[i][j] = -infinity, it means the first i elements cannot be put into two groups, such that
 * the difference is j.
 * 
 * Then, for each dp[i][j] != -infinity, we know that within the two groups of the first i elements,
 * the one which gives the smaller summation has the sum dp[i][j]. Let's call it group A.
 * Then consider array[i+1]. It may be put into group A or group B (the group with larger summation).
 * For each situation, we will give a new group A and group B, and a new difference. So we record
 * the new difference in the array dp[i+1][].
 * 
 * After calculating dp[][], we have already known all possible differences that can be produced by
 * the given array. So we just simply give the smallest difference.
 */

package com.congli.codeforces;

import java.util.Arrays;

public class MinimalDifferenceOfTwoGroupsOfIntegers {

	public static void main(String[] args) {
		MinimalDifferenceOfTwoGroupsOfIntegers test = new MinimalDifferenceOfTwoGroupsOfIntegers();
		int[] array = {13, 2, 3, 5, 12};
		test.Calculate(array);
	}
	
	public void Calculate(int[] array)
	{
		int n = array.length;
		int sum = 0;
		for(int i = 0; i < n; ++i)
			sum += Math.abs(array[i]); // The maximum difference will not surpass "sum".
		
		int[][] dp = new int[n][sum+1];
		for(int i = 0; i < n; ++i)
			Arrays.fill(dp[i], Integer.MIN_VALUE); // If dp[i][j] == Integer.MIN_VALUE, it means the first i elements cannot
												   // be put into two groups, such the difference is j.
		dp[0][Math.abs(array[0])] = 0; 
		
		int partial_sum = array[0];
		for(int i = 1; i < n; ++i)
		{
			partial_sum += array[i]; // the sum of the first i elements
			for(int diff = 0; diff <= sum; ++diff)
			{
				if(dp[i-1][diff] != Integer.MIN_VALUE) 
				{
					// array[i] join the group which has smaller summation
					int sum_small = dp[i-1][diff] + array[i];
					int sum_large = partial_sum - sum_small;
					dp[i][Math.abs(sum_large - sum_small)] = Math.min(sum_large, sum_small);
					
					// array[i] join the group which has larger summation
					sum_small = dp[i-1][diff];
					sum_large = partial_sum - sum_small;
					dp[i][Math.abs(sum_large - sum_small)] = Math.min(sum_small, sum_large);
				}
			}
		}
		
		// After the double for-loop, we have already calculated all possible differences
		// Now, find the one which has the smallest difference.
		int result = 0;
		while(dp[n-1][result] == Integer.MIN_VALUE)
			++result;

		System.out.println(result);
	}

}
