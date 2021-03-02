import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SEA7333_DeliveryPartTimeJob {
	static int T, N;
	static int[] reqBox;
	public static void main(String[] args) throws Exception {
		// SWEA7333: 한솔이의 택배 아르바이트
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		for (int testcase = 1; testcase <= T; testcase++) {
			N = Integer.parseInt(br.readLine());
			reqBox = new int[N];
			for (int i = 0; i < N; i++) {
				int w = Integer.parseInt(br.readLine());
				reqBox[i] = (int)Math.ceil(50.0 / w) - 1;
			}
			Arrays.sort(reqBox);
			//sb.append(Arrays.toString(reqBox)).append("\n");
			
			int left = 0;
			int right = N-1;
			while (left <= right) {
				right -= reqBox[left];
				if (left > right) // 50kg 미달
					break;
				left++;
			}
			sb.append("#").append(testcase);
			sb.append(" ").append(left).append("\n");
		}
		
		System.out.print(sb.toString());
		
		br.close();
	}

}
