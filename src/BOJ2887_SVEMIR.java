import java.io.*;
import java.util.*;

public class BOJ2887_SVEMIR {
    static int N;
    static int[] set;
    static Node[][] nodes;
    static ArrayList<Edge> edges;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        set = new int[N];
        nodes = new Node[4][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long x, y, z;
            x = Long.parseLong(st.nextToken());
            y = Long.parseLong(st.nextToken());
            z = Long.parseLong(st.nextToken());
            nodes[3][i] = new Node(i, new long[]{x, y, z});
        }

        for (int i = 0; i <= 2; i++) {
            int finalI = i;
            nodes[i] = Arrays.stream(nodes[3])
                    .sorted(Comparator.comparingLong(x -> x.loc[finalI]))
                    .toArray(Node[]::new);
        }

        edges = new ArrayList<>();
        for (int i = 0; i < N-1; i++) {
            for (int j = 0; j <= 2; j++) {
                Node n1 = nodes[j][i];
                Node n2 = nodes[j][i+1];
                long dist = n2.loc[j] - n1.loc[j];
                edges.add(new Edge(n1.n, n2.n, dist));
            }
        }

        for (int i = 0; i < N; i++) {
            set[i] = i;
        }

        edges.sort(Comparator.comparingLong(x -> x.dist));

        int sum = 0;
        for (Edge edge : edges) {
            if (checkDisjoint(edge.a, edge.b)) {
                sum += edge.dist;
                mergeUnion(edge.a, edge.b);
            }
        }

        System.out.println(sum);

        br.close();
    }
    
    // 부모 비교
    static boolean checkDisjoint(int a, int b) {
        a = getParent(a);
        b = getParent(b);
        return a != b;
    }

    // 부모 병합
    static void mergeUnion(int a, int b) {
        a = getParent(a);
        b = getParent(b);

        if (a < 0) {
            set[b] = a;
        } else {
            set[a] = b;
        }
    }
    
    // 부모 찾기
    static int getParent(int n) {
        if (set[n] == n) {
            return n;
        }
        return set[n] = getParent(set[n]);
    }

    static class Node {
        int n;
        long[] loc;
        public Node(int n, long[] loc) {
            this.n = n;
            this.loc = loc;
        }
    }

    static class Edge {
        int a, b;
        long dist;
        public Edge(int a, int b, long dist) {
            this.a = a;
            this.b = b;
            this.dist = dist;
        }
    }
}
