package com.example.pint_mobile;

public class Cartao {
    private String id_empresa;
    private String id_cartao;
    private String id_cliente;

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_cartao() {
        return id_cartao;
    }

    public void setId_cartao(String id_cartao) {
        this.id_cartao = id_cartao;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getNr_descontos() {
        return nr_descontos;
    }

    public void setNr_descontos(String nr_descontos) {
        this.nr_descontos = nr_descontos;
    }

    public Cartao(String id_empresa, String id_cartao, String id_cliente, String nome, String area, String nr_descontos) {
        this.id_empresa = id_empresa;
        this.id_cartao = id_cartao;
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.area = area;
        this.nr_descontos = nr_descontos;
    }

    private String nome;
    private String area;
    private String nr_descontos;

}
