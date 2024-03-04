import java.util.*;
import java.util.stream.Collectors;

public class StreamOnEmployeeC {

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(new Employee(1, "Pravitha", 5,"Female","CS",27));
        employeeList.add(new Employee(2, "Reshma", 5,"Female","CS",30));
        employeeList.add(new Employee(3, "Jani", 5,"Female","CS",20));
        employeeList.add(new Employee(4, "Aditi", 6,"Female","CS",4));
        employeeList.add(new Employee(5, "Amal", 6,"Male","EC",30));
        employeeList.add(new Employee(6, "Sidhu", 7,"Male","EC",18));
        employeeList.add(new Employee(7, "Kittu", 0,"Male","EC",17));
        // employeeList.add(new Employee(7, "Pravitha7", null));
        // If we are adding managerId as null, we will get error since we are trying to group by managerId

        // Find no of employees of each gender
        Map<String,Long> GenderCount = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()));
        System.out.println(GenderCount);

        // Find no of employees under each manager
        Map<Integer, Long> EmployeeCount =
                employeeList.stream().collect(Collectors.groupingBy(Employee::getmId, Collectors.counting()));
        System.out.println(EmployeeCount);

        // Print manager names with employees reporting to him greater than 1
        for(Map.Entry<Integer, Long> entry :EmployeeCount.entrySet()){
            if(entry.getValue()>1) {
                Optional<Employee> e = employeeList.stream().filter(x -> x.getEmpId() == entry.getKey()).findFirst();
                if(e.isPresent()) {
                    Employee e1 = e.get();
                    System.out.println(e1.getName());
                }
            }
        }

        // Print manager names with employees reporting to him greater than 1
        System.out.println(employeeList.stream().map(Employee::getmId).distinct().count());

        // Print the name of all departments
        employeeList.stream().map(Employee::getDept).distinct().forEach(System.out::println);

        // Print the average age of each gender
        Map<String, Double> EmployeeAgeAverage = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
        System.out.println(EmployeeAgeAverage);

        // Youngest Female employee in CS department
        Optional<Employee> youngestEmployeeWrapper = employeeList.stream().filter(e -> e.getGender()=="Female" && e.getDept() == "CS").min(Comparator.comparing(Employee::getAge));
        if(youngestEmployeeWrapper.isPresent()) {
            Employee yougestEmployee = youngestEmployeeWrapper.get();
            System.out.println(yougestEmployee.getName());
        }

    }
}
