package com.example.ProjekatWeb.Repositories.Kategorija;

import com.example.ProjekatWeb.Entities.Kategorija;

import java.util.List;

public interface KategorijeRepo {
    public Kategorija addKategorija(Kategorija kategorija);
    public List<Kategorija> allKategories();
    public Kategorija findKategorija(String naziv);
    public Kategorija updateKategorija(Kategorija kategorija); /// samo sibni u api sve post zahteve kao payload
    public boolean deleteKategorija(Integer id);

}
