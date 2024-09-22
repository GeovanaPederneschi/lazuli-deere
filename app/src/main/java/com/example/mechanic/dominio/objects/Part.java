package com.example.mechanic.dominio.objects;

public class Part {
    private Long id;

    public Part(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
