package Reflection.src;// Reflection is often used in frameworks for injecting dependencies at runtime.
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

class Service {
    public void serve() {
        System.out.println("Service is serving.");
    }
}

class Client {
    @Inject1
    private Service service;

    public void doWork() {
        if (service == null) {
            throw new IllegalStateException("Dependency injection failed. Service is null.");
        }
        service.serve();
    }
}

// The @Inject1 annotation is used to mark fields that need dependency injection.
// Its RUNTIME retention policy ensures that the annotation is visible during runtime for reflection.
@Retention(RetentionPolicy.RUNTIME) // Ensure the annotation is available at runtime
@interface Inject1 {}

public class Reflection_7_RunTimeDependencyInjection {
    public static void main(String[] args) throws Exception {
        Client client = new Client();

        // Perform dependency injection
        Class<?> clazz = client.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field);
            if (field.isAnnotationPresent(Inject1.class)) {
                System.out.println("Injecting into field: " + field.getName());
                field.setAccessible(true); // Ensure the private field can be accessed
                field.set(client, new Service()); // Inject the Service instance
                System.out.println("Injected Service instance into " + field.getName());
            }
        }

        System.out.println("Calling doWork()...");
        client.doWork(); // Check for NullPointerException
    }
}


