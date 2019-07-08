package com.example.pint_mobile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Cartao_empresa_fidelizada implements Parcelable {
    private String id_empresa;
    private String id_cartao;
    private String nome;
    private String email;
    private String area;
    private String nr_descontos;
    private String distrito;
    private String cor;
    private String utilizacoes;
    private String pontos;
    private String rating;
    private ArrayList<Campanha> listaCampanhas;

    public Cartao_empresa_fidelizada(String id_empresa, String id_cartao, String nome, String email, String area, String nr_descontos, String distrito, String cor, String pontos) {
        this.id_empresa = id_empresa;
        this.id_cartao = id_cartao;
        this.nome = nome;
        this.email = email;
        this.area = area;
        this.nr_descontos = nr_descontos;
        this.distrito = distrito;
        this.cor = cor;
        this.pontos = pontos;
    }

    protected Cartao_empresa_fidelizada(Parcel in) {
        id_empresa = in.readString();
        id_cartao = in.readString();
        nome = in.readString();
        email = in.readString();
        area = in.readString();
        nr_descontos = in.readString();
        distrito = in.readString();
        cor = in.readString();
        utilizacoes = in.readString();
        pontos = in.readString();
        rating = in.readString();
    }

    public static final Creator<Cartao_empresa_fidelizada> CREATOR = new Creator<Cartao_empresa_fidelizada>() {
        @Override
        public Cartao_empresa_fidelizada createFromParcel(Parcel in) {
            return new Cartao_empresa_fidelizada(in);
        }

        @Override
        public Cartao_empresa_fidelizada[] newArray(int size) {
            return new Cartao_empresa_fidelizada[size];
        }
    };

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCor() {
        return cor;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getUtilizacoes() {
        return utilizacoes;
    }

    public void setUtilizacoes(String utilizacoes) {
        this.utilizacoes = utilizacoes;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public ArrayList<Campanha> getListaCampanhas() {
        return listaCampanhas;
    }

    public void setListaCampanhas(ArrayList<Campanha> listaCampanhas) {
        this.listaCampanhas = listaCampanhas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_empresa);
        dest.writeString(id_cartao);
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(area);
        dest.writeString(nr_descontos);
        dest.writeString(distrito);
        dest.writeString(cor);
        dest.writeString(utilizacoes);
        dest.writeString(pontos);
        dest.writeString(rating);
    }
}
