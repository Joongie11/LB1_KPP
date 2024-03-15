package reflectionWork;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task3 {

    public static void callMethod(Object object, String methodName, List<Object> parameters)
            throws FunctionNotFoundException {
        Class<?>[] parameterTypes = new Class<?>[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            parameterTypes[i] = parameters.get(i).getClass();
        }

        try {
            Method method = object.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(object, parameters.toArray());
            System.out.println("Result: " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException(methodName + " not found or cannot be invoked.");
        }
    }

    public static void main(String[] args) throws Exception {

        while (true) {
            Object obj = "hello!";

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the method name: ");
            String methodName = scanner.nextLine();
            if (methodName.trim().equals("exit")) break;

            System.out.print("Enter the number of parameters: ");
            int paramCount = scanner.nextInt();
            scanner.nextLine();

            List<Object> params = new ArrayList<>();
            for (int i = 0; i < paramCount; i++) {
                System.out.print("Enter parameter #" + (i + 1) + ": ");
                String param = scanner.nextLine();
                Class<?> parameterType = getParameterType(obj, methodName, i);
                Object convertedParam = convertStringToType(param, parameterType);
                params.add(convertedParam);
            }

            try {
                callMethod(obj, methodName, params);
            } catch (FunctionNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static Class<?> getParameterType(Object object, String methodName, int parameterIndex) throws Exception {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterIndex < parameterTypes.length) {
                    return parameterTypes[parameterIndex];
                }
            }
        }
        throw new Exception("Method or parameter not found.");
    }

    public static Object convertStringToType(String param, Class<?> targetType) {
        if (targetType == String.class) {
            return param;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(param);
        }

        return null;
    }
}