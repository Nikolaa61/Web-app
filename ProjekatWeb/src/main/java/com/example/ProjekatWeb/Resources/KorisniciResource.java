package com.example.ProjekatWeb.Resources;

import com.example.ProjekatWeb.Entities.Korisnik;
import com.example.ProjekatWeb.Repositories.Korisnici.KorisniciRepo;
import com.example.ProjekatWeb.Services.KorisniciService;
import com.example.ProjekatWeb.requests.LoginRequest;
import javafx.application.Application;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/korisnici")
public class KorisniciResource {
    @Inject
    KorisniciService korisniciService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Korisnik> allKorisnici(){return korisniciService.allKorisnici();}

    @Path("/1")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik addKorisnik(@Valid Korisnik korisnik){return korisniciService.addKorisnik(korisnik);}

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik findKorisnik(@PathParam("email") String email){return korisniciService.findKorisnik(email);}

    @Path("/2")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik updateKorisnik(@Valid Korisnik korisnik){return korisniciService.updateKorisnik(korisnik);}

    @Path("/aktiviraj/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Integer aktiviraj(@PathParam("id") Integer id){return korisniciService.aktiviraj(id);}

    @Path("/deaktiviraj/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Integer deaktiviraj(@PathParam("id") Integer id){return korisniciService.deaktiviraj(id);}

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.korisniciService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        if (!this.korisniciService.isAuthorized(jwt)){
            response.put("message", "Korisnik nema pravo pristupa jer nije aktivan");
        }
        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

}
