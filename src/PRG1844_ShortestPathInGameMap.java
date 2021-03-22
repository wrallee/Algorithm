import java.util.*;

public class PRG1844_ShortestPathInGameMap {
    public static void main(String[] args) {
        int[][] maps = {{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,0},{0,0,0,0,1}};
        PRG1844_ShortestPathInGameMap s = new PRG1844_ShortestPathInGameMap();

        System.out.println(s.solution(maps));
    }
    public int solution(int[][] maps) {
        int[] dy = {-1, 1, 0, 0};
        int[] dx = { 0, 0, 1,-1};

        int N = maps.length;
        int M = maps[0].length;

        boolean[][] visit = new boolean[N][M];
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(0, 0, 1));

        while (!q.isEmpty()) {
            Point p = q.poll();
            if (p.y == N-1 && p.x == M-1) {
                return p.dist;
            }
            for (int i = 0; i < 4; i++) {
                int nY = p.y + dy[i];
                int nX = p.x + dx[i];
                if (nY < 0 || nX < 0 || nY >= N || nX >= M)
                    continue;
                if (maps[nY][nX] == 0)
                    continue;
                if (visit[nY][nX])
                    continue;
                visit[nY][nX] = true;
                q.offer(new Point(nY, nX, p.dist+1));
            }
        }

        return -1;
    }
    class Point {
        int y, x, dist;
        public Point(int y, int x, int dist) {
            this.y = y;
            this.x = x;
            this.dist = dist;
        }
    }
}
