import java.io.*;

public class BOJ2042_IntervalSummation_Tree {
    static int N, M, K;
    static long[] nodes, value;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        N = Integer.parseInt(in[0]);
        M = Integer.parseInt(in[1]);
        K = Integer.parseInt(in[2]);

        int height = (int)Math.ceil(Math.log(N)/Math.log(2)) + 1;

        value = new long[N+1];
        nodes = new long[(1 << height) + 1];

        for (int i = 1; i <= N; i++) {
            value[i] = Integer.parseInt(br.readLine());
        }
        init(1, N, 1);

        for (int i = 1; i <= M+K; i++) {
            in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            long c = Long.parseLong(in[2]);

            switch (a) {
                case 1:
                    long diff = c - value[b];
                    updateIdx(1, N, 1, b, diff);
                    break;
                case 2:
                    long res = selectSum(1, N, 1, b, c);
                    System.out.println(res);
                    break;
            }
        }

        br.close();
    }

    static long init(int stt, int end, int node) {
        if (stt == end) {
            return nodes[node] = value[stt];
        }
        int mid = (stt + end) / 2;
        return nodes[node] = init(stt, mid, 2*node)
                + init(mid+1, end, 2*node+1);
    }

    static long selectSum(int stt, int end, int node, long left, long right) {
        if (right < stt || end < left) {
            return 0;
        }
        if (left <= stt && end <= right) {
            return nodes[node];
        }
        int mid = (stt + end) / 2;
        return selectSum(stt, mid, 2*node, left, right)
                + selectSum(mid+1, end, 2*node+1, left, right);
    }

    static long updateIdx(int stt, int end, int node, int idx, long diff) {
        if (idx < stt || end < idx) {
            return nodes[node];
        }
        if (idx == stt && end == idx) {
            value[idx] += diff;
            return nodes[node] += diff;
        }
        int mid = (stt + end) / 2;
        return nodes[node] = updateIdx(stt, mid, 2*node, idx, diff)
                + updateIdx(mid+1, end, 2*node+1, idx, diff);
    }
}
