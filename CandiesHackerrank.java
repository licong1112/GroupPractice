// Author: Cong Li
// Practiced on 3/21/2013
// https://www.hackerrank.com/challenges/candies
// 
// ====================================
// For a specific child, just find how many children have consecutively
// decreasing rating score to both left and right, and max(left, right)+1
// will be the number of candies of this child. 

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CandiesHackerrank {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num_children = sc.nextInt();
        int[] array = new int[num_children];
        for(int i = 0; i < num_children; ++i)
            array[i] = sc.nextInt();
        
        int[] left = new int[num_children];
        int[] right = new int[num_children];
        
        left[0] = 0;
        for(int i = 1; i < num_children; ++i)
            left[i] = array[i] > array[i-1] ? left[i-1]+1 : 0;
        
        right[num_children-1] = 0;
        for(int i = num_children-2; i >= 0; --i)
            right[i] = array[i] > array[i+1] ? right[i+1]+1 : 0;
        
        int result = num_children;
        for(int i = 0; i < num_children; ++i)
            result += Math.max(left[i], right[i]);
        
        System.out.println(result);
    }
}