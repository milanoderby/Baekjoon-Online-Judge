import java.io.*;
import java.util.*;

public class Main {
    private static int N;
    private static boolean[] visited;
    private static List<Set<Integer>> adjacentList;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        visited = new boolean[N + 1];

        adjacentList = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) {
            adjacentList.add(new HashSet<>());
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int vertexA = Integer.parseInt(tokenizer.nextToken());
            int vertexB = Integer.parseInt(tokenizer.nextToken());
            adjacentList.get(vertexA).add(vertexB);
            adjacentList.get(vertexB).add(vertexA);
        }

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int[] nextVisit = new int[N];
        for (int i = 0; i < N; i++) {
            nextVisit[i] = Integer.parseInt(tokenizer.nextToken());
        }

        Stack<Integer> myPath = new Stack<>();
        myPath.push(1);
        visited[1] = true;
        for (Integer vertex : adjacentList.get(1)) {
            adjacentList.get(vertex).remove(1);
        }

        int order = 1;
        while(!myPath.isEmpty()) {
            if (order == N) {
                System.out.println(1);
                return;
            }
            int currentVertex = myPath.peek();
            int nextVertex = nextVisit[order];

            // 나랑 인접한 지점이라면
            if (adjacentList.get(currentVertex).contains(nextVertex)) {
                // 정상 진행이므로 Stack에 현재 진행경로를 넣고,
                myPath.push(nextVertex);
                // visited[x] = true로 바꾸어준다.
                visited[nextVertex] = true;
                order++;

                // 한번 방문한 정점은 다시 방문하지 못하도록, 인접정점 Set에서 제거한다.
                adjacentList.get(currentVertex).remove(nextVertex);
                for (Integer vertex : adjacentList.get(nextVertex)) {
                    adjacentList.get(vertex).remove(nextVertex);
                }
            }
            // 나랑 인접한 지점이 아니라면
            else {
                // 나랑 인접한 지점에 갈수 있는 곳이 있다면, 잘못된 것이므로 0을 출력하고 종료
                if (!adjacentList.get(currentVertex).isEmpty()) {
                    System.out.println(0);
                    return;
                }
                // 나랑 인접한 지점에 더 이상 갈만한 곳이 없다면, Stack에서 이전 지점을 pop 하고, 다시 이 확인작업을 진행한다.
                else {
                    myPath.pop();
                }
            }
        }
    }
}
