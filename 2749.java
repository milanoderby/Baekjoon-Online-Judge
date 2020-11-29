import java.util.Scanner;

public class Main {
    final static int MATRIX_SIZE = 2;
    final static int OPERAND = 1000000;
    public static long[][] mutiplyMatrix(long matrix1[][], long matrix2[][]){
        long[][] resultMatrix = new long[2][2];
        for(int i=0; i<MATRIX_SIZE; i++){
            for(int j=0; j<MATRIX_SIZE; j++){
                for(int k=0; k<MATRIX_SIZE; k++){
                    resultMatrix[i][j] += (matrix1[i][k] * matrix2[k][j]) % OPERAND;
                }
                resultMatrix[i][j] %= OPERAND;
            }
        }
        return resultMatrix;
    }

    public static void main(String[] args) {
	// write your code here
        long[][] matrix = {{1,1},{1,0}};
        long[][] resultMatrix = {{1,0},{0,1}};

        Scanner scan = new Scanner(System.in);
        long input = scan.nextLong();
        input--;

        String binaryString = Long.toBinaryString(input);
        for(int i=binaryString.length()-1; i>=0; i--){
            if(binaryString.charAt(i) == '1'){
                resultMatrix = mutiplyMatrix(resultMatrix, matrix);
            }
            matrix = mutiplyMatrix(matrix, matrix);
        }
        long result = resultMatrix[0][0];
        System.out.println(result);
    }
}