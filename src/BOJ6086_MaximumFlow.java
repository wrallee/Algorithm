import java.io.*;
import java.util.*;

class BOJ6086_MaximumFlow {
    static int[][] edges, flowDashboard;
    static final int A = 0, Z = 25, MAX_V_CNT = 'z'-'A'+1;
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("testcase/input1"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        initStaticVariables(br);
        System.out.println(run());
        
        br.close();
    }
    
    static int run() {
        int result = 0;
        while(true) {
            int[] trace = getPossiblePath();
            if (trace[Z] == -1)
                break;
            
            int minCapacity = 1001;
            for (int i = Z; i != A; i = trace[i]) {
                minCapacity = Math.min(minCapacity,
                                       getResidualCapacity(trace[i], i));
            }
            for (int i = Z; i != A; i = trace[i]) {
                flowDashboard[trace[i]][i] += minCapacity;
                flowDashboard[i][trace[i]] -= minCapacity;
            }
            
            result += minCapacity;
        }
        
        return result;
    }
    
    static int[] getPossiblePath() {
        Queue<Integer> q = new LinkedList<>();
        int[] trace = new int[MAX_V_CNT];
        Arrays.fill(trace, -1);
        trace[A] = 0;
        q.offer(A);
        while (!q.isEmpty()) {
            int vertex = q.poll();
            for (int next = A; next < MAX_V_CNT; next++) {
                if (edges[vertex][next] == 0)
                    continue;
                if (trace[next] != -1)
                    continue;
                if (getResidualCapacity(vertex, next) == 0)
                    continue;
                
                trace[next] = vertex;
                q.offer(next);
                if (next == Z) {
                    q.clear();
                    break;
                }
            }
        }
        
        return trace;
    }
    
    static int getResidualCapacity(int v1, int v2) {
        return edges[v1][v2] - flowDashboard[v1][v2];
    }
    
    static void initStaticVariables(BufferedReader br) throws Exception {
        edges = new int[MAX_V_CNT][MAX_V_CNT];
        flowDashboard = new int[MAX_V_CNT][MAX_V_CNT];
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = convertCharToInt(st.nextToken().charAt(0));
            int v2 = convertCharToInt(st.nextToken().charAt(0));
            int capacity = Integer.parseInt(st.nextToken());
            edges[v1][v2] += capacity;
            edges[v2][v1] += capacity;
        }
    }
    
    static int convertCharToInt(char c) {
        return c - 'A';
    }
}