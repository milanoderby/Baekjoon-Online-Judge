import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
	// write your code here
        Scanner scan = new Scanner(System.in);
        Set<Integer> integerSet = new HashSet<>();
        int N = scan.nextInt();
        for(int i=0; i<N; i++){
            int num = scan.nextInt();
            integerSet.add(num);
        }

        int M = scan.nextInt();
        for(int i=0; i<M; i++){
            int num = scan.nextInt();
            if(integerSet.contains(num)){
                System.out.println(1);
            }
            else{
                System.out.println(0);
            }
        }
    }
}
