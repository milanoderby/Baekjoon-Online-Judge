import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX_DISTANCE = 60000001;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());

        List<Path> pathList = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(tokenizer.nextToken());
            int B = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());
            pathList.add(new Path(A, B, C));
        }

        int[] distance = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            distance[i] = MAX_DISTANCE;
        }
        distance[1] = 0;

        for (int i = 1; i <= N; i++) {
            for (Path path : pathList) {
                if (distance[path.src] == MAX_DISTANCE) {
                    continue;
                }

                if (distance[path.dest] > distance[path.src] + path.dist) {
                    distance[path.dest] = distance[path.src] + path.dist;

                    // negative cycle이 없다면,
                    // N 번째 작업 시에는, distance 값이 변하지 않아야합니다.
                    // N 번째 작업 시, distance 값이 변한다는 것은 negative cycle이 존재한다는 것이므로
                    // 문제 조건에 맞게 -1을 리턴하고 종료합니다.

                    // 추가적으로, negative cycle이 있다면, 최단거리가 마이너스 오버플로우가 발생할 수도 있습니다.
                    // 나올 수 있는 최단거리보다 작은 최단거리 값이 나온다면, -1을 리턴하고 종료합니다.
                    if (i == N || distance[path.dest] <= -1 * MAX_DISTANCE) {
                        bw.append("-1");
                        bw.flush();
                        return;
                    }
                }
            }
        }

        for (int i = 2; i <= N; i++) {
            if (distance[i] == MAX_DISTANCE) {
                bw.append("-1\n");
            } else {
                bw.append(distance[i] + "\n");
            }
        }
        bw.flush();
    }

    private static class Path {
        private int src;
        private int dest;
        private int dist;

        public Path(int src, int dest, int dist) {
            this.src = src;
            this.dest = dest;
            this.dist = dist;
        }
    }
}