import java.io.*;
import java.util.*;

public class Backup {
    static int N, M, maxJump;
    static int[] level;
    static int[][] parent, dist;
    static ArrayList<Edge>[] edges;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Init
        N = Integer.parseInt(br.readLine());
        edges = new ArrayList[N+1];

        for (int i = 1; i <= N; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 1; i <= N-1; i++) {
            String[] in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            int c = Integer.parseInt(in[2]);
            edges[a].add(new Edge(b, c));
            edges[b].add(new Edge(a, c));
        }

        maxJump = (int)(Math.log(N) / Math.log(2));
        level = new int[N+1];
        parent = new int[N+1][maxJump+1];
        dist = new int[N+1][maxJump+1];

        setTree(1, 1, 0);

        System.out.println(Arrays.toString(level));

        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + ": " + Arrays.toString(dist[i]));
        }

        // Solve
        M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            String[] in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            int res = getLCA(a, b);
            sb.append(res).append("\n");
        }
        System.out.println(sb.toString());

        br.close();
    }

    static int getLCA(int a, int b) {
        if (level[a] < level[b]) { // 더 하위레벨을 a로 세팅
            int tmp = a;
            a = b;
            b = tmp;
        }
        int aDist = 0;
        int bDist = 0;

        int diff = level[a] - level[b];
        for (int i = maxJump; i >= 0; i--) {
            int bit = 1 << i;
            if ((diff & bit) > 0) {
                diff &= ~bit;
                aDist += dist[a][i];

                a = parent[a][i];
            }
        }

        if (a == b) {
            return aDist;
        }

        for (int i = maxJump; i >= 0; i--) {
            if (parent[a][i] != parent[b][i]) {
                aDist += dist[a][i];
                bDist += dist[b][i];

                a = parent[a][i];
                b = parent[b][i];
            }
        }
        aDist += dist[a][0];
        bDist += dist[b][0];

        return aDist + bDist;
    }

    static void setTree(int here, int from, int len) {
        List<Edge> hereEdges = edges[here];

        level[here] = level[from] + 1;

        parent[here][0] = from;
        dist[here][0] = len;
        for (int i = 1; i <= maxJump; i++) {
            parent[here][i] = parent[parent[here][i-1]][i-1];
            dist[here][i] = len + dist[parent[here][i-1]][i-1];
        }

        for (Edge e : hereEdges) {
            if (e.target != from)
                setTree(e.target, here, e.len);
        }
    }

    static class Edge {
        int target, len;
        public Edge(int target, int len) {
            this.target = target;
            this.len = len;
        }
    }
}
