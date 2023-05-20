import java.util.HashMap;
import java.util.Map;

public class PascalTriangleUsingMemoization {
    private static Map<String, Integer> map = new HashMap<>();

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
        String key = row + "-" + col;
        if (map.containsKey(key)) {
            return map.get(key);
        }

        int value;
        if (col == 0 || col == row) {
            value = 1; // First and last element in each row is always 1
        } else {
            value = calculatePascalValue(row - 1, col - 1) + calculatePascalValue(row - 1, col);
        }

        map.put(key, value);
        return value;
    }

}