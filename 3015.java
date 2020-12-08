import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    private static int N;
    private static Stack<Person> heightListOfA;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());

            heightListOfA = new Stack<>();
            int heightOfB;
            long answer = 0;
            for (int i = 0; i < N; i++) {
                heightOfB = Integer.parseInt(br.readLine());
                while (!heightListOfA.isEmpty()) {
                    if (heightListOfA.peek().height >= heightOfB) {
                        break;
                    }
                    Person person = heightListOfA.pop();
                    answer += person.number;
                }

                if (!heightListOfA.isEmpty()) {
                    if (heightListOfA.peek().height > heightOfB) {
                        answer += 1;
                        heightListOfA.push(new Person(heightOfB, 1));
                    } else if (heightListOfA.peek().height == heightOfB) {
                        Person person = heightListOfA.pop();
                        answer += person.number;
                        if(!heightListOfA.empty()){
                            answer += 1;
                        }
                        person.number += 1;
                        heightListOfA.push(person);
                    }
                } else {
                    heightListOfA.push(new Person(heightOfB, 1));
                }
            }
            System.out.println(answer);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Person {
        int height;
        int number;
        public Person (int height, int number) {
            this.height = height;
            this.number = number;
        }
    }
}
