import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int L = Integer.parseInt(tokenizer.nextToken());

        int[] input = new int[N];
        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int[] answer = new int[N];
        Deque<Number> deque = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            while (!deque.isEmpty()) {
                if (deque.peekLast().number >= input[i]) {
                    deque.pollLast();
                } else {
                    break;
                }
            }
            deque.add(new Number(i, input[i]));
            if (deque.peekFirst().index <= i - L) {
                deque.pollFirst();
            }

            answer[i] = deque.peekFirst().number;
        }

        for (int i = 0; i < N; i++) {
            bw.append(answer[i] + " ");
        }
        bw.flush();
    }

    private static class Number {
        private int index;
        private int number;

        public Number(int index, int number) {
            this.index = index;
            this.number = number;
        }
    }
}