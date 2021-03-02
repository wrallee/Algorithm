import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeSet;
 
public class BOJ2568_ElectricWire {
    static int N, length;
    static LinkedList<Integer> dp;
    static HashMap<Integer, Integer> lines, p, rev;
    public static void main(String[] args) throws Exception {
        // LIS
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
         
        N = Integer.parseInt(br.readLine().trim());
        lines = new HashMap<>();
        rev = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            lines.put(a, b);
            rev.put(b, a);
        }
         
        dp = new LinkedList<>();
        p = new HashMap<>();
         
        TreeSet<Integer> ts = new TreeSet<>(lines.keySet());
        //System.out.println(keySet);
         
        for (Integer i : ts) {
            Integer target = lines.get(i);
            int newIdx = getIndex(target);
            if (dp.size() == 0 || newIdx == dp.size())
                dp.add(target);
            else
                dp.set(newIdx, target);
            p.put(target, newIdx==0 ? -1 : dp.get(newIdx-1));
            //System.out.println(dp);
        }
         
        //System.out.println(p);
        int target = dp.getLast();
        while (target != -1) {
            lines.remove(rev.get(target));
            target = p.get(target);
        }
         
        StringBuilder sb = new StringBuilder();
        sb.append(lines.size()).append("\n");
        for (Integer integer : lines.keySet()) {
            sb.append(integer).append("\n");
        }
         
        System.out.print(sb.toString());
         
        br.close();
    }
     
    static int getIndex(int value) {
        if (dp.size() == 0 || dp.getFirst() > value)
            return 0;
        if (dp.getLast() < value)
            return dp.size();
         
        return Math.abs(Collections.binarySearch(dp, value) + 1);
    }
 
}