import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());
            for (int i = 0; i < T; i++) {
                int N = Integer.parseInt(br.readLine());
                System.out.println(getAppleNum(N));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static String getAppleNum(int divisor) {
        if (divisor == 1) {
            return "1";
        }

        Queue<Integer> remainderQueue = new LinkedList<>();
        Queue<String> appleNumQueue = new LinkedList<>();
        boolean[] visited = new boolean[divisor];

        remainderQueue.add(1);
        appleNumQueue.add("1");
        visited[1] = true;

        while(!remainderQueue.isEmpty()){
            int curRemainder = remainderQueue.remove();
            String curAppleNum = appleNumQueue.remove();

            int nextRemainder1 = ((curRemainder % divisor) * (10 % divisor)) % divisor;
            String nextAppleNum1 = curAppleNum + "0";

            if (nextRemainder1 == 0) {
                return nextAppleNum1;
            }

            if (!visited[nextRemainder1]) {
                remainderQueue.add(nextRemainder1);
                appleNumQueue.add(nextAppleNum1);
                visited[nextRemainder1] = true;
            }

            int nextRemainder2 = (nextRemainder1 + 1) % divisor;
            String nextAppleNum2 = curAppleNum + "1";
            if (nextRemainder2 == 0) {
                return nextAppleNum2;
            }

            if (!visited[nextRemainder2]) {
                remainderQueue.add(nextRemainder2);
                appleNumQueue.add(nextAppleNum2);
                visited[nextRemainder2] = true;
            }
        }
        return "BRAK";
    }
}
