// Author: Cong Li
// Practiced on 3/18/2013
// https://www.hackerrank.com/challenges/billboards
// 
// =========================================
// Here is the basic idea:
// Let dp[i][k] (i = 0,...N-1, k = 0,...K) represents the maximum value
// that can be achieved when exactly [array[i-k+1],...,array[i]] are kept
// from being removed (thus array[i-k] is removed). Note dp[i][0] represents
// the situation when array[i] is removed.
// 
// Therefore, dp[i][0] = max(dp[i-1][k]), k = 0,...,K.
// dp[i][k] = dp[i-1][i-k-1] + sum{array[i-k+1],...,array[i]}, when k > 0.
// 
// However, this will not pass the judge. To optimize it, one key observation
// needs to be made. 
// Let DP[i] = max{dp[i][0],...,dp[i][K]}. Thus the what we need is DP[N-1].
// 
// When DP[i] = dp[i][k] (k < K), DP[i+1] must equal to dp[i+1][k+1].
// Only when DP[i] = dp[i][K], we need to use the above equation to calculate
// dp[i+1][k] for all k = 0,...,K and thus get DP[i+1].
// 
// So, whenever DP[i] != dp[i][K], we save K-1 calculations to get DP[i+1].
// 
// Another trap is that, don't use int type, it will overflow.


#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;


int main() {
    int num_digits, k;
    cin >> num_digits >> k;
    
    long long int sum[num_digits];
    long long int dp[num_digits];
    long long int dp2[k+1];
    long long int array[num_digits];
    
    int index = 1;
    for(int i = 0; i < num_digits; ++i)
    {
        cin >> array[i];
        if(i < k)
        {
            sum[i] = i != 0 ? sum[i-1]+array[i] : array[i];
            dp[i] = sum[i];
            dp2[index++] = sum[i];
        }
        else
        {
            sum[i] = sum[i-1] - array[i-k] + array[i];
            if(index > k)
            {
                dp2[0] = dp2[k];
                long long int sum_temp = sum[i];
                long long int max = sum_temp + (i-k == 0 ? 0 : dp[i-k-1]);
                int max_index = k;
                for(int remove = i-k+1; remove < i; ++remove)
                {    
                    sum_temp -= array[remove];
                    dp2[i-remove] = dp[remove-1] + sum_temp;
                    if(max < dp2[i-remove])
                    {
                        max = dp2[i-remove];
                        max_index = i-remove;
                    }
                }
                if(max < dp2[0])
                {
                    max = dp2[0];
                    max_index = 0;
                }
                dp[i] = max;
                index = max_index+1;
            }
            else
            {
                dp2[index] = dp2[index-1] + array[i];
                dp[i] = dp2[index++];
            }
        }  
    }

    cout << dp[num_digits-1] << endl;    
    return 0;
}