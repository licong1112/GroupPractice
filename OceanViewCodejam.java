// Author: Cong Li
// Practiced on 3/24/2013
// https://code.google.com/codejam/contest/dashboard?c=2334486#s=p2
//
// =========================================
// DP is used. dp_cut[i] represents the minimal number of cut if array[i] is
// is cut. dp_no_cut[i] is similarly represented.
// 
// Obviously, dp_cut[i] = min{dp_cut[i-1], dp_no_cut[i-1]} + 1;
// To calculate dp_no_cut[i], we need to continuously consider all j's (with 0 <= j < i), which has
// array[j] < array[i], and see if we keep array[j] and cut all the numbers between
// i and j, how many cuts are needed.
// So, dp_no_cut[i] = min(dp_no_cut[j] + i-j-1), for all 0<=j<1 and array[j] < array[i].
// 
// This problem can be solved via finding longest increasing subsequence, which has
// O(NlogN) time complexity. Check here:
// http://en.wikipedia.org/wiki/Longest_increasing_subsequence



import java.util.*;

public class OceanViewCodejam{

	public static void main(String[] args) {
		int[] array = {1, 5, 6, 2, 3, 4}; //Correct answer: 2 (you need to cut 5 and 6)
		calculate(array, 1);
	}
	
	private void calculate(int[] array, int case_num)
	{
		int[] dp_cut = new int[array.length]; // Solution if array[i] is cut
		int[] dp_no_cut = new int[array.length]; // Solution if array[i] is not cut
		dp_cut[0] = 1;
		dp_no_cut[0] = 0;
		
		for(int i = 1; i < array.length; ++i)
		{
			dp_cut[i] = Math.min(dp_cut[i-1], dp_no_cut[i-1]) + 1;
			
			// Calculate dp_no_cut[i].
			int j = i-1;
			int no_cut_temp = Integer.MAX_VALUE;
			while(j >= 0)
			{
				if(array[j] < array[i])
					no_cut_temp = Math.min(no_cut_temp, dp_no_cut[j] + i-j-1);
				--j;
			}
			no_cut_temp = Math.min(no_cut_temp, i);
			dp_no_cut[i] = array[i] > array[i-1] ? Math.min(dp_no_cut[i-1], no_cut_temp) : no_cut_temp;
		}
		
		System.out.println("Case #" + case_num + ": " + Math.min(dp_cut[array.length-1], dp_no_cut[array.length-1]));
	}
}