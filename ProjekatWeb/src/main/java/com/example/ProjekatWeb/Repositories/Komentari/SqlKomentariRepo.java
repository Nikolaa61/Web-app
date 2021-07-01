package com.example.ProjekatWeb.Repositories.Komentari;

import com.example.ProjekatWeb.Entities.Komentar;
import com.example.ProjekatWeb.Entities.Korisnik;
import com.example.ProjekatWeb.Entities.ZaKategoriju.Status;
import com.example.ProjekatWeb.Entities.ZaKategoriju.TipKorisnika;
import com.example.ProjekatWeb.Repositories.Kategorija.KategorijeRepo;
import com.example.ProjekatWeb.Repositories.SqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class SqlKomentariRepo extends SqlAbstractRepository implements KomentariRepo {

    @Override
    public Komentar addKomentar(Komentar komentar) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "komentari (ime_autora, komentar, datum_kreiranja, id_vesti) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, komentar.getAutor());
            preparedStatement.setString(2, komentar.getKomentar());
            preparedStatement.setDate(3, new Date(new GregorianCalendar().getTimeInMillis()));
            preparedStatement.setInt(4, komentar.getId_vesti());
            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return komentar;
    }

    @Override
    public List<Komentar> allKomentari(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Komentar> komentari= new ArrayList<>();
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM komentari where id_vesti = ? order by datum_kreiranja desc");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Date date =resultSet.getDate("datum_kreiranja");
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date);

                Komentar komentar = new Komentar(resultSet.getString("ime_autora"),
                        resultSet.getString("komentar"), cal, resultSet.getInt("id_vesti"));
                komentari.add(komentar);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return komentari;


    }
}
