package Reflection.src;/*
In Java, the Class object represents the metadata of a class or interface, and it is used to obtain reflective information about the class (like its fields, methods, constructors, etc.).
*/

import java.util.HashMap;
import java.util.Map;

public class Reflection_2_Class {

   public static void main(String[] args) throws ClassNotFoundException {

       /*
       Using Object.getClass()
       If you have an instance of an object, you can use the getClass() method to get the Class object for that instance.
       */
       String str = "Hello World";
       Map map = new HashMap();

       Class c1 = str.getClass();
       Class c2 = map.getClass();
       //This cannot be applied to primitive types

       System.out.println(c1.getName());
       System.out.println(c2.getName());

       /*
       Using .class Syntax
       You can obtain the Class object of a class using the .class syntax if you know the class at compile time.
       This is applicable to primitive types as well
       The Class object for arrays can also be obtained using .class.
       */
       Class<?> c3 = String.class;
       System.out.println(c3.getName());

       Class<?> c4 = int.class;
       System.out.println(c4.getName());

       /*
       Using Class.forName(String className)
       You can use the fully qualified class name as a string to load the Class object dynamically.
       This is commonly used when the class name is not known at compile time.
       This is not applicable for primitive data types
       */

       Class<?> c5 = Class.forName("java.util.ArrayList");
       System.out.println(c5.getName()); // Output: java.util.ArrayList

       /*
       Using ClassLoader
       A ClassLoader is used to load classes dynamically. You can obtain the ClassLoader of the current class and use it to load another class.
       */

       try {
           ClassLoader classLoader = Reflection_2_Class.class.getClassLoader();
           Class<?> c6= classLoader.loadClass("java.util.HashMap");
           System.out.println(c6.getName()); // Output: java.util.HashMap
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }


   }
}
