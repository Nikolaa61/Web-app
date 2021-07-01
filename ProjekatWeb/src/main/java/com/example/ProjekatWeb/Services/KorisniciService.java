package com.example.ProjekatWeb.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.ProjekatWeb.Entities.Korisnik;
import com.example.ProjekatWeb.Repositories.Korisnici.KorisniciRepo;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class KorisniciService {
    @Inject
    KorisniciRepo korisniciRepo;

    public List<Korisnik> allKorisnici(){return korisniciRepo.allKorisnici();}
    public Korisnik addKorisnik(Korisnik korisnik){return korisniciRepo.addKorisnik(korisnik);}
    public Korisnik findKorisnik(String email){return korisniciRepo.findKorisnik(email);}
    public Korisnik updateKorisnik(Korisnik korisnik){return korisniciRepo.updateKorisnik(korisnik);}
    public Integer aktiviraj(Integer id){return korisniciRepo.aktiviraj(id);}
    public Integer deaktiviraj(Integer id){return korisniciRepo.deaktiviraj(id);}

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        Korisnik user = this.korisniciRepo.findKorisnik(email);
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("status", user.getStatus().toString())
                .withClaim("role", user.getTipKorisnika().toString())
                .withClaim("nick", user.getIme()+" "+user.getPrezime())
                .withClaim("id", user.getId().toString())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();


        Korisnik user = this.korisniciRepo.findKorisnik(email);


        if (user == null || jwt.getClaim("status").asString().equals("INACTIVE")){
            return false;
        }

        return true;
    }

}
