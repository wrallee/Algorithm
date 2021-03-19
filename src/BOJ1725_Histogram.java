import java.io.*;

public class BOJ1725_Histogram {
    static int[] nodes;
    static long[] value;
    static long result;
    static int N, H;
    static final int MAX = 1000000000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        H = (int)Math.ceil(Math.log(N)/Math.log(2)) + 1;

        value = new long[N + 1];
        nodes = new int[(1 << H) + 1];

        value[0] = MAX;

        for (int i = 1; i <= N; i++) {
            value[i] = Integer.parseInt(br.readLine());
        }

        initTree(1, N, 1);

        result = 0;
        int minIdx = selectMin(1, N, 1, 1, N);

        subset(1, N, minIdx);
        System.out.println(result);

        br.close();
    }

    static void subset(int l, int r, int minIdx) {
        result = Math.max(result, (r-l+1)*value[minIdx]);
        if (l >= r) {
            return;
        }

        int lMinIdx = selectMin(1, N, 1, l, minIdx-1);
        int rMinIdx = selectMin(1, N, 1, minIdx+1, r);

        subset(l, minIdx-1, lMinIdx);
        subset(minIdx+1, r, rMinIdx);
    }

    static int initTree(int stt, int end, int node) {
        if (stt == end) {
            return nodes[node] = stt;
        }
        int mid = (stt + end) / 2;
        int l = initTree(stt, mid, 2*node);
        int r = initTree(mid+1, end, 2*node+1);
        return nodes[node] = value[l] < value[r] ? l : r;
    }

    static int selectMin(int stt, int end, int node, int left, int right) {
        if (right < stt || end < left) {
            return 0;
        }
        if (left <= stt && end <= right) {
            return nodes[node];
        }
        int mid = (stt + end) / 2;
        int l = selectMin(stt, mid, 2*node, left, right);
        int r = selectMin(mid+1, end, 2*node+1, left, right);
        return value[l] < value[r] ? l : r;
    }
}
