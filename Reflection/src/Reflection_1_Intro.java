package Reflection.src;
/*
Reflection:
JVM feature that gives us runtime access to information about our application's classes and objects.
Using Reflection APIs we can write code that links different software components at run time and creates new program flow without any source code modifications.
A java program without reflection analyzes the input, performs some operations and produces some output.
A java program with reflection analyzes the input and source code, performs some operations and produces some output.

eg: In JUNIT test cases, we have annotations like @Before which when added before a method, changes the flow of program since that method gets executed first.

Java Reflection allows a program to inspect and manipulate the runtime behavior of classes, methods, fields, and constructors.
It provides the ability to analyze or modify the runtime properties of an object or class.
Reflection is part of the java.lang.reflect package and is commonly used for tasks such as:

Inspecting Classes:
Access information about a class at runtime, such as its name, package, superclass, and implemented interfaces.
Inspecting Methods:
Retrieve and invoke methods of a class, even private or protected ones, bypassing access control checks.
Inspecting Fields:
Access and modify fields of a class, including private or protected ones.
Inspecting Constructors:
Instantiate objects dynamically by invoking constructors.
Dynamic Method Invocation:
Call methods on objects where the method name is determined at runtime.
Analyzing Annotations:
Access runtime annotations on classes, methods, and fields.
*/


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection_1_Intro {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Getting the Class object
        Class<?> clazz = Class.forName("java.util.ArrayList");

        // Inspecting constructors
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Constructor: " + constructor);
        }

        // Inspecting methods
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
        }

        // Instantiating an object dynamically
        Constructor<?> constructor = clazz.getConstructor();
        Object instance = constructor.newInstance();

        // Accessing a method
        Method addMethod = clazz.getMethod("add", Object.class);
        addMethod.invoke(instance, "Reflection Example");

        System.out.println(instance);
    }
}