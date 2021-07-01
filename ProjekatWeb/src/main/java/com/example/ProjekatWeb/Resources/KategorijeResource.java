package com.example.ProjekatWeb.Resources;

import com.example.ProjekatWeb.Entities.Kategorija;
import com.example.ProjekatWeb.Services.KategorijeService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

@Path("/kategorije")
public class KategorijeResource {
    @Inject
    private KategorijeService kategorijeService;

    @Path("1")//       /kategorije/1
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Kategorija addKategorija(@Valid Kategorija kategorija){return this.kategorijeService.addKategorija(kategorija);}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kategorija> allKategories(){return this.kategorijeService.allKategories();}

    @GET
    @Path("/{naziv}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kategorija findKategorija(@PathParam("naziv") String naziv){return this.kategorijeService.findKategorija(naziv);}

    @Path("2")//    /kategorije/a
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Kategorija updateKategorija(@Valid Kategorija kategorija){return this.kategorijeService.updateKategorija(kategorija);} /// samo sibni u api sve post zahteve kao payload

    @DELETE
    @Path("/{id}")
    public boolean deleteKategorija(@PathParam("id") Integer id){return this.kategorijeService.deleteKategorija(id);}
}
