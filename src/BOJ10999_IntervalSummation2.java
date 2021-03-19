import java.io.*;

public class BOJ10999_IntervalSummation2 {
    static int N, M, K;
    static long[] nodes, lazy, value;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        N = Integer.parseInt(in[0]);
        M = Integer.parseInt(in[1]);
        K = Integer.parseInt(in[2]);

        int height = (int)Math.ceil(Math.log(N)/Math.log(2)) + 1;

        value = new long[N+1];
        nodes = new long[(1 << height) + 1];
        lazy = new long[(1 << height) + 1];

        for (int i = 1; i <= N; i++) {
            value[i] = Integer.parseInt(br.readLine());
        }
        init(1, N, 1);
        for (int i = 1; i <= M+K; i++) {
            printResult(br.readLine().split(" "));
        }
        br.close();
    }
    static void printResult(String[] in) {
        int a = Integer.parseInt(in[0]);
        int b = Integer.parseInt(in[1]);
        int c = Integer.parseInt(in[2]);
        if (a == 1) {
            long d = Long.parseLong(in[3]);
            updateRange(1, N, 1, b, c, d);
        } else if (a == 2) {
            long res = selectSum(1, N, 1, b, c);
            System.out.println(res);
        }
    }

    static long selectSum(int stt, int end, int node, long left, long right) {
        propagation(stt, end, node);
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

    static void updateRange(int stt, int end, int node, int left, int right, long load) {
        propagation(stt, end, node);
        if (right < stt || end < left) {
            return;
        }

        if (left <= stt && end <= right) {
            lazy[node] += load;
            propagation(stt, end, node);
            return;
        }

        int mid = (stt + end) / 2;
        updateRange(stt, mid, 2*node, left, right, load);
        updateRange(mid+1, end, 2*node+1, left, right, load);

        nodes[node] = nodes[2*node] + nodes[2*node+1];
    }

    static void propagation(int stt, int end, int node) {
        // Lazy 전파
        nodes[node] += lazy[node]*(end-stt+1);
        if (stt != end) {
            lazy[2*node] += lazy[node];
            lazy[2*node+1] += lazy[node];
        }
        lazy[node] = 0;
    }

    static long init(int stt, int end, int node) {
        if (stt == end) {
            return nodes[node] = value[stt];
        }
        int mid = (stt + end) / 2;
        return nodes[node] = init(stt, mid, 2*node)
                + init(mid+1, end, 2*node+1);
    }
}