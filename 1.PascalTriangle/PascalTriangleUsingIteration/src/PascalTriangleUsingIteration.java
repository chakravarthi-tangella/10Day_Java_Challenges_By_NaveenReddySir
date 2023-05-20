public class PascalTriangleUsingIteration {
    public static void main(String[] args) {
        pascalTriangle(5);
    }

    public static void pascalTriangle(int rows) {
        int[][] pascal = new int[rows][];

        for (int i = 0; i < rows; i++) {

            pascal[i] = new int[i + 1];
            pascal[i][0] = 1; // First element in each row is always 1

            for (int j = 1; j < i; j++) {
                pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j];
            }

            pascal[i][i] = 1; // Last element in each row is always 1
        }

        // Printing the Pascal's Triangle
        for (int i = 0; i < rows; i++) {
            for(int j=0;j<rows-i; j++)
            {
                System.out.print(" ");
            }
            for (int j = 0; j <= i; j++) {
                System.out.print(pascal[i][j] + " ");
            }
            System.out.println();
        }
    }

}