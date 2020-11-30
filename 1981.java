import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static int[][] arr;
    private static List<Integer> integerList;
    private static int DIR_MAX = 4;
    private static int[] dy = {0, 1, 0, -1};
    private static int[] dx = {1, 0, -1, 0};
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            arr = new int[N][N];
            Set<Integer> integerSet = new HashSet<>();

            for (int i=0; i<N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                for (int j=0; j<N; j++) {
                    arr[i][j] = Integer.parseInt(tokenizer.nextToken());
                    integerSet.add(arr[i][j]);
                }
            }

            integerList = new ArrayList<>(integerSet);
            Collections.sort(integerList);

            int low = 0;
            int high = 201;

            if(canReachDestination(low)){
                System.out.println(low);
                return;
            }

            int mid = (low + high) / 2;
            while(low + 1 <high) {
                boolean resultKMinusOne = canReachDestination(mid - 1);
                boolean resultK = canReachDestination(mid);

                if(resultKMinusOne) {
                    high = mid;
                } else if (!resultK) {
                    low = mid;
                } else {
                    System.out.println(mid);
                    return;
                }

                mid = (low + high) / 2;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static boolean canReachDestination(int minMaxDiff){
        for (Integer integer : integerList) {
            int min = integer;
            int max = integer + minMaxDiff;

            if(bfs(min, max)){
                return true;
            }
        }
        return false;
    }

    private static boolean bfs(int min, int max) {
        boolean[][] isVisited = new boolean[N][N];
        if(min <= arr[0][0] && arr[0][0] <= max){
            isVisited[0][0] = true;
        } else {
            return false;
        }

        Queue<Point> myQ = new LinkedList<>();
        myQ.add(new Point(0, 0));

        while(!myQ.isEmpty()) {
            Point cur = myQ.poll();
            for (int i = 0; i < DIR_MAX; i++) {
                int ny = cur.y + dy[i];
                int nx = cur.x + dx[i];

                if(0<=ny && ny<N && 0<=nx && nx<N){
                    if(!isVisited[ny][nx] && min<=arr[ny][nx] && arr[ny][nx]<=max){
                        if(ny == N-1 && nx == N-1) {
                            return true;
                        }
                        isVisited[ny][nx] = true;
                        myQ.add(new Point(ny, nx));
                    }
                }
            }
        }
        return false;
    }

    private static class Point {
        int x, y;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
