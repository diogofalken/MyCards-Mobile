package com.example.pint_mobile;

public class Cartao {
    private String id_empresa;
    private String id_cartao;
    private String nome;
    private String area;
    private String nr_descontos;
    private String distrito;
    private String cor;
    private String email;

    public Cartao(String id_empresa, String id_cartao, String distrito, String nome, String area, String nr_descontos, String cor, String email) {
        this.id_empresa = id_empresa;
        this.id_cartao = id_cartao;
        this.distrito = distrito;
        this.nome = nome;
        this.area = area;
        this.nr_descontos = nr_descontos;
        this.cor = cor;
        this.email = email;
    }

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

    public String getDistrito() {
        return distrito;
    }

    public void setId_cliente(String distrito) {
        this.distrito = distrito;
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

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
