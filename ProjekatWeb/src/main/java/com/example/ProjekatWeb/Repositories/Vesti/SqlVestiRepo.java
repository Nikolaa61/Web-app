package com.example.ProjekatWeb.Repositories.Vesti;

import com.example.ProjekatWeb.Entities.*;
import com.example.ProjekatWeb.Entities.ZaKategoriju.Status;
import com.example.ProjekatWeb.Entities.ZaKategoriju.TipKorisnika;
import com.example.ProjekatWeb.Repositories.SqlAbstractRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.*;
import java.sql.Date;

import java.util.*;

public class SqlVestiRepo extends SqlAbstractRepository implements VestiRepo {
    @Override
    public List<Vest> allVesti() {
        List<Vest> vesti = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id order by vreme_kreiranja desc");


            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }

                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));

                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if (komentar != null)
                    v.getKomentari().add(komentar);
                if(!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                } else {
                    int idx = vesti.indexOf(v);
                    Vest vest = vesti.get(idx);
                    if (komentar != null)
                        vest.getKomentari().add(komentar);
                    if(!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return vesti;
    }

    @Override
    public List<Vest> vestiPoTekstu(String tekst) {
        List<Vest> vesti = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id order by vreme_kreiranja desc");


            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }

                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));

                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if(komentar != null)
                    v.getKomentari().add(komentar);
                if(!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                } else {
                    int idx = vesti.indexOf(v);
                    Vest vest = vesti.get(idx);
                    if (komentar != null)
                        vest.getKomentari().add(komentar);
                    if(!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        List<Vest> praveVesti = new ArrayList<>();
        for (Vest v : vesti) {
            if (v.getTekst().toLowerCase().contains(tekst.toLowerCase()) ||
                    v.getNaslov().toLowerCase().contains(tekst.toLowerCase())) {
                praveVesti.add(v);
            }
        }
        return praveVesti;
    }

    @Override
    public List<Vest> vestiPoKategoriji(Integer idKategorije) {
        List<Vest> vesti = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();


            preparedStatement = connection.prepareStatement("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id where vesti.id_kategorije = ? order by vreme_kreiranja desc");
            preparedStatement.setInt(1, idKategorije);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }

                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));

                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if (komentar != null)
                    v.getKomentari().add(komentar);
                if (!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                } else {
                    int idx = vesti.indexOf(v);
                    Vest vest = vesti.get(idx);
                    if (komentar != null)
                        vest.getKomentari().add(komentar);
                    if(!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return vesti;
    }

    @Override
    public List<Vest> vestiPoTagu(String tag1) {
        List<Vest> vesti = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id order by vreme_kreiranja desc");


            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }

                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));

                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if(komentar != null)
                    v.getKomentari().add(komentar);
                if (!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                } else {
                    int idx = vesti.indexOf(v);
                    Vest vest = vesti.get(idx);
                    if (komentar != null)
                        vest.getKomentari().add(komentar);
                    if(!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        List<Vest> praveVesti = new ArrayList<>();
        for (Vest v : vesti) {
            boolean ima = false;
            for(Tag tag : v.getTagovi()){
                if(tag.getTag().equals(tag1)){
                    ima = true;
                }
            }
            if (ima){
                praveVesti.add(v);
            }
        }
        return praveVesti;
    }

    @Override
    public Vest addVest(Vest vest) {
        vest.setVremeKreiranja(new GregorianCalendar());
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO vesti " +
                    "(naslov, tekst, vreme_kreiranja, broj_poseta, id_autora, id_kategorije) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, vest.getNaslov());
            preparedStatement.setString(2, vest.getTekst());
            preparedStatement.setTimestamp(3, new Timestamp(vest.getVremeKreiranja().getTimeInMillis()));
            preparedStatement.setInt(4, vest.getBrojPoseta());
            preparedStatement.setInt(5, vest.getAutor().getId());
            preparedStatement.setInt(6, vest.getKategorija().getId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                vest.setId(resultSet.getInt(1));
            }
            String s = "";
            for (int i = 0; i < vest.getTagovi().size(); i++) {
                s += "('" + vest.getTagovi().get(i).getTag() + "', " + vest.getId() + ")";
                if (i != vest.getTagovi().size() - 1) {
                    s += ", ";
                }
            }

            preparedStatement = connection.prepareStatement("INSERT INTO tag_vest(tag, id_vesti) VALUES " + s);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select * from korisnici where id_korisnika = ?");
            preparedStatement.setInt(1, vest.getAutor().getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                vest.getAutor().setIme(resultSet.getString("ime_korisnika"));
                vest.getAutor().setPrezime(resultSet.getString("prezime"));
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return vest;
    }

    @Override
    public Vest findVest(Integer id) {
        Vest vest = null;
        List<Vest> vesti = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id where vesti.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }

                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));

                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if (komentar != null){
                    v.getKomentari().add(komentar);
                }
                if (!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                    vest = v;
                } else {
                    if (komentar != null)
                        vest.getKomentari().add(komentar);
                    if(!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return vest;
    }

    @Override
    public Vest updateVest(Vest vest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("update vesti set naslov = ?, tekst = ?, " +
                    "id_kategorije = ? where id = ?");
            preparedStatement.setString(1, vest.getNaslov());
            preparedStatement.setString(2, vest.getTekst());
            preparedStatement.setInt(3, vest.getKategorija().getId());
            preparedStatement.setInt(4, vest.getId());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete  from tag_vest where id_vesti = ?");
            preparedStatement.setInt(1, vest.getId());
            preparedStatement.executeUpdate();

            String s = "";
            for (int i = 0; i < vest.getTagovi().size(); i++) {
                s += "('" + vest.getTagovi().get(i).getTag() + "', " + vest.getId() + ")";
                if (i != vest.getTagovi().size() - 1) {
                    s += ", ";
                }
            }

            preparedStatement = connection.prepareStatement("INSERT INTO tag_vest(tag, id_vesti) VALUES " + s);
            preparedStatement.executeUpdate();
            preparedStatement.close();


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return vest;
    }

    @Override
    public void deleteVest(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();


            preparedStatement = connection.prepareStatement("delete from komentari where id_vesti = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete from vesti where id = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete  from tag_vest where id_vesti = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void povecajBrojPoseta(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();


            preparedStatement = connection.prepareStatement("update vesti set broj_poseta = broj_poseta + 1 " +
                    "where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Vest> desetNajnovijih() {
        List<Vest> vesti = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id order by vreme_kreiranja desc limit 10");


            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }


                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));
                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if(komentar != null){
                    v.getKomentari().add(komentar);
                }
                if(!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                } else {
                    int idx = vesti.indexOf(v);
                    Vest vest = vesti.get(idx);
                    if (komentar != null){
                        vest.getKomentari().add(komentar);
                    }
                    if(!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return vesti;
    }

    @Override
    public List<Vest> desetNajcitanijihPoslednjih30Dana() {
        List<Vest> vesti = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vesti left join korisnici on " +
                    "vesti.id_autora = korisnici.id_korisnika left join komentari on komentari.id_vesti = vesti.id " +
                    "left join kategorije on kategorije.id_kategorije = vesti.id_kategorije " +
                    "left join tag_vest on tag_vest.id_vesti = vesti.id where vreme_kreiranja " +
                    "between now() - interval 30 day and now()  order by broj_poseta desc limit 10");


            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"));

                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                Komentar komentar = null;
                if (date != null){
                    cal = new GregorianCalendar();
                    cal.setTime(date);

                    komentar = new Komentar(resultSet.getString("ime_autora"), resultSet.getString("komentar"),
                            cal);
                }

                Kategorija kategorija = new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"));

                Tag tag = new Tag(resultSet.getString("tag"));

                cal = new GregorianCalendar();

                cal.setTime(resultSet.getTimestamp("vreme_kreiranja"));

                Vest v = new Vest(resultSet.getString("naslov"), resultSet.getString("tekst"),
                        cal, resultSet.getInt("broj_poseta"),
                        korisnik, kategorija);
                if (komentar != null)
                    v.getKomentari().add(komentar);
                if(!v.getTagovi().contains(tag))
                    v.getTagovi().add(tag);

                v.setId(resultSet.getInt("id"));
                if (!vesti.contains(v)) {
                    vesti.add(v);
                } else {
                    int idx = vesti.indexOf(v);
                    Vest vest = vesti.get(idx);
                    if (komentar != null){
                        vest.getKomentari().add(komentar);
                    }
                    if (!vest.getTagovi().contains(tag))
                        vest.getTagovi().add(tag);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return vesti;
    }
}
