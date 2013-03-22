// Author: Cong Li
// Practiced on 3/21/2013
// https://www.hackerrank.com/challenges/stockmax
// 
// ===============================
// Be careful of overflow.

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class StockMaximizeHackerrank {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num_tests = sc.nextInt();
        for(int i = 0; i < num_tests; ++i)
        {
            int N = sc.nextInt();
            int[] array = new int[N];
            for(int j = 0; j < N; ++j)
                array[j] = sc.nextInt();
            calculate(array, N);
        }
    }
    
    private static void calculate(int[] array, int N)
    {
        int[] right = new int[N];
        right[N-1] = array[N-1];
        for(int i = N-2; i >= 0; --i)
			right[i] = Math.max(right[i+1], array[i]);
        
        long result = 0;
        for(int i = 0; i < N; ++i)
            result += Math.max(0, right[i]-array[i]);
        System.out.println(result);
    }
}