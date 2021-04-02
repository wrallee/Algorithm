public class STD_SegmentTree {
    int N;
    long[] val, tree, lazy;

    public STD_SegmentTree(int N, long[] val) {
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
        // lazy
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
            // lazy
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