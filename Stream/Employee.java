public class Employee {
    Integer empId;
    String name;
    Integer mId;

    String gender;

    String dept;
    int age;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Employee(Integer empId, String name, Integer mId, String gender, String dept, Integer age) {
        this.empId = empId;
        this.name = name;
        this.mId = mId;
        this.gender = gender;
        this.dept = dept;
        this.age = age;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
