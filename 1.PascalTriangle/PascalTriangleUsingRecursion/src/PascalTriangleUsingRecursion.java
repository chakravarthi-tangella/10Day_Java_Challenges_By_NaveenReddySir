public class PascalTriangleUsingRecursion {
    public static void main(String[] args) {
        pascalTraingle(5);
    }

    public static void pascalTraingle(int rows)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows-i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j <= i; j++) {
                System.out.print(calculatePascalValue(i, j) + " ");
            }
            System.out.println();
        }
    }

    public static int calculatePascalValue(int row, int col) {
        if (col == 0 || col == row) {
            return 1; // First and last element in each row is always 1
        } else {
            return calculatePascalValue(row - 1, col - 1) + calculatePascalValue(row - 1, col);
        }
    }
}
