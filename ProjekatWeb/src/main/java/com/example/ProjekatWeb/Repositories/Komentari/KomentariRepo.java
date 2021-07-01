package com.example.ProjekatWeb.Repositories.Komentari;

import com.example.ProjekatWeb.Entities.Komentar;

import java.util.List;

public interface KomentariRepo {
    public Komentar addKomentar(Komentar komentar);
    public List<Komentar> allKomentari(Integer id);

}
