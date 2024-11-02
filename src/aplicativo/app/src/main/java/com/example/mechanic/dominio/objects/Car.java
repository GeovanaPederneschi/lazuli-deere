package com.example.mechanic.dominio.objects;

import com.example.mechanic.dominio.person.Employee;

import java.util.List;

public class Car {
    private Long id;
    private Long numberIdentification;
    Long xLocation;
    Long yLocation;
    private Employee employee;
    List<Part> partList;

    public Car(Long id) {
        this.id = id;
    }

    public Car(Long id, Long numberIdentification) {
        this.id = id;
        this.numberIdentification = numberIdentification;
    }

    public Car(Long id, Long numberIdentification, Long xLocation, Long yLocation, Employee employee, List<Part> partList) {
        this.id = id;
        this.numberIdentification = numberIdentification;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.employee = employee;
        this.partList = partList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", numberIdentification=" + numberIdentification +
                ", xLocation=" + xLocation +
                ", yLocation=" + yLocation +
                ", employee=" + employee +
                ", partList=" + partList +
                '}';
    }

    public Long getNumberIdentification() {
        return numberIdentification;
    }

    public void setNumberIdentification(Long numberIdentification) {
        this.numberIdentification = numberIdentification;
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

    public Long getxLocation() {
        return xLocation;
    }

    public void setxLocation(Long xLocation) {
        this.xLocation = xLocation;
    }

    public Long getyLocation() {
        return yLocation;
    }

    public void setyLocation(Long yLocation) {
        this.yLocation = yLocation;
    }


    public static final class CarBuilder {
        private Long id;
        private Long numberIdentification;
        private Long xLocation;
        private Long yLocation;
        private Employee employee;
        private List<Part> partList;

        private CarBuilder() {
        }

        public static CarBuilder builder() {
            return new CarBuilder();
        }

        public CarBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CarBuilder numberIdentification(Long numberIdentification) {
            this.numberIdentification = numberIdentification;
            return this;
        }

        public CarBuilder xLocation(Long xLocation) {
            this.xLocation = xLocation;
            return this;
        }

        public CarBuilder yLocation(Long yLocation) {
            this.yLocation = yLocation;
            return this;
        }

        public CarBuilder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public CarBuilder partList(List<Part> partList) {
            this.partList = partList;
            return this;
        }

        public Car build() {
            return new Car(id, numberIdentification, xLocation, yLocation, employee, partList);
        }
    }
}
