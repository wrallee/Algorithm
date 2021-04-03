import java.io.*;
import java.util.*;

public class BOJ1197_MinimumSpanningTree {
    static int V, E;
    static int[] set;
    static Edge[] edges;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        edges = new Edge[E];
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a, b, dist;
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            dist = Integer.parseInt(st.nextToken());
            edges[i] = new Edge(a, b, dist);
        }

        Arrays.sort(edges, Comparator.comparingInt(e -> e.dist));

        set = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            set[i] = i;
        }

        int sum = 0;
        for (int i = 0; i < E; i++) {
            Edge edge = edges[i];
            if (checkDisjoint(edge.a, edge.b)) {
                sum += edge.dist;
                unionParent(edge.a, edge.b);
            }
        }

        System.out.println(sum);

        br.close();
    }

    // 부모 검증
    static boolean checkDisjoint(int a, int b) {
        a = getParent(a);
        b = getParent(b);
        return a != b;
    }

    // 부모 병합
    static void unionParent(int a, int b) {
        a = getParent(a);
        b = getParent(b);
        if (a < b) {
            set[b] = a;
        } else {
            set[a] = b;
        }
    }

    // 부모노드 가져오기
    static int getParent(int n) {
        if (set[n] == n) {
            return n;
        }
        return set[n] = getParent(set[n]);
    }

    static class Edge {
        int a, b, dist;
        public Edge(int a, int b, int dist) {
            this.a = a;
            this.b = b;
            this.dist = dist;
        }
    }

}
