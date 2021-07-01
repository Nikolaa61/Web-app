package com.example.ProjekatWeb.Resources;

import com.example.ProjekatWeb.Entities.Vest;
import com.example.ProjekatWeb.Services.VestiService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/vesti")
public class VestiResource {
    @Inject
    VestiService vestiService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vest> allVesti(){return vestiService.allVesti();}

    @GET
    @Path("/{tekst}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vest> vestiPoTekstu(@PathParam("tekst") String tekst){return vestiService.vestiPoTekstu(tekst);}

    @GET
    @Path("/kategorija/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vest> vestiPoKategoriji(@PathParam("id") Integer id){return vestiService.vestiPoKategoriji(id);}

    @GET
    @Path("/najnovije")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vest> desetNajnovijih(){return vestiService.desetNajnovijih();}

    @GET
    @Path("/najcitanije")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vest> desetNajcitanijihPoslednjih30Dana(){return vestiService.desetNajcitanijihPoslednjih30Dana();}

    @POST
    @Path("/1")
    @Produces(MediaType.APPLICATION_JSON)
    public Vest addVest(@Valid Vest vest){return vestiService.addVest(vest);}

    @POST
    @Path("/povecaj/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void povecajBrojPoseta(@PathParam("id") Integer id){vestiService.povecajBrojPoseta(id);}

    @GET
    @Path("/tag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vest> vestiPoTagu(@PathParam("tag") String tag){return vestiService.vestiPoTagu(tag);}

    @GET
    @Path("/vest/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vest findVest(@PathParam("id") Integer id){return vestiService.findVest(id);}

    @POST
    @Path("/2")
    @Produces(MediaType.APPLICATION_JSON)
    public Vest updateVest(@Valid Vest vest){return vestiService.updateVest(vest);}

    @DELETE
    @Path("/{id}")
    public void deleteVest(@PathParam("id") Integer id){vestiService.deleteVest(id);}

}
