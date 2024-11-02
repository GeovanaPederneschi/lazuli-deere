package com.example.mechanic.dominio.objects;

import java.util.Map;

public class ReceiptPart {
    private Receipt receipt;
    private Part part;
    private int countPart;

    public ReceiptPart(Receipt receipt, Part part, int countPart) {
        this.receipt = receipt;
        this.part = part;
        this.countPart = countPart;
    }

    @Override
    public String toString() {
        return "ReceiptPart{" +
                "receipt=" + receipt +
                ", part=" + part +
                ", countPart=" + countPart +
                '}';
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getCountPart() {
        return countPart;
    }

    public void setCountPart(int countPart) {
        this.countPart = countPart;
    }
}
