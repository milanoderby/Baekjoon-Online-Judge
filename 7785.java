import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static Set<String> workingPeople;
    private static String ENTER = "enter";
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            workingPeople = new HashSet<>();
            String name, enterYn;
            StringTokenizer tokenizer;
            for (int i = 0; i < N; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                name = tokenizer.nextToken();
                enterYn = tokenizer.nextToken();

                if(enterYn.equals(ENTER)) {
                    workingPeople.add(name);
                } else {
                    workingPeople.remove(name);
                }
            }
            List<String> workingPeopleList = new ArrayList<>(workingPeople);
            Collections.sort(workingPeopleList, Collections.reverseOrder());
            for (String workingPerson : workingPeopleList) {
                System.out.println(workingPerson);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
