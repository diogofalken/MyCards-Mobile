package com.example.pint_mobile;

public class Empresa {
    private String id;
    private String nome;
    private String area;
    private String distrito;
    private String email;
    private String facebook;
    private String linkedIn;
    private String twitter;

    public Empresa(String id, String nome, String area, String distrito, String email, String facebook, String linkedIn, String twitter) {
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.distrito = distrito;
        this.email = email;
        this.facebook = facebook;
        this.twitter = twitter;
        this.linkedIn = linkedIn;
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
