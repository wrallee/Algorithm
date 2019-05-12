import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class VillageGroups_SEA7465 {
	static int T, N, M;
	static boolean[] visit;
	static boolean[][] relation;
	static Queue<Integer> q;
	public static void main(String[] args) throws Exception {
		// BFS
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		q = new LinkedList<>();
		T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			
			// INIT
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			visit = new boolean[N+1];
			relation = new boolean[N+1][N+1];
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int fst = Integer.parseInt(st.nextToken());
				int snd = Integer.parseInt(st.nextToken());
				relation[fst][snd] = relation[snd][fst] = true;
			}
			
			// SOLVE
			int cnt = 0;
			for (int i = 1; i <= N; i++) {
				if (visit[i])
					continue;
				cnt++;
				bfs(i);
			}
			sb.append("#").append(tc);
			sb.append(" ").append(cnt).append("\n");
		}
		
		System.out.println(sb.toString());
		
		br.close();
	}
	
	static void bfs(int stt) {
		q.offer(stt);
		visit[stt] = true;
		while (!q.isEmpty()) {
			int node = q.poll();
			for (int i = 1; i <= N; i++) {
				if (visit[i])
					continue;
				if (!relation[node][i])
					continue;
				
				q.offer(i);
				visit[i] = true;
			}
		}
	}

}
