package com.example.ProjekatWeb.Repositories.Vesti;

import com.example.ProjekatWeb.Entities.Vest;

import java.util.List;

public interface VestiRepo {
    public List<Vest> allVesti();
    public List<Vest> vestiPoTekstu(String tekst);
    public List<Vest> vestiPoKategoriji(Integer idKategorije);
    public List<Vest> vestiPoTagu(String tag);
    public Vest addVest(Vest vest);
    public Vest findVest(Integer id);
    public Vest updateVest(Vest vest);
    public void deleteVest(Integer id);
    public void povecajBrojPoseta(Integer id);
    public List<Vest> desetNajnovijih();
    public List<Vest> desetNajcitanijihPoslednjih30Dana();

}
