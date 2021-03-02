import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16986_InsidersRSP {
	static int N, K;
	static int[][] A;
	static int[][] actionInfo; // 각 사람이 내는 손동작 순서
	static int[] indexes; // 0:score, 1:indexes
	static int[] scores;
	static int win, lose;
	static boolean winFlag;
	public static void main(String[] args) throws IOException {
		// 완전탐색
		// DFS를 돌리되, 지우의 동작이 겹치지 않도록 visit을 변수로 던진다
		// 한 게임이 끝날 때마다 가위바위보의 결과를 기록하자
		// 셋 중 한명이라도 우승하면 종료
		// 20경기가 끝나도 종료
		
		// 지우:0 | 경희:1 | 민호:2
		
		// 주의!) 주어지는 경희와 민호의 손동작 배열은 [자신이 참여하는 20경기에서 낼 손동작의 순서]이다.
		//		 (회차별로 띄엄띄엄 내는것이 아님..)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		A = new int[N+1][N+1];
		scores = new int[3];
		indexes = new int[3];
		actionInfo = new int[3][20];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 1; i <= 2; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 20; j++) {
				actionInfo[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// SOLVE
		playGame(0, 0, 1, 2);
		if (winFlag)
			System.out.println(1);
		else
			System.out.println(0);
		
		br.close();
	}
	
	static void playGame(int used, int p1, int p2, int other) {
		if (scores[p1] >= K) {
			if (p1 == 0)
				winFlag = true;
			return;
		}
		
		if (winFlag)
			return;
		
		int winner, loser;
		if (p1 == 0 || p2 == 0) {
			for (int card = 1; card <= N; card++) {
				int useNow = 1 << (card-1);
				if ((used & useNow) > 0)
					continue;
				int idx = indexes[0];
				actionInfo[0][idx] = card;
				
				getResult(p1, p2);
				winner = win;
				loser = lose;
				
				update(winner, loser);
				playGame(used|useNow, winner, other, loser);
				rollBack(winner, loser);
			}
		} else {
			getResult(p1, p2);
			winner = win;
			loser = lose;
			
			update(winner, loser);
			playGame(used, winner, other, loser);
			rollBack(winner, loser);
		}
	}
	
	public static void getResult(int p1, int p2) {
		int p1Index = indexes[p1];
		int p2Index = indexes[p2];
		int p1Card = actionInfo[p1][p1Index];
		int p2Card = actionInfo[p2][p2Index];
		
		if (A[p1Card][p2Card] == 2) {
			win = p1;
			lose = p2;
		} else if (A[p1Card][p2Card] == 0) {
			win = p2;
			lose = p1;
		} else {
			win = Math.max(p1, p2);
			lose = Math.min(p1, p2);
		}
	}
	public static void update(int winner, int loser) {
		// update indexes
		indexes[winner]++;
		indexes[loser]++;
		
		// update score
		scores[winner]++;
	}
	public static void rollBack(int winner, int loser) {
		indexes[winner]--;
		indexes[loser]--;
		
		scores[winner]--;
	}
}
