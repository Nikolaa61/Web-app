package com.example.ProjekatWeb.Repositories.Korisnici;

import com.example.ProjekatWeb.Entities.Korisnik;
import com.example.ProjekatWeb.Entities.ZaKategoriju.Status;
import com.example.ProjekatWeb.Entities.ZaKategoriju.TipKorisnika;
import com.example.ProjekatWeb.Repositories.SqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlKorisniciRepo extends SqlAbstractRepository implements KorisniciRepo{
    @Override
    public List<Korisnik> allKorisnici() {
        List<Korisnik> korisnici = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from korisnici ");

            while (resultSet.next()) {
                Korisnik k = new Korisnik(resultSet.getString("email"), resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime"), Status.valueOf(resultSet.getString("status")),
                        TipKorisnika.valueOf(resultSet.getString("tip_korisnika")),
                        resultSet.getString("prezime"));
                k.setId(resultSet.getInt("id_korisnika"));
                korisnici.add(k);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return korisnici;
    }

    @Override
    public Korisnik addKorisnik(Korisnik korisnik) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnici where email = ?");
            preparedStatement.setString(1, korisnik.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                System.out.println("Postoji korisnik sa tim mailom");
            }else{
                preparedStatement = connection.prepareStatement("INSERT INTO korisnici (email, ime_korisnika, prezime, status," +
                        "tip_korisnika, password) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
                preparedStatement.setString(1, korisnik.getEmail());
                preparedStatement.setString(2, korisnik.getIme());
                preparedStatement.setString(3, korisnik.getPrezime());
                preparedStatement.setString(4, String.valueOf(korisnik.getStatus()));
                preparedStatement.setString(5, String.valueOf(korisnik.getTipKorisnika()));

                String hashedPassword = DigestUtils.sha256Hex(korisnik.getPassword());
                preparedStatement.setString(6, hashedPassword);

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    korisnik.setId(resultSet.getInt(1));
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public Korisnik findKorisnik(String email) {
        Korisnik korisnik = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnici where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String ime = resultSet.getString("ime_korisnika");
                String prezime = resultSet.getString("prezime");
                Status status = Status.valueOf(resultSet.getString("status"));
                TipKorisnika tipKorisnika = TipKorisnika.valueOf(resultSet.getString("tip_korisnika"));
                String password = resultSet.getString("password");
                Integer id = resultSet.getInt("id_korisnika");

                korisnik = new Korisnik(email, ime, prezime, status, tipKorisnika, password);

                korisnik.setId(id);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public Korisnik updateKorisnik(Korisnik korisnik) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("update korisnici set email = ?, ime_korisnika = ?, " +
                    "prezime = ?, tip_korisnika = ? where id_korisnika = ?");
            preparedStatement.setString(1, korisnik.getEmail());
            preparedStatement.setString(2, korisnik.getIme());
            preparedStatement.setString(3, korisnik.getPrezime());
            preparedStatement.setString(4, String.valueOf(korisnik.getTipKorisnika()));
            preparedStatement.setInt(5, korisnik.getId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public Integer aktiviraj(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("update korisnici set status = ? where id_korisnika = ?");
            preparedStatement.setString(1, String.valueOf(Status.ACTIVE));
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return id;
    }

    @Override
    public Integer deaktiviraj(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("update korisnici set status = ? where id_korisnika = ?");
            preparedStatement.setString(1, String.valueOf(Status.INACTIVE));
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return id;
    }


}
