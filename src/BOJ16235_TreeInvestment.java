import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class TreeInvestment_BOJ16235 {
	static int N, M, K;
	static int[][] A, ground;
	static Queue<Tree> dead, seed;
	static PriorityQueue<Tree> live, next;
	static int[] dr = {1,-1, 0, 0, 1,-1, 1,-1};
	static int[] dc = {0, 0, 1,-1, 1, 1,-1,-1};
	public static void main(String[] args) throws Exception {
		// BOJ16235: 나무 재테크
		// 봄, 여름, 가을, 겨울
		// 한칸에 나무 여러개 가능
		// ----------봄----------
		// 어린나이부터 나이만큼 양분을 먹고 나이 증가
		// 양분 없으면 죽음
		// ---------여름----------
		// 죽은나무의나이/2가 양분이 됨
		// ---------가을----------
		// 나무의 나이가 5의 배수일 경우 8개 칸에 번식
		// ---------겨울----------
		// 기계가 양분을 추가
		
		// K년 후 살아있는 나무의 갯수
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 맵 크기
		M = Integer.parseInt(st.nextToken()); // 나무갯수
		K = Integer.parseInt(st.nextToken());
		A = new int[N][N];
		ground = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		live = new PriorityQueue<>();
		next = new PriorityQueue<>();
		dead = new LinkedList<>();
		seed = new LinkedList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int age = Integer.parseInt(st.nextToken());
			live.add(new Tree(r-1, c-1, age));
		}
		for (int i = 0; i < N; i++) {
			Arrays.fill(ground[i], 5);
		}
		
		// SOLVE
		for (int i = 0; i < K; i++) {
			farm();
		}
		System.out.println(live.size());
		
		
		br.close();
	}
	
	static void farm () {
		// 봄
		while (!live.isEmpty()) {
			Tree tree = live.poll();
			int r = tree.r;
			int c = tree.c;
			if (ground[r][c] < tree.age) {
				// dead
				dead.offer(tree);
			} else {
				ground[r][c] -= tree.age++;
				next.offer(tree);
				if (tree.age % 5 == 0)
					seed.offer(tree);
			}
		}
		// 여름
		while (!dead.isEmpty()) {
			Tree tree = dead.poll();
			int r = tree.r;
			int c = tree.c;
			ground[r][c] += tree.age / 2;
		}
		// 가을
		while (!seed.isEmpty()) {
			Tree tree = seed.poll();
			int r = tree.r;
			int c = tree.c;
			for (int i = 0; i < 8; i++) {
				int nR = r + dr[i];
				int nC = c + dc[i];
				if (outOfBound(nR, nC))
					continue;
				next.offer(new Tree(nR, nC, 1));
			}
		}
		// 겨울
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ground[i][j] += A[i][j];
			}
		}
		
		PriorityQueue<Tree> tmp = live;
		live = next;
		next = tmp;
	}
	
	static boolean outOfBound(int r, int c) {
		if (r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
	
	static class Tree implements Comparable<Tree> {
		int r, c, age;
		public Tree(int r, int c, int age) {
			this.r = r;
			this.c = c;
			this.age = age;
		}
		@Override
		public int compareTo(Tree o) {
			return this.age - o.age;
		}
	}

}
