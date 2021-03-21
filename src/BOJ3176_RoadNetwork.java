import java.io.*;
import java.util.*;

public class BOJ3176_RoadNetwork {
    static int N, K, maxJump;
    static int[] level;
    static int[][] parent, minP, maxP;
    static List[] edges;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // INIT
        N = Integer.parseInt(br.readLine());
        maxJump = (int)(Math.log(N) / Math.log(2));

        level = new int[N+1];
        parent = new int[N+1][maxJump+1];
        minP = new int[N+1][maxJump+1];
        maxP = new int[N+1][maxJump+1];
        edges = new ArrayList[N+1];

        for (int i = 0; i <= N; i++) {
            edges[i] = new ArrayList();
            Arrays.fill(minP[i], 1000001);
        }
        for (int i = 1; i <= N-1; i++) {
            String[] in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            int c = Integer.parseInt(in[2]);
            edges[a].add(new Edge(b, c));
            edges[b].add(new Edge(a, c));
        }
        setTree();

        // SOLVE
        K = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < K; i++) {
            String[] in = br.readLine().split(" ");
            int d = Integer.parseInt(in[0]);
            int e = Integer.parseInt(in[1]);
            int[] res = getMinMaxLength(d, e);
            sb.append(res[0])
                    .append(" ")
                    .append(res[1])
                    .append("\n");
        }
        System.out.println(sb.toString());

        br.close();
    }

    static int[] getMinMaxLength(int d, int e) {
        if (level[d] < level[e]) {
            int tmp = d;
            d = e;
            e = tmp;
        }

        int[] minMax = {1000001, 0};

        int diff = level[d] -  level[e];
        for (int i = maxJump; i >= 0; i--) {
            int bit = 1 << i;
            if ((diff & bit) > 0) {
                minMax[0] = Math.min(minMax[0], minP[d][i]);
                minMax[1] = Math.max(minMax[1], maxP[d][i]);

                d = parent[d][i];
            }
        }

        if (d == e) {
            return minMax;
        }

        for (int i = maxJump; i >= 0; i--) {
            if (parent[d][i] != parent[e][i]) {
                minMax[0] = Math.min(minMax[0], Math.min(minP[d][i], minP[e][i]));
                minMax[1] = Math.max(minMax[1], Math.max(maxP[d][i], maxP[e][i]));

                d = parent[d][i];
                e = parent[e][i];
            }
        }

        minMax[0] = Math.min(minMax[0], Math.min(minP[d][0], minP[e][0]));
        minMax[1] = Math.max(minMax[1], Math.max(maxP[d][0], maxP[e][0]));

        return minMax;
    }

    static void setTree() {
        Queue<Integer> q = new LinkedList<>();
        q.add(1);

        parent[1][0] = 1;

        boolean[] visit = new boolean[N+1];
        while (!q.isEmpty()) {
            int here = q.poll();
            visit[here] = true;
            List<Edge> branch = edges[here];
            for (Edge e : branch) {
                int next = e.target;
                if (visit[e.target])
                    continue;

                parent[next][0] = here;
                maxP[next][0] = e.length;
                minP[next][0] = e.length;

                level[next] = level[here] + 1;
                q.offer(e.target);
            }
        }

        for (int i = 1; i <= maxJump; i++) {
            for (int node = 1; node <= N; node++) {
                parent[node][i] = parent[parent[node][i-1]][i-1];
                minP[node][i] = Math.min(minP[node][i-1], minP[parent[node][i-1]][i-1]);
                maxP[node][i] = Math.max(maxP[node][i-1], maxP[parent[node][i-1]][i-1]);
            }
        }
    }

    static class Edge {
        int target, length;
        public Edge(int target, int length) {
            this.target = target;
            this.length = length;
        }
    }
}
