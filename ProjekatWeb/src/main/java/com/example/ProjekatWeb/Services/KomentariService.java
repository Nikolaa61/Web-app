package com.example.ProjekatWeb.Services;

import com.example.ProjekatWeb.Entities.Kategorija;
import com.example.ProjekatWeb.Entities.Komentar;
import com.example.ProjekatWeb.Repositories.Kategorija.KategorijeRepo;
import com.example.ProjekatWeb.Repositories.Komentari.KomentariRepo;

import javax.inject.Inject;
import java.util.List;

public class KomentariService {
    @Inject
    KomentariRepo komentariRepo;

    public Komentar addKomentar(Komentar komentar){return this.komentariRepo.addKomentar(komentar);}
    public List<Komentar> allKomentari(Integer id){return this.komentariRepo.allKomentari(id);}

}
