package com.example.pint_mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class Campanha implements Parcelable {
    private String idCampanha;
    private String designacao;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String valor;
    private String tipoCampanha;
    private String nomeEmpresa;
    private String areaInteresse;
    private String utilizacoes;
    private String cor;
    private String id_cartao;
    private String id_empresa;

    public Campanha(String idCampanha, String designacao, String descricao, String dataInicio, String dataFim, String valor, String tipoCampanha, String id_cartao, String id_empresa, String utilizacoes) {
        this.idCampanha = idCampanha;
        this.designacao = designacao;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valor = valor;
        this.tipoCampanha = tipoCampanha;
        this.id_cartao = id_cartao;
        this.id_empresa = id_empresa;
        this.utilizacoes = utilizacoes;
    }

    public Campanha(String utilizacoes) {
        this.utilizacoes = utilizacoes;
    }

    public String getId_cartao() {
        return id_cartao;
    }

    public void setId_cartao(String id_cartao) {
        this.id_cartao = id_cartao;
    }

    protected Campanha(Parcel in) {
        idCampanha = in.readString();
        designacao = in.readString();
        descricao = in.readString();
        dataInicio = in.readString();
        dataFim = in.readString();
        valor = in.readString();
        tipoCampanha = in.readString();
        id_cartao = in.readString();
        id_empresa = in.readString();
    }

    public static final Creator<Campanha> CREATOR = new Creator<Campanha>() {
        @Override
        public Campanha createFromParcel(Parcel in) {
            return new Campanha(in);
        }

        @Override
        public Campanha[] newArray(int size) {
            return new Campanha[size];
        }
    };

    public String getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(String idCampanha) {
        this.idCampanha = idCampanha;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoCampanha() {
        return tipoCampanha;
    }

    public void setTipoCampanha(String tipoCampanha) {
        this.tipoCampanha = tipoCampanha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCampanha);
        dest.writeString(designacao);
        dest.writeString(descricao);
        dest.writeString(dataInicio);
        dest.writeString(dataFim);
        dest.writeString(valor);
        dest.writeString(tipoCampanha);
        dest.writeString(id_cartao);
        dest.writeString(id_empresa);
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getAreaInteresse() {
        return areaInteresse;
    }

    public void setAreaInteresse(String areaInteresse) {
        this.areaInteresse = areaInteresse;
    }

    public String getUtilizacoes() {
        return utilizacoes;
    }

    public void setUtilizacoes(String utilizacoes) {
        this.utilizacoes = utilizacoes;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }
}
