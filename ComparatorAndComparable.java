// Sort a list of employees based on its name 

// Using Comparator and Lambda
import java.util.*;

class Employee {
    private String name;
    private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "'}";
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("John", 101),
            new Employee("Alice", 102),
            new Employee("Bob", 103)
        );

        // Sort by name using lambda expression
        employees.sort(Comparator.comparing(Employee::getName));

        // Print sorted list
        employees.forEach(System.out::println);
    }
}

// Using Comparable Interface

import java.util.*;

class Employee implements Comparable<Employee> {
    private String name;
    private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Employee other) {
        // Compare employees by name
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "'}";
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("John", 101),
            new Employee("Alice", 102),
            new Employee("Bob", 103)
        );

        // Sort using Comparable interface (natural order)
        Collections.sort(employees);

        // Print sorted list
        employees.forEach(System.out::println);
    }
}

