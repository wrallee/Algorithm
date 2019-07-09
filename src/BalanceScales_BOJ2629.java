import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class BalanceScales_BOJ2629 {
    static int N, M;
    static int[] w, kSum;
    static boolean[][] dp;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         
        N = Integer.parseInt(br.readLine());
        w = new int[N+1];
        kSum = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            w[i] = Integer.parseInt(st.nextToken());
            kSum[i] = kSum[i-1] + w[i];
        }
         
        bottomUp();
         
        M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            int q = Integer.parseInt(st.nextToken());
            if (q > kSum[N])
                System.out.print("N");
            else
                System.out.print(dp[N&1][q] ? "Y" : "N");
            System.out.print(" ");
        }
        
        br.close();
    }
     
    static void bottomUp() {
        dp = new boolean[2][kSum[N]+1];
         
        dp[0][0] = true;
         
        for (int n = 1; n <= N; n++) {
            for (int k = 0; k+w[n] <= kSum[n]; k++) {
                // 추를 사용하지 않을 때
                dp[n&1][k] |= dp[(n-1)&1][k];
                 
                // 추를 사용할 때
            	dp[n&1][k+w[n]] |= dp[(n-1)&1][k];
            	dp[n&1][Math.abs(k-w[n])] |= dp[(n-1)&1][k];
            }
        }
    }
 
}