// Author: Cong Li
// Practiced on 4/4/2013
//
// Have the function ArrayAdditionI(arr) take the array of numbers stored in arr 
// and return the string true if any combination of numbers in the array can be 
// added up to equal the largest number in the array, otherwise return the string false. 
// For example: if arr contains [4, 6, 23, 10, 1, 3] the output should return true 
// because 4 + 6 + 10 + 3 = 23. The array will not be empty, will not contain all 
// the same elements, and may contain negative numbers. 
// 
// ============================================================
// 1. Since there might be negative numbers, traditional knapsack algorithm will
//    not succeed.
// 2. The strategy is to minimize every element (except the maximum) with the minimum minus one
//    For example: -4 2 7 8 9 becomes 1, 7, 12, 13, 9.
// 3. Let's denote "target = 9" and "min_val = 5" (abs(-4-1)). 
// 3. Let dp[i][j] represent the number of elements that are needed to have the sum j, and
//    array[i] must be used.
// 4. Then we need to see if dp[row][target + i*min_val] = i, for all possible i and row.
//    This is because when we have two numbers sum up to target (2+7=9), then since we have
//    added 2 and 7 by 5, its summation will be 9 + 2*5.
// 5. If we only need to determine if there is a combination that sum to the target without
//    needing to specify which numbers sum to the target, then dp[i][j] does not have to
//    use array[i].

import java.util.Arrays;

public class ArrayCombineToMaximum {

	public static void main(String[] args) {
		int n = 5;
		int[] array = new int[n];
		for(int i = 0; i < n; ++i)
			array[i] = (int)(Math.random()*20 - 5);
		Arrays.sort(array);
		
		for(Integer a : array)
			System.out.print(a + " ");
		System.out.println();
		
		ArrayCombineToMaximum test = new ArrayCombineToMaximum();
		test.solve(array);
	}

	public void solve(int[] array)
	{
		int min_val = array[0] < 0 ? -array[0]+1 : 0;
		int target = array[array.length-1];
		
		if(min_val > 0)
			for(int i = 0; i < array.length-1; ++i)
				array[i] += min_val;
		
		int[][] dp = new int[array.length-1][target + min_val * (array.length-1) +1];
		dp[0][array[0]] = 1;
		
		for(int row = 1; row < array.length-1; ++row)
		{
			for(int col = 0; col < dp[0].length; ++col)
			{
				if(dp[row-1][col] > 0 && (col + array[row]) < dp[0].length)
					dp[row][col+array[row]] = dp[row-1][col] + 1;
			}
			if(dp[row][array[row]] == 0)
				dp[row][array[row]] = 1;
		}
		if(min_val == 0)
		{
			if(dp[array.length-2][target] != 0)
			{
				retrieve(dp, array, array.length-2, array[array.length-1], 0);
				return;
			}
		}
		else
		{
			for(int row = array.length-2; row >= 0; --row)
			{
				int i = 1;
				while(i < array.length-1 && dp[row][target + i*min_val] != i)
				{
					++i;
				}
				if(i != array.length-1)
				{
					retrieve(dp, array, row, target + i*min_val, min_val);
					return;
				}
			}
		}
		System.out.println("Impossible!");
	}
	
	public boolean retrieve(int[][] dp, int[] array, int row, int target, int min_val)
	{
		if(target == 0)
		{
			return true;
		}
		
		for(int r = row; r >= 0; --r)
		{
			if(dp[r][target] == 0) continue;
			if(retrieve(dp, array, r-1, target-array[r], min_val))
			{
				System.out.print(array[r]-min_val + " ");
				return true;
			}
		}
		return false;
	}
}
