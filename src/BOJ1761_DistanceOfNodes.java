import java.io.*;
import java.util.*;

public class BOJ1761_DistanceOfNodes {
    static int N, M, maxJump;
    static int[] level;
    static int[][] parent, distance;
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
        distance = new int[N+1][maxJump+1];

        setTree();

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
                aDist += distance[a][i];

                a = parent[a][i];
            }
        }

        if (a == b) {
            return aDist;
        }

        for (int i = maxJump; i >= 0; i--) {
            if (parent[a][i] != parent[b][i]) {
                aDist += distance[a][i];
                bDist += distance[b][i];

                a = parent[a][i];
                b = parent[b][i];
            }
        }
        aDist += distance[a][0];
        bDist += distance[b][0];

        return aDist + bDist;
    }

    static void setTree() {
        Queue<Integer> q = new LinkedList<>();
        q.add(1);

        boolean[] visit = new boolean[N+1];
        while (!q.isEmpty()) {
            int here = q.poll();
            visit[here] = true;
            List<Edge> hereEdges = edges[here];

            for (int i = 0; i < hereEdges.size(); i++) {
                Edge next = hereEdges.get(i);
                if (visit[next.target]) {
                    continue;
                }
                level[next.target] = level[here] + 1;
                parent[next.target][0] = here;
                distance[next.target][0] = next.len;
                q.offer(next.target);
            }
        }

        for (int i = 1; i <= maxJump; i++) {
            for (int node = 1; node <= N; node++) {
                parent[node][i] = parent[parent[node][i-1]][i-1];
                distance[node][i] = distance[parent[node][i-1]][i-1] + distance[node][i-1];
            }
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
