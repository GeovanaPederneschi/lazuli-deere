package com.example.mechanic.dominio.objects;

import com.example.mechanic.dominio.person.Employee;
import com.example.mechanic.dominio.points.CollectionPoint;
import com.example.mechanic.dominio.points.StockPoint;

import java.time.LocalDateTime;
import java.util.Objects;

public class Receipt {

    private Long idReceipt;
    private Employee employee;
    private Car car;
    private CollectionPoint collectionPoint;
    private StockPoint stockPoint;
    private LocalDateTime registerReceipt;
    private LocalDateTime dataTimeDone;
    private StatusReceipt statusReceipt;
    private StatusDirection statusDirection;

    public Receipt(Long idReceipt, Employee employee, Car car, CollectionPoint collectionPoint, StockPoint stockPoint, LocalDateTime registerReceipt, LocalDateTime dataTimeDone, StatusReceipt statusReceipt, StatusDirection statusDirection) {
        this.idReceipt = idReceipt;
        this.employee = employee;
        this.car = car;
        this.collectionPoint = collectionPoint;
        this.stockPoint = stockPoint;
        this.registerReceipt = registerReceipt;
        this.dataTimeDone = dataTimeDone;
        this.statusReceipt = statusReceipt;
        this.statusDirection = statusDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(idReceipt, receipt.idReceipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReceipt);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "idReceipt=" + idReceipt +
                ", employee=" + employee +
                ", car=" + car +
                ", collectionPoint=" + collectionPoint +
                ", stockPoint=" + stockPoint +
                ", registerReceipt=" + registerReceipt +
                ", dataTimeDone=" + dataTimeDone +
                ", statusReceipt=" + statusReceipt +
                ", statusDirection=" + statusDirection +
                '}';
    }

    public StatusDirection getStatusDirection() {
        return statusDirection;
    }

    public void setStatusDirection(StatusDirection statusDirection) {
        this.statusDirection = statusDirection;
    }

    public Long getIdReceipt() {
        return idReceipt;
    }

    public void setIdReceipt(Long idReceipt) {
        this.idReceipt = idReceipt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public StockPoint getStockPoint() {
        return stockPoint;
    }

    public void setStockPoint(StockPoint stockPoint) {
        this.stockPoint = stockPoint;
    }

    public LocalDateTime getRegisterReceipt() {
        return registerReceipt;
    }

    public void setRegisterReceipt(LocalDateTime registerReceipt) {
        this.registerReceipt = registerReceipt;
    }

    public LocalDateTime getDataTimeDone() {
        return dataTimeDone;
    }

    public void setDataTimeDone(LocalDateTime dataTimeDone) {
        this.dataTimeDone = dataTimeDone;
    }

    public StatusReceipt getStatusReceipt() {
        return statusReceipt;
    }

    public void setStatusReceipt(StatusReceipt statusReceipt) {
        this.statusReceipt = statusReceipt;
    }


    public static final class ReceiptBuilder {
        private Long idReceipt;
        private Employee employee;
        private Car car;
        private CollectionPoint collectionPoint;
        private StockPoint stockPoint;
        private LocalDateTime registerReceipt;
        private LocalDateTime dataTimeDone;
        private StatusReceipt statusReceipt;
        private StatusDirection statusDirection;

        private ReceiptBuilder() {
        }

        public static ReceiptBuilder builder() {
            return new ReceiptBuilder();
        }

        public ReceiptBuilder idReceipt(Long idReceipt) {
            this.idReceipt = idReceipt;
            return this;
        }

        public ReceiptBuilder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public ReceiptBuilder car(Car car) {
            this.car = car;
            return this;
        }

        public ReceiptBuilder collectionPoint(CollectionPoint collectionPoint) {
            this.collectionPoint = collectionPoint;
            return this;
        }

        public ReceiptBuilder stockPoint(StockPoint stockPoint) {
            this.stockPoint = stockPoint;
            return this;
        }

        public ReceiptBuilder registerReceipt(LocalDateTime registerReceipt) {
            this.registerReceipt = registerReceipt;
            return this;
        }

        public ReceiptBuilder dataTimeDone(LocalDateTime dataTimeDone) {
            this.dataTimeDone = dataTimeDone;
            return this;
        }

        public ReceiptBuilder statusReceipt(StatusReceipt statusReceipt) {
            this.statusReceipt = statusReceipt;
            return this;
        }

        public ReceiptBuilder statusDirection(StatusDirection statusDirection) {
            this.statusDirection = statusDirection;
            return this;
        }

        public Receipt build() {
            return new Receipt(idReceipt, employee, car, collectionPoint, stockPoint, registerReceipt, dataTimeDone, statusReceipt, statusDirection);
        }
    }
}
