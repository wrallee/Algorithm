import java.io.*;
import java.util.*;

public class UnidentifiedSpot_BOJ9370 {
	static int T,n,m,t,s,g,h;
	static int[] dist;
	static boolean[] check;
	
	static List<Integer> target;
	static List<List<Node>> edge;
	static PriorityQueue<Node> pq;
	public static void main(String[] args) throws Exception {
		// 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= T; i++) {
			init(br);
			solve(sb);
		}
		System.out.print(sb.toString());
		
		br.close();
	}
	
	static void solve(StringBuilder sb) {
		while (!pq.isEmpty()) {
			Node prev = pq.poll();
			
			List<Node> list = edge.get(prev.num);
			for (Node next : list) {
				int newRoute = dist[prev.num] + next.dist;
				if (newRoute > dist[next.num])
					continue;
				
				if (newRoute == dist[next.num])
					check[next.num] |= check[prev.num] | check(prev.num, next.num);
				else // newRoute < dist[next.num]
					check[next.num] = check[prev.num] | check(prev.num, next.num);
				
				dist[next.num] = newRoute;
				pq.add(new Node(next.num, dist[next.num]));
			}
		}
		
		for (int i : target) {
			sb.append(check[i] ? i + " " : "");
		}
		sb.append("\n");
	}
	
	static boolean check(int stt, int end) {
		return (stt == g && end == h)
				|| (stt == h && end == g);
	}
	
	static void init(BufferedReader br) throws Exception {
		pq = new PriorityQueue<>();
		edge = new ArrayList<>();
		target = new ArrayList<>();
		
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		s = Integer.parseInt(st.nextToken());
		g = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());
		
		dist = new int[n+1];
		check = new boolean[n+1];
		pq.add(new Node(s, 0));
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[s] = 0;
		
		edge.add(null);
		for (int i = 0; i < n; i++) {
			edge.add(new ArrayList<>());
		}
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			edge.get(a).add(new Node(b, d));
			edge.get(b).add(new Node(a, d));
		}
		for (int i = 0; i < t; i++) {
			target.add(Integer.parseInt(br.readLine()));
		}
		Collections.sort(target);
	}
	
	static class Node implements Comparable<Node> {
		int num;
		int dist;
		public Node(int idx, int dist) {
			this.num = idx;
			this.dist = dist;
		}
		
		@Override
		public int compareTo(Node n) {
			return this.dist - n.dist;
		}
	}
}
