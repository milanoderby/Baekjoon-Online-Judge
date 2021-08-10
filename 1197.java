import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(tokenizer.nextToken());
        int E = Integer.parseInt(tokenizer.nextToken());

        PriorityQueue<Path> pathList = new PriorityQueue<>(new PathComparator());
        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken());
            int dest = Integer.parseInt(tokenizer.nextToken());
            int cost = Integer.parseInt(tokenizer.nextToken());
            pathList.add(new Path(src, dest, cost));
        }

        int costOfMST = 0;
        int[] myGroup = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            myGroup[i] = i;
        }
        
        while (!pathList.isEmpty()) {
            Path path = pathList.poll();

            int groupOfSrc = findGroup(path.src, myGroup);
            int groupOfDest = findGroup(path.dest, myGroup);
            if (groupOfSrc == groupOfDest) {
                continue;
            }
            unionGroup(groupOfSrc, groupOfDest, myGroup);
            costOfMST += path.cost;
        }
        
        bw.append(costOfMST + "");
        bw.flush();
        return;
    }
    
    private static class Path {
        private int src;
        private int dest;
        private int cost;

        public Path(int src, int dest, int cost) {
            this.src = src;
            this.dest = dest;
            this.cost = cost;
        }
    }

    private static class PathComparator implements Comparator<Path> {
        @Override
        public int compare(Path p1, Path p2) {
            return p1.cost - p2.cost;
        }
    }

    private static int findGroup(int index, int[] myGroup) {
        if (index == myGroup[index]) {
            return index;
        }
        return myGroup[index] = findGroup(myGroup[index], myGroup);
    }

    private static void unionGroup(int index1, int index2, int[] myGroup) {
        if (index1 < index2) {
            myGroup[index2] = index1;
            return;
        }
        myGroup[index1] = index2;
        return;
    }
}