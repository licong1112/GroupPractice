import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int num_test = sc.nextInt();
        for(int i = 0; i < num_test; ++i) {
            int num_people = sc.nextInt();
            int[] data = new int[num_people];
            for(int j = 0; j < num_people; ++j) {
                data[j] = sc.nextInt();
            }
            int[] count = new int[1010];
            calculate(data, count);
        }
    }
    
    public static void calculate(int[] data, int[] count) {
        if(data.length == 0) {
            System.out.println(0);
            return;
        }
        int min = Integer.MAX_VALUE;
        int cum_count = 0;
        for(int i = 0; i < data.length; ++i) {
            count[data[i]]++;
            min = Math.min(min, data[i]);
        }
        
        long num_op = 0;
        for(int i = 0; i < count.length; ++i) {
            if(i-min >= 5) {
                num_op += (i-min)/5 * count[i];
                count[min+(i-min)%5] += count[i];
            }
        }
        
        long num_op_0 = 0, num_op_1 = 0, num_op_2 = 0;
        for(int i = 0; i < 5; ++i) {
            num_op_0 += ProcessNum(count[min+i], i);
            num_op_1 += ProcessNum(count[min+i], i+1);
            num_op_2 += ProcessNum(count[min+i], i+2);
        }
        System.out.println(num_op + Math.min(Math.min(num_op_0, num_op_1), num_op_2));
    }
    
    public static int ProcessNum(int freq, int diff) {
        if(diff == 0) return 0;
        if(diff == 1 || diff == 2 || diff == 5) return freq;
        if(diff == 3 || diff == 4) return 2*freq;
        
        return diff/5 * freq + ProcessNum(freq, diff%5);
    }
}
