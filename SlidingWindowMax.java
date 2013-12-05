/**
 * Practiced on 12/5/2013
 * 
 * http://leetcode.com/2011/01/sliding-window-maximum.html
 */

package group_practice;

import java.util.LinkedList;

public class SlidingWindowMax {
	public static void main(String[] args) {
		SlidingWindowMax test = new SlidingWindowMax();
		int size = 10;
		int[] array = new int[size];
		for (int i = 0; i < array.length; ++i) {
			array[i] = (int)(Math.random()*100 - 50);
		}
		
		int[] result = test.findMax(array, 3);
		int[] result_brute_force = test.findMaxBruteForce(array, 3);
		
		for (int i : result) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i : result_brute_force) {
			System.out.print(i + " ");
		}
	}
	
	public int[] findMax(int[] array, int width) {
		LinkedList<Integer> deque = new LinkedList<Integer>();
		deque.add(0);
		for (int i = 1; i < width-1; ++i) {
			if (array[i] <= array[deque.getLast()]) {
				deque.addLast(i);
			} else {
				deque.addFirst(i);
			}
		}
		
		int[] result = new int[array.length - width + 1];
		int result_index = 0;
		for (int i = width-1; i < array.length; ++i) {
			while (!deque.isEmpty() && array[i] > array[deque.getLast()]) {
				deque.removeLast();
			}
			deque.addLast(i);
			while (!deque.isEmpty() && deque.getFirst() <= i-width) {
				deque.removeFirst();
			}
			result[result_index++] = array[deque.getFirst()];
		}
		return result;
	}
	
	
	public int[] findMaxBruteForce(int[] array, int width) {
		int[] result = new int[array.length - width + 1];
		for (int i = 0; i+width-1 < array.length; ++i) {
			int max = Integer.MIN_VALUE;
			for (int j = i; j < i+width; ++j) {
				max = Math.max(max, array[j]);
			}
			result[i] = max;
		}
		return result;
	}
}
