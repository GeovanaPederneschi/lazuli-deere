package com.example.mechanic.dominio.points;

import com.example.mechanic.dominio.objects.Part;

import java.util.List;

public class StockPoint {
    private Long id;
    private int xLocation;
    private int yLocation;
    private List<Part> stockPartList;

    public StockPoint(Long id, int xLocation, int yLocation) {
        this.id = id;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public StockPoint(Long id, int xLocation, int yLocation, List<Part> stockPartList) {
        this.id = id;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.stockPartList = stockPartList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getxLocation() {
        return xLocation;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    public List<Part> getStockPartList() {
        return stockPartList;
    }

    public void setStockPartList(List<Part> stockPartList) {
        this.stockPartList = stockPartList;
    }
}
