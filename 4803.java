import java.io.*;
import java.util.*;

public class Main {
    private static boolean hasCycle;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int numOfCase = 1;
        while (true) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(tokenizer.nextToken());
            int M = Integer.parseInt(tokenizer.nextToken());

            if (N == 0 & M == 0) {
                break;
            }

            boolean[] visited = new boolean[N + 1];
            Vector<Integer>[] myGraph = new Vector[N + 1];
            for (int i = 0; i <= N; i++) {
                myGraph[i] = new Vector<>();
            }

            for (int i = 0; i < M; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                int src = Integer.parseInt(tokenizer.nextToken());
                int dest = Integer.parseInt(tokenizer.nextToken());
                myGraph[src].add(dest);
                myGraph[dest].add(src);
            }

            int numOfTree = 0;
            for (int vertex = 1; vertex <= N; vertex++) {
                if (!visited[vertex]) {
                    hasCycle = false;
                    findCycle(-1, vertex, myGraph, visited);
                    if (!hasCycle) {
                        numOfTree++;
                    }
                }
            }

            bw.append("Case " + numOfCase + ": ");
            if (numOfTree == 0) {
                bw.append("No trees.\n");
            } else if (numOfTree == 1) {
                bw.append("There is one tree.\n");
            } else {
                bw.append("A forest of " + numOfTree + " trees.\n");
            }
            numOfCase++;
        }
        bw.flush();
    }

    private static void findCycle(int prev, int cur, Vector<Integer>[] myGraph, boolean[] visited) {
        visited[cur] = true;
        for (Integer next : myGraph[cur]) {
            if (!visited[next]) {
                findCycle(cur, next, myGraph, visited);
            } else {
                if (cur == next) {
                    hasCycle = true;
                }

                if (next != prev) {
                    hasCycle = true;
                }
            }
        }
    }
}