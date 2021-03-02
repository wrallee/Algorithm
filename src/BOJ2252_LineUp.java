import java.io.*;
import java.util.*;

class BOJ2252_LineUp {
    static int[] fromCnts;
    static ArrayList<Integer>[] edges;
    public static void main(String[] args) throws Exception {
        //System.setIn(new FileInputStream("testcase/input1"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        initStaticVariables(br);
        System.out.println(run());
        
        br.close();
    }
    
    static String run() {
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i < fromCnts.length; i++) {
            if (fromCnts[i] == 0)
                q.offer(i);
        }
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            int from = q.poll();
            sb.append(from).append(" ");
            for (int i = 0; i < edges[from].size(); i++) {
                int to = edges[from].get(i);
                fromCnts[to]--;
                if (fromCnts[to] == 0)
                    q.offer(to);
            }
        }
        
        return sb.toString().trim();
    }
    
    static void initStaticVariables(BufferedReader br) throws Exception {
        int N, M;
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        fromCnts = new int[N+1];
        edges = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) {
            edges[i] = new ArrayList<Integer>();
        }
        
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            edges[from].add(to);
            fromCnts[to]++;
        }
    }
}