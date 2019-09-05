package com.github.heaweavy.saa.dp;

/**
 * @author caimb
 * @date Created at 2019-08-01 17:59
 * @modefier
 */
public class Str1ToStr2 {
    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        //cin>>str1;
        //cin>>str2;
        int M = str1.length();
        int N = str2.length();
        //vector<int> p(M+1,0);
        //vector<vector<int>> dp(N+1,p);
        int dp[][] = new int[10][10];
        int ic=5,dc=3,rc=2;
        //int ic = 1,dc=1,rc=1;
        dp[0][0] = 0;
        for (int i = 1;i<N+1;i++)
            dp[0][i] = ic*i;
        for (int i = 1;i<M+1;i++)
            dp[i][0] = dc*i;

        for (int i=0;i<M;i++){
            for (int j = 0;j<N;j++){
                int x = Math.min(dc+dp[i][j+1],dp[i+1][j]+ic);
                if ( str1.charAt( i ) != str2.charAt( j ) ) {
                    dp[i + 1][j + 1] = Math.min( dp[i][j] + rc, x );
                } else {
                    dp[i + 1][j + 1] = Math.min( dp[i][j], x );
                }
            }
        }
        System.out.println(dp[M][N]);
    }
}
