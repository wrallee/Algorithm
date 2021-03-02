import java.io.*;
import java.util.*;

class BOJ11438_LCA2 {
    static int N, M, maxJump;
    static int[] level;
    static int[][] parent;
    static ArrayList[] edges;
    public static void main(String[] args) throws Exception {
        //System.setIn(new FileInputStream("testcase/input1"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        edges = new ArrayList[N+1];
        for(int i = 1; i <= N; i++) {
            edges[i] = new ArrayList<>();
        }
        for(int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(b);
            edges[b].add(a);
        }
        
        
        setTree();
        
        M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int result = go(a, b);
            sb.append(result).append("\n");
        }
        
        System.out.print(sb.toString());
        
        br.close();
    }
    
    static int go(int a, int b) {
        if (level[a] < level[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        
        int diff = level[a] - level[b];
        // 점프해서 올라가기
        for(int i = maxJump; i >= 0; i--) {
            int bit = 1 << i;
            if ((diff & bit) > 0) {
                diff &= ~bit;
                a = parent[i][a];
            }
        }
        
        if (a == b)
            return a;
        
        
        for(int i = maxJump; i >= 0; i--) {
            if (parent[i][a] != parent[i][b]) {
                a = parent[i][a];
                b = parent[i][b];
            }
        }
        
        return parent[0][a];
        
    }
    
    static void setTree() {
        maxJump = (int)(Math.log(N) / Math.log(2));
        level = new int[N+1];
        parent = new int[maxJump+1][N+1];
        parent[0][1] = 1; // 루트는 부모가 자기 자신이다
        
        Queue<Integer> q = new LinkedList<>();
        boolean[] visit = new boolean[N+1];
        
        q.offer(1);
        while(!q.isEmpty()) {
            int node = q.poll();
            visit[node] = true;
            List<Integer> list = edges[node];
            for (int i = 0; i < list.size(); i++) {
                int next = list.get(i);
                if (visit[next])
                    continue;
                parent[0][next] = node;
                level[next] = level[node] + 1;
                q.offer(next);
            }
        }
        
        for(int i = 1; i <= maxJump; i++) {
            for(int node = 1; node <= N; node++) {
                parent[i][node] = parent[i-1][parent[i-1][node]];
            }
        }
    }
}