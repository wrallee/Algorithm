import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1753_ShortestPath {
	static int V, E, K;
	static ArrayList<Line>[] lines;
	static int[] distance;
	static boolean[] visit;
	static PriorityQueue<Node> q;
	static final int MAX = 200001;
	public static void main(String[] args) throws Exception {
		// 다익스트라 알고리즘
		// 우선순위 큐를 사용하여 최단경로를 빠르게 찾는다
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//Runtime rt = Runtime.getRuntime();long before = rt.totalMemory() - rt.totalMemory();
		//long stt = System.currentTimeMillis();
		// INIT
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(br.readLine());
		
		lines = new ArrayList[V+1]; // [V+1][V+1] -> 메모리초과 & 생성시간 엄청남(2초)
		
		distance = new int[V+1];
		visit = new boolean[V+1];
		q = new PriorityQueue<>();
		for (int i = 1; i <= V; i++) {
			lines[i] = new ArrayList<>();
		}
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			lines[u].add(new Line(v, w));
		}
		
		// SOLVE
		Arrays.fill(distance, MAX);
		for (int i = 0; i < lines[K].size(); i++) { // K에서 모든방향으로 스탠바이
			Line line = lines[K].get(i);
			q.add(new Node(line, line.w));
			distance[line.v] = Math.min(distance[line.v], line.w);
		}
		bfs(); // 큐!
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= V; i++) {
			if (i == K)
				sb.append(0);
			else if (distance[i] == MAX)
				sb.append("INF");
			else
				sb.append(distance[i]);
			sb.append("\n");
		}
		//double time = (System.currentTimeMillis() - stt) / 1000.0; sb.append(time);
		//double mem = (rt.totalMemory() - rt.freeMemory() - before) / 1024.0 / 1024.0; sb.append(mem);
		System.out.println(sb.toString());
		
		br.close();
	}
	
	static void bfs() {
		
		while (!q.isEmpty()) {
			Node node = q.poll();
			Line before = node.line;
			if (visit[before.v])
				continue;
			
			visit[before.v] = true;
			for (int i = 0; i < lines[before.v].size(); i++) {
				Line line = lines[before.v].get(i);
				if (distance[line.v] <= node.length+line.w) // 지나온 경로가 더 길거나 같으면 스킵
					continue;
				distance[line.v] = node.length+line.w; // 지나온 경로가 더 짧으므로 저장
				q.add(new Node(line, node.length+line.w));
			}
		}
		
	}
	static class Node implements Comparable<Node> {
		Line line;
		int length;
		public Node(Line line, int length) {
			this.line = line;
			this.length = length;
		}
		@Override
		public int compareTo(Node o) {
			return this.length - o.length;
		}
	}
	static class Line {
		int v, w;
		public Line(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}

}
