package com.example.ProjekatWeb.Services;

import com.example.ProjekatWeb.Entities.Vest;
import com.example.ProjekatWeb.Repositories.Vesti.VestiRepo;

import javax.inject.Inject;
import java.util.List;

public class VestiService {
    @Inject
    VestiRepo vestiRepo;

    public List<Vest> allVesti(){return vestiRepo.allVesti();}
    public List<Vest> vestiPoTekstu(String tekst){return vestiRepo.vestiPoTekstu(tekst);}
    public List<Vest> vestiPoTagu(String tag){return vestiRepo.vestiPoTagu(tag);}
    public List<Vest> desetNajnovijih(){return vestiRepo.desetNajnovijih();}
    public List<Vest> desetNajcitanijihPoslednjih30Dana(){return vestiRepo.desetNajcitanijihPoslednjih30Dana();}
    public Vest addVest(Vest vest){return vestiRepo.addVest(vest);}
    public Vest findVest(Integer id){return vestiRepo.findVest(id);}
    public Vest updateVest(Vest vest){return vestiRepo.updateVest(vest);}
    public void deleteVest(Integer id){vestiRepo.deleteVest(id);}
    public void povecajBrojPoseta(Integer id){vestiRepo.povecajBrojPoseta(id);}
    public List<Vest> vestiPoKategoriji(Integer id){return vestiRepo.vestiPoKategoriji(id);}


}
