import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16958_Teleport {
	static int N, M;
	static int teleportDistance;
	static City[] cities;
	
	static final int MAX = 2001;
	public static void main(String[] args) throws Exception {
		// 보통 도시의 이동시간은 |r1-r2|+|c1-c2|
		// 특별한 도시는 텔레포트 가능하며 이동시간은 T
		// 도시->도시 이동
		// 현재도시와 타겟도시의 상태(특별)값 비교
		// T와 일반이동시간 비교
		// DFS+메모이제이션으로 최소 이동거리를 찾는다
		// -> 실패
		
		// 다익스트라로 진행해보자!
		// node == end때 종료시켜서 답이 틀린다.
		// 이부분을 지워도 시간초과
		// -> 실패
		
		// a. 텔레포트는 두 번 수행 할 이유가 없다
		//   i. A->B->C로 텔레포트 한다면 A->C로 가능하기 때문
		// b. 걸어서 갈 경우 다른 지점을 방문할 이유가 없다
		//   i. 시작점, 출발점을 기준으로 직사각형을 그려보면
		//   ii. 직사각형 내부일 경우 결국 똑같고, 외부일 경우 거리가 멀어진다
		// 1) A와 B가 모두 1이면 걷거나/텔레포트
		// 2) A만 1이면 B근처 가장 가까운 텔레포트 지점 확인
		// 3) B만 1이면 A근처 가장 가까운 텔레포트 지점 확인
		// 4) 둘다 0이면 각자 가장 가까운 텔레포트지점까지만 걷는다
		// 2,3,4를 모두 1과 비교한다(뭘 하든 직접이동보단 작아야 됨)
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//long stt = System.currentTimeMillis();
		
		// INIT
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		teleportDistance = Integer.parseInt(st.nextToken());
		cities = new City[N+1];
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			cities[i] = new City(i, s, x, y);
		}
		
		// SOLVE
		StringBuilder sb = new StringBuilder();
		M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int res = getDistance(A, B);
			sb.append(res).append("\n");
		}
		
		//double time = (System.currentTimeMillis() - stt) / 1000.0; sb.append(time);
		System.out.println(sb.toString());
		
		br.close();
	}
	
	static int getDistance(int A, int B) {
		City cityA = cities[A];
		City cityB = cities[B];
		int distance = getBetween(A, B);
		int tmpDistance = MAX;
		if (cityA.s == 1 && cityB.s == 1)
			// 1, 1
			return distance;
		else if (cityA.s == 1) {
			// 1, 0
			tmpDistance = teleportDistance + goTeleportPoint(B);
		} else if (cityB.s == 1) {
			// 0, 1
			tmpDistance = goTeleportPoint(A) + teleportDistance;
		} else { // 둘다 텔레포트 불가
			// 0, 0
			tmpDistance = goTeleportPoint(A) + teleportDistance + goTeleportPoint(B);
		}
		distance = Math.min(distance, tmpDistance);
		return distance;
	}
	
	static int getBetween(int A, int B) {
		City cityA = cities[A];
		City cityB = cities[B];
		int between = Math.abs(cityA.x - cityB.x)
					+ Math.abs(cityA.y - cityB.y);
		if ((cityA.s & cityB.s) == 1)
			between = Math.min(between, teleportDistance);
		
		return between;
	}
	
	static int goTeleportPoint(int normal) {
		int distance = MAX;
		for (int i = 1; i <= N; i++) {
			City target = cities[i];
			if (target.s == 0) // 텔레포트 포인트만 확인
				continue;
			distance = Math.min(distance, getBetween(normal, i));
		}
		return distance;
	}
	
	static class City {
		int num, s, x, y;
		public City(int num, int s, int x, int y) {
			this.num = num;
			this.s = s;
			this.x = x;
			this.y = y;
		}
	}
	
	/*
	static void bfs(int start, int end) {
		if (length[start][end] != MAX) // 이미 탐색했다면 종료
			return;
		
		// 매번 큐를 새로 생성하기 때문에 clear가 필요없음
		q = new PriorityQueue<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return length[start][o1] - length[start][o2];
			}
		});
		
		q.add(start);
		while (!q.isEmpty()) {
			int node = q.poll();
			
			if (node == end) // 마지막점까지 도달했다면 종료 // 도달했다고 끝난건아니지..
				return;
			
			if (visit[start][node])
				continue;
			visit[start][node] = true;
			
			for (int i = 1; i <= N; i++) {
				if (i == node)
					continue;
				int newLength = length[start][node]+adj[node][i];
				if (length[start][i] <= newLength)
					continue;
				length[start][i] = newLength;
				q.offer(i);
			}
		}
		
	}
	*/
	/*
	static int dfs(int bef) {
		if (bef == B)
			return 0;
		if (memo[bef][B] != MAX)
			return memo[bef][B];
		
		memo[bef][B] = getBetween(bef, B);
		
		int dist = MAX;
		for (int i = 1; i <= N; i++) {
			int between = getBetween(bef, i);
			dist = Math.min(dist, between+dfs(i));
		}
		
		return memo[bef][B] = Math.min(memo[bef][B], dist);
	}
	*/

}