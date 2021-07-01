package com.example.ProjekatWeb.Resources;

import com.example.ProjekatWeb.Entities.Komentar;
import com.example.ProjekatWeb.Services.KomentariService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

@Path("/komentari")
public class KomentariResource {
    @Inject
    KomentariService komentariService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Komentar addKomentar(@Valid Komentar komentar){return this.komentariService.addKomentar(komentar);}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Komentar> allKomentari(@PathParam("id") Integer id){return this.komentariService.allKomentari(id);}
}
