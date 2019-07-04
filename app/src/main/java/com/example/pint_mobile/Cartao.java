package com.example.pint_mobile;

public class Cartao {
    private String id;
    private String nome;
    private String area;
    private String nr_clientes;
    private String nr_descontos;

    public Cartao(String id, String nome, String area, String nr_clientes, String nr_descontos) {
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.nr_clientes = nr_clientes;
        this.nr_descontos = nr_descontos;
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

    public String getNr_clientes() {
        return nr_clientes;
    }

    public void setNr_clientes(String nr_clientes) {
        this.nr_clientes = nr_clientes;
    }

    public String getNr_descontos() {
        return nr_descontos;
    }

    public void setNr_descontos(String nr_descontos) {
        this.nr_descontos = nr_descontos;
    }
}
