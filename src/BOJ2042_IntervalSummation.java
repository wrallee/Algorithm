import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2042_IntervalSummation {
    static int N, M, K;
    static long[] arr, sum;
    static Diff[] diffs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new long[N];
        sum = new long[N];
        diffs = new Diff[M];

        sum[0] = arr[0] = Long.parseLong(br.readLine());
        for (int i = 1; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
            sum[i] = arr[i] + sum[i-1];
        }

        int cnt = 0;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M+K; i++) {
            long result = 0;
            st = new StringTokenizer(br.readLine(), " ");

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                diffs[cnt++] = new Diff(b-1, c-arr[b-1]);
                arr[b-1] = c;
            }

            if (a == 2) {
                int start = b-1;
                int end = (int)(c-1);

                if (b == 1) {
                    result = sum[end];
                } else {
                    result = sum[end] - sum[b-2];
                }

                for (int j = 0; j < cnt; j++) {
                    Diff diff = diffs[j];
                    if (diff.idx >= start && diff.idx <= end)
                        result += diff.val;
                }
                sb.append(result).append("\n");
            }
        }

        System.out.print(sb.toString());
    }

    static class Diff {
        int idx;
        long val;
        public Diff(int idx, long val) {
            this.idx = idx;
            this.val = val;
        }
    }
}