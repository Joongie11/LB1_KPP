package reflectionWork;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class Task1 {
    public static String describeClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return describeClass(clazz);
    }

    public static String describeClass(Class<?> clazz) {
        StringBuilder description = new StringBuilder();

        // Package
        Package pkg = clazz.getPackage();
        if (pkg != null) {
            description.append("package ").append(pkg.getName()).append(";\n");
        }

        // Class declaration
        int modifiers = clazz.getModifiers();
        description.append(Modifier.toString(modifiers)).append(" ");

        if (Modifier.isInterface(modifiers)) {
            description.append("interface ");
        } else if (Modifier.isAbstract(modifiers)) {
            description.append("abstract ");
        } else {
            description.append("class ");
        }

        description.append(clazz.getSimpleName());

        // Superclass
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            description.append(" extends ").append(superclass.getSimpleName());
        }

        // Implemented interfaces
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            description.append(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                if (i > 0) {
                    description.append(", ");
                }
                description.append(interfaces[i].getSimpleName());
            }
        }

        description.append(" {\n\n");

        // Fields
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            description.append("\t// Fields\n");
            for (Field field : fields) {
                description.append("\t")
                        .append(Modifier.toString(field.getModifiers()))
                        .append(" ")
                        .append(getSimpleTypeName(field.getType()))
                        .append(" ")
                        .append(field.getName())
                        .append(";\n");
            }
            description.append("\n");
        }

        // Constructors
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length > 0) {
            description.append("\t// Constructors\n");
            for (Constructor<?> constructor : constructors) {
                description.append("\t")
                        .append(Modifier.toString(constructor.getModifiers()))
                        .append(" ")
                        .append(clazz.getSimpleName())
                        .append("(");

                Class<?>[] params = constructor.getParameterTypes();
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) {
                        description.append(", ");
                    }
                    description.append(getSimpleTypeName(params[i]));
                }

                description.append(");\n");
            }
            description.append("\n");
        }

        // Methods
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length > 0) {
            description.append("\t// Methods\n");
            for (Method method : methods) {
                description.append("\t")
                        .append(Modifier.toString(method.getModifiers()))
                        .append(" ")
                        .append(getSimpleTypeName(method.getReturnType()))
                        .append(" ")
                        .append(method.getName())
                        .append("(");

                Class<?>[] params = method.getParameterTypes();
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) {
                        description.append(", ");
                    }
                    description.append(getSimpleTypeName(params[i]));
                }

                description.append(");\n");
            }
        }

        description.append("}");

        return description.toString();
    }

    private static String getSimpleTypeName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            return getSimpleTypeName(componentType) + "[]";
        } else {
            return simpleName;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Class name: ");
        String className = scanner.nextLine();

        try {
            String classDescription = Task1.describeClass(className);
            System.out.println(classDescription);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + className);
        }
    }
}