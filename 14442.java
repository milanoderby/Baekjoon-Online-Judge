import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static char[][] myMap;
    private static boolean[][][] isVisited;
    private static Queue<Position> positionQueue;
    private static int[] dy = {0, 0, 1, -1};
    private static int[] dx = {1, -1, 0, 0};
    private static int N, M;
    private static int MAX_NUM_OF_DESTOYED_WALL;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());

            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());
            MAX_NUM_OF_DESTOYED_WALL = Integer.parseInt(tokenizer.nextToken());

            myMap = new char[N][M];
            isVisited = new boolean[N][M][MAX_NUM_OF_DESTOYED_WALL + 1];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < M; j++) {
                    myMap[i][j] = line.charAt(j);
                }
            }

            positionQueue = new LinkedList<>();
            positionQueue.add(new Position(0, 0, 1, 0));
            isVisited[0][0][0] = true;

            getMinDistance();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Position {
        int y, x;
        int distance;
        int numOfDestroyedWall;

        public Position(int y, int x, int distance, int numOfDestroyedWall){
            this.y = y;
            this.x = x;
            this.distance = distance;
            this.numOfDestroyedWall = numOfDestroyedWall;
        }
    }

    private static void getMinDistance() {
        while(!positionQueue.isEmpty()) {
            Position curPosition = positionQueue.remove();

            if (curPosition.y == N - 1 && curPosition.x == M - 1) {
                System.out.println(curPosition.distance);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nextY = curPosition.y + dy[i];
                int nextX = curPosition.x + dx[i];
                int nextDistance = curPosition.distance + 1;
                int curNumOfDestroyedWall = curPosition.numOfDestroyedWall;

                if(nextY < 0 || nextY >= N || nextX < 0 || nextX >= M){
                    continue;
                }

                if(myMap[nextY][nextX] == '0') {
                    if (!isVisited[nextY][nextX][curNumOfDestroyedWall]) {
                        isVisited[nextY][nextX][curNumOfDestroyedWall] = true;
                        positionQueue.add(new Position(nextY, nextX, nextDistance, curNumOfDestroyedWall));
                    }
                } else {
                    if(curNumOfDestroyedWall + 1 > MAX_NUM_OF_DESTOYED_WALL){
                        continue;
                    }

                    if (!isVisited[nextY][nextX][curNumOfDestroyedWall + 1]) {
                        isVisited[nextY][nextX][curNumOfDestroyedWall + 1] = true;
                        positionQueue.add(new Position(nextY, nextX, nextDistance, curNumOfDestroyedWall + 1));
                    }
                }
            }
        }
        System.out.println(-1);
        return;
    }
}
