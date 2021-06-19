import java.io.*;
import java.util.*;

public class Main {
    private static int[] countOfMinOperation;
    private static int[] prevNum;
    private static int MAX_NUM = 1000001;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        countOfMinOperation = new int[MAX_NUM];
        for (int i = 0; i < MAX_NUM; i++) {
            countOfMinOperation[i] = MAX_NUM;
        }
        prevNum = new int[MAX_NUM];

        getAnswer(N);
        bw.append(countOfMinOperation[N] + "\n");

        List<Integer> prevNumPath = new ArrayList<>();
        for (int i = N; i != -1; i = prevNum[i]) {
            prevNumPath.add(i);
        }

        for (Integer prev : prevNumPath) {
            bw.append(prev + " ");
        }
        bw.flush();
    }

    private static void getAnswer(int N) {
        countOfMinOperation[1] = 0;
        prevNum[1] = -1;

        Queue<Number> q = new LinkedList<>();
        q.add(new Number(1, 0));

        while (!q.isEmpty()) {
            Number cur = q.poll();
            if (cur.num == N) {
                return;
            }

            int nextNum1 = cur.num + 1;
            int nextNum2 = cur.num * 2;
            int nextNum3 = cur.num * 3;
            int nextCountOfOperation = cur.countOfOperation + 1;

            if (nextNum1 < MAX_NUM && countOfMinOperation[nextNum1] > nextCountOfOperation) {
                countOfMinOperation[nextNum1] = nextCountOfOperation;
                prevNum[nextNum1] = cur.num;
                q.add(new Number(nextNum1, nextCountOfOperation));
            }

            if (nextNum2 < MAX_NUM && countOfMinOperation[nextNum2] > nextCountOfOperation) {
                countOfMinOperation[nextNum2] = nextCountOfOperation;
                prevNum[nextNum2] = cur.num;
                q.add(new Number(nextNum2, nextCountOfOperation));
            }

            if (nextNum3 < MAX_NUM && countOfMinOperation[nextNum3] > nextCountOfOperation) {
                countOfMinOperation[nextNum3] = nextCountOfOperation;
                prevNum[nextNum3] = cur.num;
                q.add(new Number(nextNum3, nextCountOfOperation));
            }
        }
    }

    private static class Number {
        private int num;
        private int countOfOperation;

        public Number(int num, int countOfOperation) {
            this.num = num;
            this.countOfOperation =  countOfOperation;
        }
    }
}