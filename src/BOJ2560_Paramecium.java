import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2560_Paramecium {
	static int a, b, d, N;
	static int std, save; // 기준 인덱스, 기준 합
	static int[] sum;
	static final int DIV = 1000;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		sum = new int[d]; // 0 ~ d-1살 까지 있다
		
		bottomUp();
		
		System.out.println((sum[std] - save + DIV) % DIV);
		
		br.close();
	}
	
	static void bottomUp() {
		std = d-1; // 기준 초기화
		sum[std] = 1; // 1마리 배치
		
		//printDay(0, std);
		for (int i = 1; i <= N; i++) {
			int newbie = (std-1 + d) % d;
			int nA = (std+a-1 + d) % d;
			int nB = (std+b-1 + d) % d;
			int last = sum[std];
			int diff = sum[nA] - sum[nB];
			
			save = sum[newbie];
			sum[newbie] = (last + diff + DIV) % DIV;
			
			std = newbie;
			//printDay(i, newbie);
		}
	}
	
	static void printDay(int day, int std) {
		System.out.print("DAY" + day + "|\t");
		for (int j = 0; j < d; j++) {
			int idx = (std+j) % d;
			if ((idx+1)%d == std)
				System.out.print((sum[idx] - save + DIV) % DIV + " ");
			else
				System.out.print((sum[idx] - sum[(idx+1)%d] + DIV) % DIV + " ");
		}
		System.out.print("| Total:" + (sum[std] - save + DIV) % DIV);
		System.out.println();
	}
}