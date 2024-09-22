package com.example.mechanic.dominio.objects;

import com.example.mechanic.dominio.person.Employee;

import java.util.List;

public class Car {
    private Long id;
    private Employee employee;
    List<Part> partList;

    public Car(Long id) {
        this.id = id;
    }

    public Car(Long id, Employee employee, List<Part> partList) {
        this.id = id;
        this.employee = employee;
        this.partList = partList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Part> getPartList() {
        return partList;
    }

    public void setPartList(List<Part> partList) {
        this.partList = partList;
    }
}
