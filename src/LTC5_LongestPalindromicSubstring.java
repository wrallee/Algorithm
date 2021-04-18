import java.util.Arrays;

class LTC5_LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n+1][n];
        String answer = String.valueOf(s.charAt(0));

        Arrays.fill(dp[0], true);
        Arrays.fill(dp[1], true);

        int max = 1;
        for (int len = 2; len <= n; len++) {
            for (int idx = 0; idx+len-1 < n; idx++) {
                if (s.charAt(idx) == s.charAt(idx+len-1) && dp[len-2][idx+1]) {
                    dp[len][idx] = true;
                    if (len > max) {
                        max = len;
                        answer = s.substring(idx, idx+len);
                    }
                }
            }
        }

        return answer;
    }
}