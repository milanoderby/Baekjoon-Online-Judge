import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());

        List<Integer>[] directedGraph = new List[N + 1];
        for (int i = 0; i < N + 1; i++) {
            directedGraph[i] = new ArrayList<>();
        }

        int[] countOfEntry = new int[N + 1];
        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken());
            int dest = Integer.parseInt(tokenizer.nextToken());
            directedGraph[src].add(dest);
            countOfEntry[dest]++;
        }
        boolean[] visited = new boolean[N + 1];
        Queue<Integer> studentQueue = new ArrayDeque<>();
        for (int i = 1; i < N + 1; i++) {
            if (countOfEntry[i] == 0) {
                studentQueue.add(i);
                visited[i] = true;
            }
        }

        while (!studentQueue.isEmpty()) {
            int cur = studentQueue.poll();
            bw.append(cur + " ");
            for (Integer next : directedGraph[cur]) {
                countOfEntry[next]--;
                if (countOfEntry[next] == 0) {
                    studentQueue.add(next);
                }
            }
        }
        bw.flush();
    }
}