package com.example.ProjekatWeb.Entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Kategorija implements Serializable {
    @NotNull
    @NotEmpty
    private String ime; // pk
    @NotNull(message = "ne sme prazan opis")
    @NotEmpty(message = "Ne sme prazan opis")
    private String opis;

    Integer id;

    public Kategorija(){

    }
    public Kategorija(Integer id){
        this.id = id;
    }

    public Kategorija(@NotNull @NotEmpty String ime, @NotNull @NotEmpty String opis) {
        this.ime = ime;
        this.opis = opis;
    }

    public Kategorija(@NotNull @NotEmpty String ime, @NotNull @NotEmpty String opis, Integer id) {
        this.ime = ime;
        this.opis = opis;
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Kategorija{" +
                "ime='" + ime + '\'' +
                ", opis='" + opis + '\'' +
                ", id=" + id +
                '}';
    }
}
