import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(tokenizer.nextToken());
        }

        Stack<Number> stack = new Stack<>();
        int[] rightBigNum = new int[N];
        for (int i = 0; i < N; i++) {
            rightBigNum[i] = -1;
        }

        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty()) {
                Number top = stack.peek();
                if (top.num < nums[i]) {
                    rightBigNum[top.index] = nums[i];
                    stack.pop();
                } else {
                    break;
                }
            }
            stack.push(new Number(i, nums[i]));
        }

        for (int i = 0; i < N; i++) {
            bw.append(rightBigNum[i] + " ");
        }
        bw.flush();
    }
    
    private static class Number {
        private int index;
        private int num;

        public Number(int index, int num) {
            this.index = index;
            this.num = num;
        }
    }
}