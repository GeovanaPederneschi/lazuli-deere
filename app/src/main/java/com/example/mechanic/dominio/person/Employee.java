package com.example.mechanic.dominio.person;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Employee implements Serializable {
    private LocalDate dataBirthday;
    private LocalDateTime registerDateTime;
    private String cpf;
    private String fullName;
    private Long phoneNumber;
    private String email;
    private String password;
    private TypeEmployee typeEmployee;

    public Employee(LocalDate dataBirthday, LocalDateTime registerDateTime, String cpf, String fullName, Long phoneNumber, String email, String password, TypeEmployee typeEmployee) {
        this.dataBirthday = dataBirthday;
        this.registerDateTime = registerDateTime;
        this.cpf = cpf;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.typeEmployee = typeEmployee;
    }

    public Employee(String cpf) {
        this.cpf = cpf;
    }

    public TypeEmployee getTypeEmployee() {
        return typeEmployee;
    }

    public LocalDate getDataBirthday() {
        return dataBirthday;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(cpf, employee.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "dataBirthday=" + dataBirthday +
                ", registerDateTime=" + registerDateTime +
                ", cpf='" + cpf + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", typeEmployee=" + typeEmployee +
                '}';
    }

    public static final class EmployeeBuilder {
        private LocalDate dataBirthday;
        private LocalDateTime registerDateTime;
        private String cpf;
        private String fullName;
        private Long phoneNumber1;
        private String email;
        private String password;
        private TypeEmployee typeEmployee;

        private EmployeeBuilder() {
        }

        public static EmployeeBuilder builder() {
            return new EmployeeBuilder();
        }

        public EmployeeBuilder dataBirthday(LocalDate dataBirthday) {
            this.dataBirthday = dataBirthday;
            return this;
        }

        public EmployeeBuilder registerDateTime(LocalDateTime registerDateTime) {
            this.registerDateTime = registerDateTime;
            return this;
        }

        public EmployeeBuilder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public EmployeeBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public EmployeeBuilder phoneNumber1(Long phoneNumber1) {
            this.phoneNumber1 = phoneNumber1;
            return this;
        }
        

        public EmployeeBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeBuilder password(String password) {
            this.password = password;
            return this;
        }


        public EmployeeBuilder typeEmployee(TypeEmployee typeEmployee) {
            this.typeEmployee = typeEmployee;
            return this;
        }

        public Employee build() {
            return new Employee(dataBirthday, registerDateTime, cpf, fullName, phoneNumber1, email, password, typeEmployee);
        }
    }
}
