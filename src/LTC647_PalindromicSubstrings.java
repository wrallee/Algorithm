import java.util.Arrays;

class LTC647_PalindromicSubstrings {
    public int countSubstrings(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        boolean[][] dp = new boolean[n+1][n];

        Arrays.fill(dp[0], true);
        Arrays.fill(dp[1], true);

        int cnt = n;
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n-len+1; i++) {
                if (chars[i] == chars[i+len-1] && dp[len-2][i+1]) {
                    dp[len][i] = true;
                    cnt++;
                }
            }
        }

        return cnt;
    }
}