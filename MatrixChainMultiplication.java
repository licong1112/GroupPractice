// Author: Cong Li
// Practiced on 4/10/2013
//
// Given a sequence of matrices, find the most efficient way to multiply these 
// matrices together. The problem is not actually to perform the multiplications, 
// but merely to decide in which order to perform the multiplications.
// 
// ===============================================
// Input: int[] array. Matrix i has dimension array[i]-by-array[i+1].
// 
// Note that in the following solution, dp[i][j] represents the best solution
// of considering array[i,...,j], which is the best solution of considering
// matrix[i] to matrix[j-1].


public class MatrixChainMultiplication {

	public static void main(String[] args) {
		
		MatrixChainMultiplication test = new MatrixChainMultiplication();
		int[] array = {10, 30, 5, 60, 10}; // A0:10*30, A1:30*5, A2:5*60, A3:60*10
		test.solve(array);
	}

	public void solve(int[] array)
	{
		int n = array.length;
		int[][] dp = new int[n][n];
		
		int temp = 0;
		int end = 0;
		for(int interval = 3; interval <= n; ++interval)
		{
			for(int start = 0; start <= n - interval; ++start)
			{
				temp = Integer.MAX_VALUE;
				end = start + interval - 1;
				
				for(int mid = start+1; mid < end; ++mid)
					temp = Math.min(temp, dp[start][mid] + dp[mid][end] + array[start]*array[mid]*array[end]);
				
				dp[start][end] = temp;
			}
		}
		System.out.println(dp[0][n-1]);
		System.out.println(print_sol(dp, array, 0, n-1));
	}
	
	public String print_sol(int[][] dp, int[] array, int start, int end)
	{
		if(start == end-1)
			return "A" + start;
		
		String result = "";
		for(int mid = start+1; mid < end; ++mid)
		{
			if(dp[start][end] == dp[start][mid] + dp[mid][end] + array[start]*array[mid]*array[end])
			{
				String left = print_sol(dp, array, start, mid);
				String right = print_sol(dp, array, mid, end);
				result = "(" + left + ")(" + right + ")";
			}
		}
		return result;
	}
}
