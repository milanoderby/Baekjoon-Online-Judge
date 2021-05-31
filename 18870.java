import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        // 숫자로 정렬하고, 인덱스를 저장한다.
        Map<Integer, List<Integer>> integerListTreeMap= new TreeMap<>();

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int index = 0; index < N; index++) {
            int inputNum = Integer.parseInt(tokenizer.nextToken());
            if (integerListTreeMap.containsKey(inputNum)) {
                integerListTreeMap.get(inputNum).add(index);
            } else {
                List<Integer> integerList = new LinkedList<>();
                integerList.add(index);
                integerListTreeMap.put(inputNum, integerList);
            }
        }

        Set<Integer> indexSet = integerListTreeMap.keySet();
        List<Integer> keyList = indexSet.stream()
                                          .sorted()
                                          .collect(Collectors.toList());

        int orderNum = 0;
        int[] answerArr = new int[N];
        for (Integer key : keyList) {
            for (Integer index : integerListTreeMap.get(key)) {
                answerArr[index] = orderNum;
            }
            orderNum++;
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < answerArr.length; i++) {
            bw.append(answerArr[i] + " ");
        }
        bw.flush();
    }
}
