package com.example.ProjekatWeb.Repositories.Korisnici;

import com.example.ProjekatWeb.Entities.Korisnik;

import java.util.List;

public interface KorisniciRepo {
    public List<Korisnik> allKorisnici();
    public Korisnik addKorisnik(Korisnik korisnik);
    public Korisnik findKorisnik(String email);
    public Korisnik updateKorisnik(Korisnik korisnik);
    public Integer aktiviraj(Integer id);
    public Integer deaktiviraj(Integer id);

}
