package Reflection.src;

// Reflection can be used to load classes dynamically and create instances.
public class Reflection_6_DynamicClassLoading {
    public static void main(String[] args) throws Exception {
        // Fully qualified class name
        String className = "java.util.ArrayList";

        // Load the class dynamically
        Class<?> clazz = Class.forName(className);

        // Create an instance
        Object instance = clazz.getDeclaredConstructor().newInstance();

        System.out.println("Class: " + clazz.getName());
        System.out.println("Instance: " + instance);
    }
}
