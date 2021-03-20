import java.io.*;
import java.util.*;

public class BOJ1194_GoWhenTheMoonRising {
    static int N, M, H, res;
    static Point stt;
    static char[][] map;
    static boolean[][][] visit;

    static final int MAX = 320001;
    static final int[] dy = {-1, 1, 0, 0};
    static final int[] dx = { 0, 0, 1,-1};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");

        N = Integer.parseInt(in[0]);
        M = Integer.parseInt(in[1]);
        H = 7;

        map = new char[N][M];
        visit = new boolean[N][M][1 << H]; // 50*50*128
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '0') stt = new Point(i, j, 0, 0);
                Arrays.fill(visit[i][j], false);
            }
        }

        res = MAX;
        bfs();

        System.out.println(res == MAX ? -1 : res);


        br.close();
    }

    static void bfs() {
        Queue<Point> q = new LinkedList<>();
        q.add(stt);
        visit[stt.y][stt.x][0] = true;
        while (!q.isEmpty()) {
            Point p = q.poll();

            // 도착
            if (map[p.y][p.x] == '1') {
                res = Math.min(res, p.cnt);
                continue;
            }

            // 이동
            for (int i = 0; i < 4; i++) {
                int nY = p.y + dy[i];
                int nX = p.x + dx[i];

                // 이동불가 조건
                if (nY < 0 || nX < 0 || nY >= N || nX >= M || map[nY][nX] == '#')
                    continue;
                if ('A' <= map[nY][nX] && map[nY][nX] <= 'F' && (p.visit & keyToInt(map[nY][nX])) == 0)
                    continue;

                // Visit 설정
                int newVisit = p.visit;
                if ('a' <= map[nY][nX] && map[nY][nX] <= 'f') {
                    newVisit = p.visit | keyToInt(map[nY][nX]);
                }

                // 이동 실행
                if (visit[nY][nX][newVisit])
                    continue;

                visit[nY][nX][newVisit] = true;
                q.offer(new Point(nY, nX, p.cnt + 1, newVisit));
            }
        }
    }

    static class Point {
        int y, x, cnt, visit;
        public Point(int y, int x, int cnt, int visit) {
            this.y = y;
            this.x = x;
            this.cnt = cnt;
            this.visit = visit;
        }
    }

    static int keyToInt(char key) {
        switch (key) {
            case 'a': case 'A': return 1;
            case 'b': case 'B': return 1 << 1;
            case 'c': case 'C': return 1 << 2;
            case 'd': case 'D': return 1 << 3;
            case 'e': case 'E': return 1 << 4;
            case 'f': case 'F': return 1 << 5;
        }
        return -1;
    }
}
