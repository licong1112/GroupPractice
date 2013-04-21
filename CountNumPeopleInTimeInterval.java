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