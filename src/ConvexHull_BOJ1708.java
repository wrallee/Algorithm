import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

public class ConvexHull_BOJ1708 {
	static int N;
	static Dot[] dots;
	static Stack<Integer> stack;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		dots = new Dot[N+1];
		int stdIdx = 0;
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			dots[i] = new Dot(y, x);
			if (y <= dots[stdIdx].y) {
				if (y == dots[stdIdx].y) {
					stdIdx = x < dots[stdIdx].x ? i : stdIdx;
				} else {
					stdIdx = i;
				}
			}
		}
		dots[N] = dots[stdIdx];
		dots[stdIdx] = dots[0];
		dots[0] = dots[N];
		Arrays.sort(dots, 1, N, new Comparator<Dot>() {
			@Override
			public int compare(Dot n2, Dot n3) {
				int ccw = ccw(dots[0], n2, n3);
				if (ccw == 0) {
					return dots[0].dist(n2) > dots[0].dist(n3) ? 1 : -1;
				} else {
					return (-1)*ccw;
				}
			}
		});
		//System.out.println(Arrays.toString(dots));
		
		stack = new Stack<>();
		stack.push(0);
		stack.push(1);
		
		int nxt = 2;
		while (nxt < N) {
			while (stack.size() >= 2) {
				int fst, snd;
				snd = stack.pop();
				fst = stack.peek();
				
				if (ccw(dots[fst], dots[snd], dots[nxt]) > 0) {
					stack.push(snd);
					break;
				}
			}
			
			stack.push(nxt++);
		}
		System.out.println(stack.size());
		
		
		br.close();
	}
	
	static int ccw(Dot d1, Dot d2, Dot d3) {
		long ccw = d1.x * d2.y + d2.x * d3.y + d3.x * d1.y
				- (d1.y * d2.x + d2.y * d3.x + d3.y * d1.x);
		if (ccw == 0)
			return 0;
		else
			return ccw > 0 ? 1 : -1;
	}
	
	static class Dot {
		long y, x;
		public Dot(long y, long x) {
			this.y = y;
			this.x = x;
		}
		public long dist(Dot dot) {
			return (long)(Math.pow(this.y - dot.y, 2)
					+ Math.pow(this.x - dot.x, 2));
		}
	}

}
