package reflectionWork;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Calculator {
    int add(int a, int b);

    int subtract(int a, int b);
}

class CalculatorImpl implements Calculator {
    public int add(int a, int b) {
        for (int i = 0; i < 2_000_000_000; i++) {
            // left blank
        }
        return a + b;
    }

    public int subtract(int a, int b) {
        for (int i = 0; i < 2_000_000_000; i++) {
            // left blank
        }
        return a - b;
    }
}

class Profiling implements InvocationHandler {
    private final Object target;

    public Profiling(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("Method: " + method.getName() + ", Time: " + elapsedTime + " ns");

        return result;
    }
}

class Tracing implements InvocationHandler {
    private final Object target;

    public Tracing(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method: " + method.getName() + ", Parameters: " + argsToString(args));

        Object result = method.invoke(target, args);

        System.out.println("Method: " + method.getName() + ", Result: " + result);

        return result;
    }

    private String argsToString(Object[] args) {
        if (args == null || args.length == 0) {
            return "None";
        }

        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}

public class Task5 {
    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();

        Calculator profilingProxy = (Calculator) Proxy.newProxyInstance(
                Calculator.class.getClassLoader(),
                new Class[]{Calculator.class},
                new Profiling(calculator)
        );

        Calculator tracingProxy = (Calculator) Proxy.newProxyInstance(
                Calculator.class.getClassLoader(),
                new Class[]{Calculator.class},
                new Tracing(calculator)
        );

        System.out.println("Profiling Proxy - Add: " + profilingProxy.add(5, 3));
        System.out.println("Profiling Proxy - Subtract: " + profilingProxy.subtract(10, 4));

        System.out.println("Tracing Proxy - Add: " + tracingProxy.add(5, 3));
        System.out.println("Tracing Proxy - Subtract: " + tracingProxy.subtract(10, 4));
    }
}
