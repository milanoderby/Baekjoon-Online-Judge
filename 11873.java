import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    private static int answer;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int N = Integer.parseInt(tokenizer.nextToken());
                int M = Integer.parseInt(tokenizer.nextToken());

                if (N == 0 && M == 0) {
                    break;
                }

                int[][] matrix = new int[N][M];
                for (int i = 0; i < N; i++) {
                    tokenizer = new StringTokenizer(br.readLine());
                    for (int j = 0; j < M; j++) {
                        matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
                    }
                }

                int[][] block = new int[N][M];
                for (int j = 0; j < M; j++) {
                    for (int i = 0; i < N; i++) {
                        if (i == 0) {
                            block[i][j] = matrix[i][j];
                            continue;
                        }

                        if (matrix[i][j] == 0) {
                            block[i][j] = 0;
                        } else {
                            block[i][j] = block[i - 1][j] + 1;
                        }
                    }
                }

                answer = 0;
                for (int i = 0; i < N; i++) {
                    getDimension(i, block, M);
                }
                System.out.println(answer);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Block {
        int index;
        int height;

        public Block(int index, int height) {
            this.index = index;
            this.height = height;
        }
    }

    private static void getDimension(int i, int[][] block, int M) {
        Stack<Block> blockStack = new Stack<>();
        int[] right = new int[M];

        for (int j = 0; j < M; j++) {
            Block cur = new Block(j, block[i][j]);
            while (!blockStack.isEmpty()) {
                Block top = blockStack.peek();
                if (top.height <= cur.height) {
                    break;
                }
                right[top.index] = cur.index - top.index;
                blockStack.pop();
            }
            blockStack.push(cur);
        }

        while (!blockStack.isEmpty()) {
            Block top = blockStack.peek();
            right[top.index] = M - top.index;
            blockStack.pop();
        }

        int[] left = new int[M];
        for (int j = M - 1; j >= 0; j--) {
            Block cur = new Block(j, block[i][j]);
            while(!blockStack.isEmpty()) {
                Block top = blockStack.peek();
                if (top.height <= cur.height) {
                    break;
                }
                left[top.index] = top.index - cur.index;
                blockStack.pop();
            }
            blockStack.push(cur);
        }

        while (!blockStack.isEmpty()) {
            Block top = blockStack.peek();
            left[top.index] = top.index + 1;
            blockStack.pop();
        }

        for (int j = 0; j < M; j++) {
            int width = left[j] + right[j] - 1;
            int height = block[i][j];
            int dimension = width * height;
            if (answer < dimension) {
                answer = dimension;
            }
        }
    }
}