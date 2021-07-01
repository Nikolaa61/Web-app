package com.example.ProjekatWeb.Services;

import com.example.ProjekatWeb.Entities.Kategorija;
import com.example.ProjekatWeb.Repositories.Kategorija.KategorijeRepo;

import javax.inject.Inject;
import java.util.List;

public class KategorijeService {
    @Inject
    KategorijeRepo kategorijeRepo;

    public Kategorija addKategorija(Kategorija kategorija){return this.kategorijeRepo.addKategorija(kategorija);}
    public List<Kategorija> allKategories(){return this.kategorijeRepo.allKategories();}
    public Kategorija findKategorija(String naziv){return this.kategorijeRepo.findKategorija(naziv);}
    public Kategorija updateKategorija(Kategorija kategorija){return this.kategorijeRepo.updateKategorija(kategorija);} /// samo sibni u api sve post zahteve kao payload
    public boolean deleteKategorija(Integer id){return this.kategorijeRepo.deleteKategorija(id);}


}
