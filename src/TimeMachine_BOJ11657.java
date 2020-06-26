import java.io.*;
import java.util.*;

public class TimeMachine_BOJ11657 {
	static int N, M;
	static long[] vertices;
	static Edge[] edges;
	static final int INF = 987654321;
	public static void main(String[] args) throws Exception {
		// 벨만-포드 알고리즘
		// 시작점을 제외하고 INF로 초기화
		// 모든 간선에 대해 Relax 진행
		// 마지막에 Relax를 한 번 더 진행해서 변화가 있으면 -1 출력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		init(br);
		relaxing(N);
		print(relaxing(1));
		
		br.close();
	}
	
	static boolean relaxing(int iteration) {
		boolean flag = false;
		for (int n = 1; n <= iteration; n++) {
			//System.out.println(Arrays.toString(vertices));
			for (int i = 1; i <= M; i++) {
				Edge e = edges[i];
				if (vertices[e.prev] == INF)
					continue;
				
				long tmpDistance = vertices[e.prev] + e.dist;
				if (vertices[e.next] > tmpDistance) {
					vertices[e.next] = tmpDistance;
					flag |= true;
				}
			}
		}
		
		return flag;
	}
	
	static void print(boolean flag) {
		StringBuilder sb = new StringBuilder();
		if (flag) {
			sb.append(-1).append("\n");
		} else {
			for (int i = 2; i <= N; i++) {
				long result = vertices[i] != INF ? vertices[i] : -1;
				sb.append(result).append("\n");
			}
		}
		
		System.out.print(sb.toString());
	}
	
	static void init(BufferedReader br) throws Exception {
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		
		vertices = new long[N+1];
		Arrays.fill(vertices, INF);
		
		edges = new Edge[M+1];
		for (int i = 1; i <= M; i++) {
			input = br.readLine().split(" ");
			int vertexFrom = Integer.parseInt(input[0]);
			int vertexTo = Integer.parseInt(input[1]);
			int timeForTrip = Integer.parseInt(input[2]);
			edges[i] = new Edge(vertexFrom, vertexTo, timeForTrip);
		}
		
		vertices[1] = 0;
	}
	
	static class Edge {
		int prev, next, dist;
		public Edge(int prev, int next, int dist) {
			this.prev = prev;
			this.next = next;
			this.dist = dist;
		}
	}

}
