package Reflection.src;/*
You can invoke a method on an object without knowing the method name at compile time.
*/
import java.lang.reflect.Method;

class ReflectionExample {
    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
}

public class Reflection_4_MethodInvocation {
    public static void main(String[] args) throws Exception {
        ReflectionExample obj = new ReflectionExample();

        // Get the class object
        Class<?> clazz = obj.getClass();

        // Get the method by name and parameter types
        Method method = clazz.getMethod("greet", String.class);

        // Invoke the method
        method.invoke(obj, "John");
    }
}

