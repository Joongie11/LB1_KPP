package reflectionWork;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Task4 {
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Class<?> clazz = selectDataType();
        boolean isMatrix = selectArrayOrMatrix();

        int[] dimensions = enterDimensions(isMatrix);
        int rows = dimensions[0];
        int columns = dimensions[1];

        if (isMatrix) {
            Object[][] matrix = createMatrix(rows, columns, clazz);
            print(matrix);
        } else {
            Object[] array = createArray(rows, clazz);
            print(array);
        }

        resizeMenu(clazz, isMatrix);
    }

    private static Class<?> selectDataType() {
        System.out.print("Enter the class name:");

        String className = scanner.next();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found. Defaulting to java.lang.String.");
            return String.class;
        }
    }

    private static boolean selectArrayOrMatrix() {
        System.out.println("Create:");
        System.out.println("1. Array");
        System.out.println("2. Matrix");

        int choice = scanner.nextInt();
        return choice == 2;
    }

    private static int[] enterDimensions(boolean isMatrix) {
        int[] dimensions = new int[2];
        System.out.print("Enter number of rows: ");
        dimensions[0] = scanner.nextInt();

        if (isMatrix) {
            System.out.print("Enter number of columns: ");
            dimensions[1] = scanner.nextInt();
        } else {
            dimensions[1] = 1;
        }

        return dimensions;
    }

    private static Object[][] createMatrix(int rows, int columns, Class<?> clazz) {
        Object[][] matrix = new Object[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = generateRandomValue(clazz);
            }
        }
        return matrix;
    }

    private static Object[] createArray(int size, Class<?> clazz) {
        Object[] array = new Object[size];

        for (int i = 0; i < size; i++) {
            array[i] = generateRandomValue(clazz);
        }

        return array;
    }

    private static Object generateRandomValue(Class<?> clazz) {
        if (clazz == int.class || clazz == Integer.class) {
            return random.nextInt(10);
        } else if (clazz == double.class || clazz == Double.class) {
            return random.nextDouble();
        } else if (clazz == String.class) {
            StringBuilder sb = new StringBuilder(5);
            for (int i = 0; i < 5; i++) {
                char randomChar = (char) (random.nextInt(26) + 97); // 97 - a (English a);
                sb.append(randomChar);
            }
            return sb.toString();
        } else if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            Object[] array = (Object[]) Array.newInstance(componentType, 3);
            for (int i = 0; i < 3; i++) {
                array[i] = generateRandomValue(componentType);
            }
            return array;
        } else {
            return null;
        }
    }

    private static void print(Object obj) {
        if (obj instanceof Object[]) {
            if (((Object[]) obj).getClass().getComponentType().isArray()) {
                System.out.println("Array:");
                Object[][] array = (Object[][]) obj;
                for (Object[] subArray : array) {
                    System.out.println(Arrays.toString(subArray));
                }
            } else {
                System.out.println("Matrix:");
                System.out.println(Arrays.toString((Object[]) obj));
            }
        }
    }

    private static void resizeMenu(Class<?> clazz, boolean isMatrix) {
        System.out.println("\nResize Menu:");
        System.out.println("1. Resize dimensions");
        System.out.println("0. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> resizeDimensions(clazz, isMatrix);
            case 0 -> System.out.println("Exiting...");
            default -> {
                System.out.println("Invalid choice. Please try again.");
                resizeMenu(clazz, isMatrix);
            }
        }
    }

    private static void resizeDimensions(Class<?> clazz, boolean isMatrix) {
        int[] dimensions = enterDimensions(isMatrix);
        int rows = dimensions[0];
        int columns = dimensions[1];

        if (isMatrix) {
            Object[][] matrix = createMatrix(rows, columns, clazz);
            print(matrix);
        } else {
            Object[] array = createArray(rows, clazz);
            print(array);
        }

        resizeMenu(clazz, isMatrix);
    }
}
