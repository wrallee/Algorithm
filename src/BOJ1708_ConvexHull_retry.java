import java.io.*;
import java.util.*;

public class BOJ1708_ConvexHull_retry {
    static class Dot {
        long y, x;

        public Dot(long y, long x) {
            this.y = y;
            this.x = x;
        }

        public long getDist(Dot other) {
            return (long) (Math.pow(this.y - other.y, 2)
                    + Math.pow(this.x - other.x, 2));
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int std = 0;

        Dot[] dots = new Dot[N];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long y = Long.parseLong(st.nextToken());
            long x = Long.parseLong(st.nextToken());
            dots[i] = new Dot(y, x);
            if (y <= dots[std].y) {
                if (y == dots[std].y) {
                    std = x < dots[std].x ? i : std;
                } else {
                    std = i;
                }
            }
        }

        Dot tmp = dots[std];
        dots[std] = dots[0];
        dots[0] = tmp;

        Arrays.sort(dots, 1, N, (d1, d2) -> {
            int ccw = getCCW(dots[0], d1, d2);
            if (ccw == 0) {
                return d1.getDist(dots[0]) > d2.getDist(dots[0]) ? 1 : -1;
            }
            return (-1) * ccw;
        });

        System.out.println(getConvexHull(dots, N));

        br.close();
    }

    static int getConvexHull(Dot[] dots, int N) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(1);
        for (int next = 2; next < N; next++) {
            while (stack.size() >= 2) {
                int first, second;
                second = stack.pop();
                first = stack.peek();
                if (getCCW(dots[first], dots[second], dots[next]) > 0) {
                    stack.push(second); // second 검증 완료
                    break;
                }
            }

            stack.push(next); // 검증 대상 추가
        }

        return stack.size();
    }

    static int getCCW(Dot d1, Dot d2, Dot d3) {
        long ccw = (d1.x * d2.y + d2.x * d3.y + d3.x * d1.y)
                - (d1.y * d2.x + d2.y * d3.x + d3.y * d1.x);
        return ccw == 0 ? 0 : (ccw > 0 ? 1 : -1);
    }
}