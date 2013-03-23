// Author: Cong Li
// Practiced on 3/22/2013
// https://www.hackerrank.com/challenges/vertical-sticks
// 
// ==================================================
// Consider each stick separately: on average, how many scores will it get?
// 
// Suppose array[1,...,N] is sorted. Some key observations:
// 1. array[i] can only achieve at most i scores.
// 2. For array[i] to achieve s score, we can only put it at position
//    s, s+1, ..., N. Also, when it is put on a specific position j,
//    its previous s-1 positions must have sticks that are shorter than itself,
//    and its previous s-th position must have sticks that are not shorter than
//    itself.
// 
// For example, consider array = [1, 2, 3, 4, 5, 6]. Let's calculate the probability
// that 4 get score 3.
// 
// When it's at position 3, then position 1 and 2 has to be shorter than 4. We have 
// to choose 2 from the 3 sticks that are shorter than 4. The prob. is 3/5 * 2/4.
// 
// When 4 is put at position 4, still, we need to choose 2 shorter sticks
// from the 3 (with prob. 3/5 * 2/4), and we need to choose a longer stick 
// to put at position 1, and this prob. is 2/3 (we have only 3 candidates now, and
// there are 2 of them are longer than 4), which gives the final prob. 
// 3/5 * 2/4 * 2/3.
// 
// When 4 is put at position 5 or 6, it's the same as putting it at position 4.
// So we don't need to repeat the calculation, just need to multiply the result of 
// position 4 by 3.
	  

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num_tests = sc.nextInt();
        for(int i = 0; i < num_tests; ++i)
        {
            int N = sc.nextInt();
            int[] array = new int[N+1];
            for(int j = 1; j <= N; ++j)
                array[j] = sc.nextInt();
            calculate(array, N);
        }
    }
    
    private static void calculate(int[] array, int N)
    {
        Arrays.sort(array);
        double[] single_score = new double[N+1];
        int i = 1;
        while(i <= N)
        {
            double score_i = 0; 
            for(int score = 1; score <= i; ++score)
            {
            	double prob = 0;
            	if(i == N)
            		prob = 1;
            	else
            	{
            		double larger = N-i; // # digits that are larger than array[i]
            		double small_need = score-1; // need this amount of smaller-than-array[i]-number consecutively before array[i]
            		double smaller = i-1; // # digits that are smaller than array[i]
            		double numerator = 1;
            		double denominator = 1;
            		int num = N-1;
            		while(small_need > 0)
            		{
            			numerator *= smaller;
            			denominator *= num;
            			--smaller;
            			--small_need;
            			--num;
            		}
        			prob = numerator/denominator;
            		
            		prob = prob + prob * (N-score)*(larger/num);
            	}
        		prob /= N;
                score_i +=  prob*score;
            }
            
            single_score[i] = score_i;
            ++i;
            while(i <= N && array[i] == array[i-1])
            {
                single_score[i] = single_score[i-1];
                ++i;
            }
        }
        
        double result = 0;
        for(int ind = 1; ind <= N; ++ind)
            result += single_score[ind];
        
        System.out.format("%.2f%n", result);
    }
}