package com.demo;

public class Employee {
    Integer empId;
    String name;
    String city;
    int age;

    public Employee(Integer empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        return empId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId.equals(employee.empId);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                '}';
    }
}
