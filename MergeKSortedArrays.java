// Author: Cong Li
// Practiced on 4/3/2013
//
// Given k sorted arrays, merge them to one sorted array.
//
// ============================================
// merge(int[][] list) uses min-heap
// mergeWithoutHeap(int[][] list, int K, int N) merge every two rows.
// 
// Both methods have time complexity O(k*n*logk).

import java.util.Arrays;
import java.util.PriorityQueue;

public class MergeKSortedArrays {

	public static void main(String[] args) {
		int k = 5;
		int n = 5;
		int[][] list = new int[k][n];
		for(int i = 0; i < k; ++i)
		{
			for(int j = 0; j < n; ++j)
				list[i][j] = (int)(Math.random()*100);
			Arrays.sort(list[i]);
			for(int j = 0; j < n; ++j)
				System.out.print(list[i][j] + " ");
			System.out.println();
		}
		System.out.println();
		
		MergeKSortedArrays test = new MergeKSortedArrays();
		test.merge(list);
		test.mergeWithoutHeap(list, k, n);
	}

	public void merge(int[][] list)
	{
		int k = list.length;
		int n = list[0].length;
		PriorityQueue<Pair> heap = new PriorityQueue<Pair>();
		
		for(int i = 0; i < k; ++i)
			heap.add(new Pair(list[i][0], i));
		
		int[] result = new int[k*n];
		int[] position = new int[k];

		for(int index = 0; index < result.length; ++index)
		{
			Pair curr_min = heap.poll();
			result[index] = curr_min.num;
			int curr_index = curr_min.index;
			if(position[curr_index] < n-1)
				heap.add(new Pair(list[curr_index][++position[curr_index]], curr_index));
		}
		
		for(Integer a : result)
			System.out.print(a + " ");
		System.out.println();
	}
	
	public void mergeWithoutHeap(int[][] list, int K, int N)
	{
		if(list.length == 1)
		{
			for(int i = 0; i < K*N; ++i)
				System.out.print(list[0][i] + " ");
			System.out.println();
			return;
		}
		
		int k = list.length;
		int n = list[0].length;
		int[][] new_list = new int[(k+1)/2][2*n];
		int row = 0;
		int col = 0;
		int i = 0;
		while(i < k-1)
		{
			int a = 0;
			int b = 0;
			while(a < n || b < n) // Merge row i and i+1.
			{
				if(a < n && b < n)
					new_list[row][col++] = list[i][a] < list[i+1][b] ? list[i][a++] : list[i+1][b++];
				else if(a < n)
					new_list[row][col++] = list[i][a++];
				else
					new_list[row][col++] = list[i+1][b++];
			}
			++row;
			col = 0;
			i += 2;
		}
		if(i == k-1) // If list has odd number of rows, the last row is simply copied to the new list.
		{
			Arrays.fill(new_list[row], Integer.MAX_VALUE);
			for(int j = 0; j < n; ++j)
				new_list[row][j] = list[k-1][j];
		}
		mergeWithoutHeap(new_list, K, N);
	}
	
	class Pair implements Comparable<Pair>
	{
		int num;
		int index;
		public Pair(int num, int index)
		{
			this.num = num;
			this.index = index;
		}
		public int compareTo(Pair a)
		{
			return this.num - a.num;
		}
	}
}
