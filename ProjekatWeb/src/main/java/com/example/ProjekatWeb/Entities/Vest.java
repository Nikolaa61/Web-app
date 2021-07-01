package com.example.ProjekatWeb.Entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

public class Vest implements Serializable {
    @NotEmpty
    @NotNull
    private String naslov;
    @NotEmpty
    @NotNull
    private String tekst;
    private GregorianCalendar vremeKreiranja;
    private int brojPoseta;
    private Korisnik autor;

    private List<Komentar> komentari = new ArrayList<>();
    @NotNull
    @NotEmpty
    private List<Tag> tagovi = new ArrayList<>();
    @NotNull
    private Kategorija kategorija;

    Integer id;

    public  Vest(){

    }
    public Vest(@NotEmpty @NotNull String naslov, @NotEmpty @NotNull String tekst,
                 Korisnik autor, @NotNull Kategorija kategorija, @NotNull @NotEmpty List<Tag> tagovi) {
        this.naslov = naslov;
        this.tagovi = tagovi;
        this.tekst = tekst;
        this.vremeKreiranja = new GregorianCalendar();
        this.brojPoseta = 0;
        this.autor = autor;
        this.kategorija = kategorija;
    }
    public Vest(@NotEmpty @NotNull String naslov, @NotEmpty @NotNull String tekst, GregorianCalendar vremeKreiranja,
                 int brojPoseta,  Korisnik autor, @NotNull Kategorija kategorija) {
        this.naslov = naslov;
        this.tekst = tekst;
        this.vremeKreiranja = vremeKreiranja;
        this.brojPoseta = brojPoseta;
        this.autor = autor;
        this.kategorija = kategorija;
    }



    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public GregorianCalendar getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(GregorianCalendar vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public void setBrojPoseta(int brojPoseta) {
        this.brojPoseta = brojPoseta;
    }

    public void setAutor(Korisnik autor) {
        this.autor = autor;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public void setTagovi(List<Tag> tagovi) {
        this.tagovi = tagovi;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getTekst() {
        return tekst;
    }


    public int getBrojPoseta() {
        return brojPoseta;
    }

    public Korisnik getAutor() {
        return autor;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public List<Tag> getTagovi() {
        return tagovi;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vest vest = (Vest) o;
        return id.equals(vest.id);
    }

    @Override
    public String toString() {
        return "Vest{" +
                "naslov='" + naslov + '\'' +
                ", tekst='" + tekst + '\'' +
                ", vremeKreiranja=" + vremeKreiranja.get(Calendar.DAY_OF_MONTH) +"."+ (vremeKreiranja.get(Calendar.MONTH)+1) +"."+ vremeKreiranja.get(Calendar.YEAR) +
                ", brojPoseta=" + brojPoseta +
                ", autor=" + autor +
                ", komentari=" + komentari +
                ", tagovi=" + tagovi +
                ", kategorija=" + kategorija +
                ", id=" + id +
                '}';
    }
}
