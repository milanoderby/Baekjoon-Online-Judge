import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < T; testCase++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(tokenizer.nextToken());
            int K = Integer.parseInt(tokenizer.nextToken());

            List<Integer>[] graph = new List[N + 1];
            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
            }
            int[] timeToConstruct = new int[N + 1];
            tokenizer = new StringTokenizer(br.readLine());
            for (int building = 1; building <= N; building++) {
                timeToConstruct[building] = Integer.parseInt(tokenizer.nextToken());
            }

            int[] countOfEntry = new int[N + 1];
            for (int countOfEdge = 0; countOfEdge < K; countOfEdge++) {
                tokenizer = new StringTokenizer(br.readLine());
                int src = Integer.parseInt(tokenizer.nextToken());
                int dest = Integer.parseInt(tokenizer.nextToken());

                graph[src].add(dest);
                countOfEntry[dest]++;
            }

            int W = Integer.parseInt(br.readLine());

            int[] constructionCompletionTime = new int[N + 1];
            PriorityQueue<Integer> buildingQueue = new PriorityQueue<>(Comparator.comparingInt(building -> constructionCompletionTime[building]));

            int currentTime = 0;
            for (int building = 1; building <= N; building++) {
                if (countOfEntry[building] == 0) {
                    constructionCompletionTime[building] = currentTime + timeToConstruct[building];
                    buildingQueue.offer(building);
                }
            }

            while (!buildingQueue.isEmpty()) {
                int currentBuilding = buildingQueue.poll();
                currentTime = constructionCompletionTime[currentBuilding];
                if (currentBuilding == W) {
                    break;
                }

                for (Integer nextBuilding : graph[currentBuilding]) {
                    countOfEntry[nextBuilding]--;
                    if (countOfEntry[nextBuilding] == 0) {
                        constructionCompletionTime[nextBuilding] = currentTime + timeToConstruct[nextBuilding];
                        buildingQueue.offer(nextBuilding);
                    }
                }
            }

            bw.append(constructionCompletionTime[W] + "\n");
        }
        bw.flush();
    }
}