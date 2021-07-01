package com.example.ProjekatWeb.Entities;

import com.example.ProjekatWeb.Entities.ZaKategoriju.Status;
import com.example.ProjekatWeb.Entities.ZaKategoriju.TipKorisnika;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Korisnik implements Serializable {
    @NotNull
    @NotEmpty
    private String email; // pk
    @NotNull
    @NotEmpty
    private String ime;
    @NotNull
    @NotEmpty
    private String prezime;
    private Status status;
    @NotNull
    private TipKorisnika tipKorisnika;
    @NotNull
    @NotEmpty
    private String password;

    Integer id;

    public Korisnik(){

    }

    public Korisnik(Integer id){
        this.id = id;
    }
    public Korisnik(@NotNull @NotEmpty String email, @NotNull @NotEmpty String ime, @NotNull @NotEmpty String prezime,
                   Status status, @NotNull TipKorisnika tipKorisnika, @NotNull @NotEmpty String password) {
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
        this.status = status;
        this.tipKorisnika = tipKorisnika;
        this.password = password;
    }

    public Korisnik(@NotNull @NotEmpty String email, @NotNull @NotEmpty String ime, @NotNull @NotEmpty String prezime) {
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getIme() {
        return ime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrezime() {
        return prezime;
    }

    public Status getStatus() {
        return status;
    }

    public TipKorisnika getTipKorisnika() {
        return tipKorisnika;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTipKorisnika(TipKorisnika tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
