import java.io.*;
import java.util.*;

public class BOJ9376_JailBreak {
    static int T, H, W, min;
    static char[][] map;
    static List<Point> stt;

    static int[] dy = {1,-1, 0, 0};
    static int[] dx = {0, 0, 1,-1};

    static final int MAX_VAL = 10001;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            String[] input = br.readLine().split(" ");

            // INIT
            min = MAX_VAL;
            H = Integer.parseInt(input[0]);
            W = Integer.parseInt(input[1]);
            map = new char[H+2][W+2];

            int[][] resMap = new int[H+2][W+2]; // 결과계산용 맵
            int[][][] visit = new int[3][H+2][W+2]; // 죄수1, 죄수2, 함께여는문 -> 3층짜리 2차원 배열 생성
            for (int i = 0; i < 3; i++) { // 방문확인을 위한 MAX_VAL 초기화
                for (int j = 0; j < H+2; j++) {
                    Arrays.fill(visit[i][j], MAX_VAL);
                }
            }

            stt = new ArrayList<>(); // 3층 각각의 시작지점을 담은 리스트
            stt.add(new Point(0, 0));
            for (int i = 1; i <= H; i++) {
                String line = br.readLine();
                for (int j = 1; j <= W; j++) {
                    map[i][j] = line.charAt(j-1);
                    if (map[i][j] == '$')
                        stt.add(new Point(i, j));

                }
            }
			/*
			// INPUT TEST
			System.out.println(stt.toString());
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			*/

            // SOLVE
            for (int i = 0; i < 3; i++) { // 탐색
                bfs(stt.get(i), visit[i]);
            }
            for (int i = 0; i < H+2; i++) { // 결과 계산
                for (int j = 0; j < W+2; j++) {
                    resMap[i][j] = visit[0][i][j]
                            + visit[1][i][j]
                            + visit[2][i][j];
                    if (map[i][j] == '*') // 벽은 -1 초기화 상태이므로 스킵
                        continue;
                    if (map[i][j] == '#') // 문은 동시에 한번만 열기 때문에 3번 중 2번을 빼준다
                        resMap[i][j] -= 2;
                    min = Math.min(min, resMap[i][j]);
                }
            }
            /*
			// VISIT TEST
			for (int k = 0; k < visit.length; k++) {
				System.out.println("Phase:"+k);
				for (int i = 0; i < visit[k].length; i++) {
					for (int j = 0; j < visit[k][i].length; j++) {
						System.out.print(resMap[i][j] + "\t");
					}
					System.out.println();
				}
			}
			*/

            System.out.println(min); // 출력

        }


        br.close();
    }

    static void bfs(Point start, int[][] visitMap) {
        Queue<Point> q = new LinkedList<>();
        visitMap[start.y][start.x] = 0;
        q.add(start);
        while (!q.isEmpty()) {
            Point p = q.poll();
            for (int i = 0; i < 4; i++) {
                int nY = p.y + dy[i];
                int nX = p.x + dx[i];
                if (nY >= H+2 || nX >= W+2 || nY < 0 || nX < 0)
                    continue;
                if (map[nY][nX] == '*')
                    continue;

                // 벽이 아닌 경우(빈 공간, 죄수, 문)
                int next;
                if (map[nY][nX] == '#') // 문일 경우
                    next = visitMap[p.y][p.x] + 1;
                else // 빈 공간이나 죄수일 경우
                    next = visitMap[p.y][p.x];

                if (visitMap[nY][nX] == MAX_VAL) { // 탐색을 안 한 경우
                    visitMap[nY][nX] = next;
                    q.add(new Point(nY, nX));
                    continue;
                }

                if (visitMap[nY][nX] > next) { // 기록할 값이 기존값보다 작을 경우
                    visitMap[nY][nX] = next;
                    q.add(new Point(nY, nX));
                }
            }
        }
    }

    static class Point {
        int y, x;
        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
        @Override
        public String toString() {
            return y + " " + x;
        }
    }
}