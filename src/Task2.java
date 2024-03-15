package reflectionWork;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task2 {
    private final Object object;

    public Task2(Object object) {
        this.object = object;
    }

    public static void main(String[] args) {
        String myObject = "hello world!";

        Task2 inspector = new Task2(myObject);

        inspector.inspect();
    }

    public void inspect() {
        Class<?> clazz = object.getClass();
        System.out.println("Type: " + clazz.getName());

        System.out.println("State:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                System.out.println(field.getName() + " = " + field.get(object));
            } catch (IllegalAccessException e) {
                // ignore
            }
        }

        System.out.println("Methods:");
        Method[] methods = clazz.getMethods();
        List<Method> methodsWithoutParams = new ArrayList<>();
        int k = 1;
        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                System.out.printf("\u001B[32m %-3d %s\n", k++, method.getName());
                methodsWithoutParams.add(method);
                continue;
            }

            System.out.printf("\u001B[39m %s\n", method.getName());
        }
        inspectInteractive(methodsWithoutParams);
    }

    private void inspectInteractive(List<Method> methods) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\u001B[39mEnter the index of the method to invoke (or -1 to exit):");
        int index = scanner.nextInt();

        if (index > 0 && index < methods.size()) {
            Method method = methods.get(index - 1);

            try {
                Object result = method.invoke(object);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            inspectInteractive(methods);
        } else if (index != -1) {
            System.out.println("Invalid index");
            inspectInteractive(methods);
        }
    }
}
