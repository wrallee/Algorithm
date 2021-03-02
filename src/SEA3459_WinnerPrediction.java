import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SEA3459_WinnerPrediction {
	static int T;
	static long N;
	public static void main(String[] args) throws Exception {
		// SWEA3459: 승자 예측하기
		// Alice로 끝난다면 절반 이상부터 확인, Bob으로 끝난다면 절반 이하부터 확인
		// Alice로 끝날 때 -> 절반 이상이면 Alice 승리
		// Bob으로 끝날 때 -> 절반 이하라면 Alice 승리
		// 절반 이하 -> 절반 이상 -> 절반 이하 -> 절반 이상 유무를 확인하며 반복
		
		// aliceEnd가 true -> upCheck이 true -> Alice 승리
		// aliceEnd가 false -> upCheck이 false -> Alice 승리
		// --> aliceEnd XOR up == 0(두 플래그가 같으면) -> Alice 승리
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int testcase = 1; testcase <= T; testcase++) {
			N = Long.parseLong(br.readLine());
			int height = (int)Math.ceil(Math.log(N+1) / Math.log(2)) - 1;
			
			boolean aliceEnd;
			if ((height & 1) > 0) // 홀수층
				aliceEnd = true;
			else // 짝수층
				aliceEnd = false;
			
			//long capa = (int)Math.pow(2, height); // 1차시도: 여기서 틀렸다
			long capa = (long)Math.pow(2, height);
			
			long mid = capa;
			mid += capa /= 2;
			
			// 마지막이 Alice라면 up부터 시작
			boolean upCheck = aliceEnd;
			while (true) {
				if (capa == 1)
					break;
				
				if (upCheck) {
					if (N >= mid) // 절반 이상이면 승리
						break;
					else
						mid -= capa /= 2;
				} else {
					if (N < mid) // 절반 이하라면 승리
						break;
					else
						mid += capa /= 2;
				}
				upCheck = !upCheck;
			}
			
			sb.append("#").append(testcase).append(" ");
			if (aliceEnd ^ upCheck) // 다르면
				sb.append("Bob");
			else
				sb.append("Alice");
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
		
		br.close();
	}

}
