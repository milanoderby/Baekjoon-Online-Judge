import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<Entry> entryList = new ArrayList<>();
        for (int index = 0; index < N; index++) {
            entryList.add(new Entry(index, Integer.parseInt(br.readLine())));
        }

        int maxDiff = 0;

        // 이 때, 사용하는 정렬은 정렬 전/후 같은 값들끼리 순서가 보장되는 stable sort 입니다.
        // Collections.sort 는 merge sort를 사용하며, 이는 stable sort 입니다.
        Collections.sort(entryList, new EntryComparator());

        for (int index = 0; index < entryList.size(); index++) {
            // 정렬 전과 정렬 후의 index 값이 가장 큰 차이가 나는 숫자를 찾는다.
            int curDiff = entryList.get(index).originIndex - index;
            if (maxDiff < curDiff) {
                maxDiff = curDiff;
            }
        }
        System.out.println(maxDiff + 1);
    }

    private static class Entry {
        int originIndex;
        int value;

        public Entry(int originIndex, int value) {
            this.originIndex = originIndex;
            this.value = value;
        }
    }

    private static class EntryComparator implements Comparator<Entry> {

        @Override
        public int compare(Entry o1, Entry o2) {
            return o1.value - o2.value;
        }
    }
}

