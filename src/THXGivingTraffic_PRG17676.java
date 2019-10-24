import java.util.*;

class THXGivingTraffic_PRG17676 {
	int[] dataStt, dataEnd;
	public int solution(String[] lines) {
		// programmers.co.kr/learn/courses/30/lessons/17676
		// 추석 트래픽 - programmers
		initTiming(lines);
		int max = 0, stt = 0, cnt = 0;
		for (int i = 0; i < dataStt.length; i++) {
			while(dataStt[i] - 1000 + 1 > dataEnd[stt]) {
				cnt--;
				stt++;
			}
			cnt++;
			max = Math.max(max, cnt);
		}
		return max;
	}
	
	void initTiming(String[] lines) {
		dataStt = new int[lines.length];
		dataEnd = new int[lines.length];
		for (int i = 0; i < lines.length; i++) {
			String[] in = lines[i].split(" ");
			int timing = stringToTiming(in[1]);
			int diff = stringToDiff(in[2]);
			dataStt[i] = timing-diff+1;
			dataEnd[i] = timing;
		}
		Arrays.sort(dataStt);
		Arrays.sort(dataEnd);
	}

	int stringToDiff(String str) {
		return (int)(Double.parseDouble(str.replace("s", "")) * 1000);
	}
	
	int stringToTiming(String str) {
		int timing = 0;
		StringTokenizer st = new StringTokenizer(str, ":");
		timing += Integer.parseInt(st.nextToken())*3600000;
		timing += Integer.parseInt(st.nextToken())*60000;
		timing += Double.parseDouble(st.nextToken())*1000;
		return timing;
	}
}