/**
 * Practiced on 4/21/2013
 * 
 * For each people, he/she arrives at a bus station at a certain
 * time, and leaves at another certain time. Given a set of time
 * intervals that each person stays at the bus station, count how
 * many people are there, at the bus station, for each time spot.
 * 
 * ====================================================
 * This problem is used to practice interval tree.
 * 
 * For details of interval tree, check the following wikipedia page:
 * http://en.wikipedia.org/wiki/Interval_tree
 * 
 * Basically, each tree node contains the following key fields:
 * 
 * 1. center: the key of this node
 * 2. left: left child
 * 3. right: right child
 * 4. sort_left: a list that contains all intervals that overlap "center"
 *               and these intervals are sorted by their left endpoint
 * 5. sort_right: contains the same intervals as in sort_left, but are
 *                sorted by their right endpoint.
 *                
 * When inserting an interval, if its right endpoint is less than "center",
 * then insert it to its left node; or, if its left endpoint is larger than
 * "center", then insert it to its right node. This procedure is performed
 * recursively. Otherwise, this interval must overlap "center". So insert
 * it in sort_left and sort_right.
 * 
 * The "center" of each node is preferred to be chosen such that the tree
 * is well balanced. In the following implementation, every node maintains
 * a start and an end field, so the center is the average of start and end.
 * Thus when building the tree, we need to know the "start" and "end" of
 * the root node.
 * 
 * However, what if we don't know this information?
 */

import java.util.LinkedList;

class TreeNode
{
	int start;
	int end;
	int center;
	TreeNode left;
	TreeNode right;
	LinkedList<Interval> sort_left;
	LinkedList<Interval> sort_right;
	
	public TreeNode(int start, int end)
	{
		this.start = start;
		this.end = end;
		
		this.center = start + (end-start)/2;
		left = null;
		right = null;
		sort_left = new LinkedList<Interval>();
		sort_right = new LinkedList<Interval>();
	}
	
	public int query(int time_spot)
	{
		int result = 0;
		if(time_spot < center)
		{
			result += count_sort_left(time_spot);
			if(left != null)
				result += left.query(time_spot);
		}
		else if(time_spot > center)
		{
			result += count_sort_right(time_spot);
			if(right != null)
				result += right.query(time_spot);
		}
		else
			result += count_sort_left(time_spot);
		
		return result;
	}
	
	private int count_sort_left(int time_spot)
	{
		int result = 0;
		for(int i = 0; i < sort_left.size() && sort_left.get(i).left <= time_spot; ++i)
			++result;
		return result;
	}
	
	private int count_sort_right(int time_spot)
	{
		int result = 0;
		for(int i = sort_right.size()-1; i >= 0 && sort_right.get(i).right >= time_spot; --i)
			++result;
		return result;
	}
	
	public void insert(Interval interval)
	{
		if(interval.left > center)
		{
			if(right == null)
				right = new TreeNode(center+1, end);
			right.insert(interval);
		}
		else if(interval.right < center)
		{
			if(left == null)
				left = new TreeNode(start, center-1);
			left.insert(interval);
		}
		else
			insert_interval(interval);
	}
	
	private void insert_interval(Interval interval)
	{
		if(sort_left.size() == 0)
		{
			sort_left.add(interval);
			sort_right.add(interval);
			return;
		}
		
		int i = 0;
		while(i < sort_left.size() && sort_left.get(i).left <= interval.left)
		{
			if(sort_left.get(i).left == interval.left && sort_left.get(i).right > interval.right)
				break;
			++i;
		}
		sort_left.add(i, interval);
		
		i = 0;
		while(i < sort_right.size() && sort_right.get(i).right <= interval.right)
		{
			if(sort_right.get(i).right == interval.right && sort_right.get(i).left > interval.left)
				break;
			++i;
		}
		sort_right.add(i, interval);
	}
}

class Interval
{
	int left;
	int right;
	public Interval(int left, int right)
	{
		this.left = left;
		this.right = right;
	}
}

public class CountNumPeopleInTimeInterval {

	public static void main(String[] args) {
		
		// Test case =========================
		int[][] array = {{1, 3},  // 1 2 3
						 {2, 3},  //   2 3
						 {1, 2},  // 1 2
						 {2, 5},  //   2 3 4 5
						 {3, 4},  //     3 4
						 {4, 5}}; //       4 5
		
		                   // total: 2 4 4 3 2
		TreeNode root = new TreeNode(0, 6);
		for(int i = 0; i < array.length; ++i)
			root.insert(new Interval(array[i][0], array[i][1]));
			
		System.out.println(root.query(1) == 2);
		System.out.println(root.query(2) == 4);
		System.out.println(root.query(3) == 4);
		System.out.println(root.query(4) == 3);
		System.out.println(root.query(5) == 2);
		System.out.println(root.query(0) == 0);
		
		// End test case =======================
	}

}