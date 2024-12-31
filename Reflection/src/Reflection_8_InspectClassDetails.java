package Reflection.src;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class SampleClass {
    private String field1;
    public int field2;

    public void method1() {}
    private void method2() {}
}

public class Reflection_8_InspectClassDetails {
    public static void main(String[] args) {
        Class<?> clazz = SampleClass.class;

        System.out.println("Fields:");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("  " + field.getName() + " - " + field.getType());
        }

        System.out.println("Methods:");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("  " + method.getName());
        }
    }
}
