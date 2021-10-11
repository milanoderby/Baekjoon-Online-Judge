import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());

        List<Integer>[] graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        int[] countOfEntry = new int[N + 1];
        for (int edgeIndex = 0; edgeIndex < M; edgeIndex++) {
            tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken());
            int dest = Integer.parseInt(tokenizer.nextToken());

            graph[src].add(dest);
            countOfEntry[dest]++;
        }

        PriorityQueue<Integer> problemQueue = new PriorityQueue<>();

        for (int problem = 1; problem <= N; problem++) {
            if (countOfEntry[problem] == 0) {
                problemQueue.offer(problem);
            }
        }

        while (!problemQueue.isEmpty()) {
            int currentProblem = problemQueue.poll();
            bw.append(currentProblem + " ");

            for (Integer nextProblem : graph[currentProblem]) {
                countOfEntry[nextProblem]--;
                if (countOfEntry[nextProblem] == 0) {
                    problemQueue.offer(nextProblem);
                }
            }
        }
        bw.flush();
    }
}