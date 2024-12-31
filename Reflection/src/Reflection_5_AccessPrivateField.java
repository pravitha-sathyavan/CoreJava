package Reflection.src;// Reflection allows access to private fields in a class.
import java.lang.reflect.Field;

class Person {
    private String name = "Alice";
}

public class Reflection_5_AccessPrivateField {
    public static void main(String[] args) throws Exception {
        Person person = new Person();

        // Get the class object
        Class<?> clazz = person.getClass();

        // Access the private field
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true); // Make it accessible

        // Get and modify the field value
        String name = (String) field.get(person);
        System.out.println("Original Name: " + name);

        field.set(person, "Bob");
        System.out.println("Modified Name: " + field.get(person));
    }
}
