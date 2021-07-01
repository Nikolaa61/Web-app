package com.example.ProjekatWeb.Entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class Komentar implements Serializable {
    @NotNull
    @NotEmpty
    private String autor;
    @NotNull
    @NotEmpty
    private String komentar;
    private GregorianCalendar datumKreiranja;

    Integer id_vesti;

    public Komentar(){

    }

    public Komentar(@NotNull @NotEmpty String autor,@NotEmpty @NotNull String komentar) {
        this.autor = autor;
        this.komentar = komentar;
        this.datumKreiranja = new GregorianCalendar();
    }

    public Komentar(@NotNull @NotEmpty String autor, @NotNull @NotEmpty String komentar, GregorianCalendar datumKreiranja,  Integer id_vesti) {
        this.autor = autor;
        this.komentar = komentar;
        this.datumKreiranja = datumKreiranja;
        this.id_vesti = id_vesti;
    }

    public Komentar(@NotNull@NotEmpty String autor, @NotNull@NotEmpty String komentar, GregorianCalendar datumKreiranja) {
        this.autor = autor;
        this.komentar = komentar;
        this.datumKreiranja = datumKreiranja;
    }

    public Integer getId_vesti() {
        return id_vesti;
    }

    public void setId_vesti(Integer id_vesti) {
        this.id_vesti = id_vesti;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setDatumKreiranja(GregorianCalendar datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public GregorianCalendar getDatumKreiranja() {
        return datumKreiranja;
    }
}
