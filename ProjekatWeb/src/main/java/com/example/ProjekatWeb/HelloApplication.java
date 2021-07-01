package com.example.ProjekatWeb;

import com.example.ProjekatWeb.Repositories.Kategorija.KategorijeRepo;
import com.example.ProjekatWeb.Repositories.Kategorija.SqlKategorijeRepo;
import com.example.ProjekatWeb.Repositories.Komentari.KomentariRepo;
import com.example.ProjekatWeb.Repositories.Komentari.SqlKomentariRepo;
import com.example.ProjekatWeb.Repositories.Korisnici.KorisniciRepo;
import com.example.ProjekatWeb.Repositories.Korisnici.SqlKorisniciRepo;
import com.example.ProjekatWeb.Repositories.TagVest.SqlTagVestRepo;
import com.example.ProjekatWeb.Repositories.TagVest.TagVestRepo;
import com.example.ProjekatWeb.Repositories.Tagovi.SqlTagoviRepo;
import com.example.ProjekatWeb.Repositories.Tagovi.TagoviRepo;
import com.example.ProjekatWeb.Repositories.Vesti.SqlVestiRepo;
import com.example.ProjekatWeb.Repositories.Vesti.VestiRepo;
import com.example.ProjekatWeb.Services.KategorijeService;
import com.example.ProjekatWeb.Services.KomentariService;
import com.example.ProjekatWeb.Services.KorisniciService;
import com.example.ProjekatWeb.Services.VestiService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(SqlKategorijeRepo.class).to(KategorijeRepo.class).in(Singleton.class);
                this.bind(SqlKomentariRepo.class).to(KomentariRepo.class).in(Singleton.class);
                this.bind(SqlKorisniciRepo.class).to(KorisniciRepo.class).in(Singleton.class);
                this.bind(SqlTagoviRepo.class).to(TagoviRepo.class).in(Singleton.class);
                this.bind(SqlTagVestRepo.class).to(TagVestRepo.class).in(Singleton.class);
                this.bind(SqlVestiRepo.class).to(VestiRepo.class).in(Singleton.class);

                this.bindAsContract(KategorijeService.class);
                this.bindAsContract(KorisniciService.class);
                this.bindAsContract(VestiService.class);
                this.bindAsContract(KomentariService.class);

            }
        };
        register(binder);


        // Ucitavamo resurse
        packages("com.example.ProjekatWeb");
    }
}