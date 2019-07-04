package com.example.pint_mobile;

public class Empresa {
    private String id;
    private String nome;
    private String area;

    public Empresa(String id, String nome, String area) {
        this.id = id;
        this.nome = nome;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
