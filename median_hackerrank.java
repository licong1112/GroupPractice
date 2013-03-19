// Author: Cong Li
// Practiced on 3/18/2013
// https://www.hackerrank.com/challenges/median
// 
// =================================
// The basic idea is to keep two heaps, one max-heap, one min-heap.
// For a given sorted array a[0], ..., a[n], always keep a[0]...a[n/2]
// in the max-heap, so that a[n/2] is at the peek of the max-heap.
// Also, keep a[n/2+1]...a[n] in the min-heap.
// 
// When n is odd, there are even number of elements in a. The median is
// (min-heap.peek()+max-heap.peek())/2. 
// When n is even, there are odd number of elements in a. The median is
// min-heap.peek();
// 
// Be careful!! The test set contains negative numbers. Don't print as
// (int)(a+b)/2 + ".5". If you do so, when a = -1 and b = 0, you will get
// 0.5 instead of -0.5.

import java.util.*;

class median_hackerrank{
   public static void main( String args[] ){
       Scanner in = new Scanner(System.in);
       int N;
       N = in.nextInt();
       String s[] = new String[N];
       int x[] = new int[N];
       for(int i=0; i<N; i++){
          s[i] = in.next();
          x[i] = in.nextInt();
       }
       
       Comparator<Integer> cp = new SmallPQComparator();
       PriorityQueue<Integer> small = new PriorityQueue<Integer>(10, cp); // max-heap
       PriorityQueue<Integer> large = new PriorityQueue<Integer>(); // min-heap
       HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
       for(int i = 0; i < N; ++i)
       {
           if(s[i].equals("a"))
           {
               if(map.containsKey(x[i]))
                   map.put(x[i], map.get(x[i])+1);
               else
                   map.put(x[i], 1);
               
               if(small.size() == 0)
            	   small.add(x[i]);
               else
               {
                   if(x[i] <= small.peek())
                   {
                       small.add(x[i]);
                       if((small.size()+large.size()) % 2 == 0)
                    	   large.add(small.poll());
                   }
                   else
                   {
                       large.add(x[i]);
                       if((small.size()+large.size()) % 2 == 1)
                    	   small.add(large.poll());
                   }
               }
           }
           else
           {
               if(small.size() == 0)
               {
                   System.out.println("Wrong!");
                   continue;
               }
               else
               {
                   if(!map.containsKey(x[i]) || map.get(x[i]) == 0)
                   {
                       System.out.println("Wrong!");
                       continue;
                   }
                   if(x[i] <= small.peek())
                   {
                       small.remove(x[i]);
                       if((small.size()+large.size()) % 2 == 1 && large.size() > 0)
                    	   small.add(large.poll());
                   }
                   else
                   {
                       large.remove(x[i]);
                       if((small.size()+large.size()) % 2 == 0 && small.size() > 1)
                    	   large.add(small.poll());
                   }
                   map.put(x[i], map.get(x[i])-1);
               }
           }
           if(small.size()+large.size() == 0)
        	   System.out.println("Wrong!");
           else
           {
        	   if((small.size()+large.size()) % 2 == 1)
                   System.out.println(small.peek());
               else
               {
                   double temp = small.peek() + (double)large.peek(); 
                   if(temp % 2 != 0)
                       System.out.format("%.1f%n", (temp/2));
                   else
                       System.out.println((int)(temp/2));
               }
                   
           }
       }
   }
}

class SmallPQComparator implements Comparator<Integer>
{
	@Override
	public int compare(Integer a, Integer b) {
		// TODO Auto-generated method stub
		if(a > b)
			return -1;
		if(a < b)
			return 1;
		return 0;
	}
}