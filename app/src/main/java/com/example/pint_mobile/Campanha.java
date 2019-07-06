package com.example.pint_mobile;

public class Campanha {
    private String idCampanha;
    private String designacao;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String valor;
    private String tipoCampanha;

    public Campanha(String idCampanha, String designacao, String descricao, String dataInicio, String dataFim, String valor, String tipoCampanha) {
        this.idCampanha = idCampanha;
        this.designacao = designacao;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valor = valor;
        this.tipoCampanha = tipoCampanha;
    }

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
}
