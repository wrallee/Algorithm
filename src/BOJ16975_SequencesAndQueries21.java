import java.io.*;
import java.util.*;
import java.util.stream.*;

public class BOJ16975_SequencesAndQueries21 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        long[] val = new long[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            val[i] = Long.parseLong(st.nextToken());
        }


        Tree tree = new Tree(N, val);

        int M = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int a = 0; a < M; a++) {
            st = new StringTokenizer(br.readLine());
            int c, i, j, k, x;
            c = Integer.parseInt(st.nextToken());
            if (c == 1) {
                i = Integer.parseInt(st.nextToken());
                j = Integer.parseInt(st.nextToken());
                k = Integer.parseInt(st.nextToken());
                tree.updateRange(i, j, k);
            } else {
                x = Integer.parseInt(st.nextToken());
                sb.append(tree.query(x));
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());

        br.close();
    }

    static class Tree {
        int N;
        long[] val, tree, lazy;

        public Tree(int N, long[] val) {
            this.N = N;
            this.val = val;

            int H = (int)Math.ceil(Math.log(N) / Math.log(2)) + 1;
            this.tree = new long[(1 << H) + 1];
            this.lazy = new long[(1 << H) + 1];

            initTree(1, N, 1);
        }

        public long query(int target) {
            return query(1, N, 1, target);
        }

        public void updateRange(int left, int right, long load) {
            updateRange(1, N, 1, left, right, load);
        }

        private long query(int stt, int end, int node, int target) {
            lazyPropagation(stt, end, node);
            if (target < stt || end < target) {
                return 0;
            }
            if (stt == target && target == end) {
                return tree[node];
            }
            int mid = (stt + end) / 2;
            return query(stt, mid, 2*node, target)
                    + query(mid+1, end, 2*node+1, target);
        }

        private void updateRange(int stt, int end, int node, int left, int right, long load) {
            // lazy
            lazyPropagation(stt, end, node);
            if (right < stt || end < left) {
                return;
            }

            if (left <= stt && end <= right) {
                lazy[node] += load;
                lazyPropagation(stt, end, node);
                return;
            }

            int mid = (stt + end) / 2;
            updateRange(stt, mid, 2*node, left, right, load);
            updateRange(mid+1, end, 2*node+1, left, right, load);

            tree[node] = tree[2*node] + tree[2*node+1];
        }

        // lazy 처리
        private void lazyPropagation(int stt, int end, int node) {
            tree[node] += lazy[node] * (end-stt+1);
            if (stt != end) {
                lazy[2*node] += lazy[node];
                lazy[2*node+1] += lazy[node];
            }
            lazy[node] = 0;
        }

        // 트리 초기화
        private long initTree(int stt, int end, int node) {
            if (stt == end) {
                return tree[node] = val[stt];
            }
            int mid = (stt + end) / 2;
            return tree[node] = initTree(stt, mid, 2*node)
                    + initTree(mid+1, end, 2*node+1);
        }
    }

}
