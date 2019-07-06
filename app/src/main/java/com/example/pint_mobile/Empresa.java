package com.example.pint_mobile;

public class Empresa {
    private String id;
    private String nome;
    private String area;
    private String distrito;
    private String email;

    public Empresa(String id, String nome, String area, String distrito, String email) {
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.distrito = distrito;
        this.email = email;
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
