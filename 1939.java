import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static class AdjacentIsland {
        int island;
        int weightLimit;

        public int getIsland() {
            return island;
        }

        public int getWeightLimit() {
            return weightLimit;
        }

        public void setIsland(int island) {
            this.island = island;
        }

        public void setWeightLimit(int weightLimit) {
            this.weightLimit = weightLimit;
        }
    }

    private static List<List<AdjacentIsland>> adjacentGraph;
    private static int N;
    private static int M;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            adjacentGraph = new ArrayList<>();
            for(int i=0; i<N+1; i++) {
                adjacentGraph.add(new ArrayList<>());
            }

            for(int i=0; i<M; i++){
                tokenizer = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                int c = Integer.parseInt(tokenizer.nextToken());

                AdjacentIsland islandB = new AdjacentIsland();
                islandB.setIsland(b);
                islandB.setWeightLimit(c);
                adjacentGraph.get(a).add(islandB);

                AdjacentIsland islandA = new AdjacentIsland();
                islandA.setIsland(a);
                islandA.setWeightLimit(c);
                adjacentGraph.get(b).add(islandA);
            }
            tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken());
            int dest = Integer.parseInt(tokenizer.nextToken());

            int left = 0;
            int right = 1000000000;
            if(canTransport(right, src, dest)){
                System.out.println(right);
                return;
            }

            while(left + 1 < right){
                int mid = (left + right)/2;
                if(!canTransport(mid, src, dest)){
                    right = mid;
                } else if(canTransport(mid, src, dest) && !canTransport(mid + 1, src, dest)){
                    System.out.println(mid);
                    return;
                } else {
                    left = mid;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static boolean canTransport(int weight, int src, int dest){
        boolean[] isVisited = new boolean[N+1];
        Queue<Integer> myQ = new LinkedList<>();
        myQ.add(src);
        isVisited[src] = true;

        while (!myQ.isEmpty()){
            int island = myQ.poll();
            for (AdjacentIsland adjacentIsland : adjacentGraph.get(island)) {
                if(!isVisited[adjacentIsland.getIsland()] && adjacentIsland.getWeightLimit() >= weight){
                    isVisited[adjacentIsland.getIsland()] = true;
                    myQ.add(adjacentIsland.getIsland());
                    if(dest == adjacentIsland.getIsland()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
