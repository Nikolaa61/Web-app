package com.example.ProjekatWeb.Repositories.Kategorija;

import com.example.ProjekatWeb.Entities.Kategorija;
import com.example.ProjekatWeb.Entities.Korisnik;
import com.example.ProjekatWeb.Entities.ZaKategoriju.Status;
import com.example.ProjekatWeb.Entities.ZaKategoriju.TipKorisnika;
import com.example.ProjekatWeb.Repositories.SqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlKategorijeRepo extends SqlAbstractRepository implements KategorijeRepo {

    @Override
    public Kategorija addKategorija(Kategorija kategorija) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("SELECT * FROM kategorije where ime_kategorije = ?");
            preparedStatement.setString(1, kategorija.getIme());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Postoji kategorija sa tim imenom");
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO kategorije (ime_kategorije, opis) VALUES (?, ?)", generatedColumns);
                preparedStatement.setString(1, kategorija.getIme());
                preparedStatement.setString(2, kategorija.getOpis());
                preparedStatement.executeUpdate();

                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    kategorija.setId(resultSet.getInt(1));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return kategorija;
    }

    @Override
    public List<Kategorija> allKategories() {
        List<Kategorija> kategorije = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from kategorije");

            while (resultSet.next()) {
                kategorije.add(new Kategorija(resultSet.getString("ime_kategorije"),
                        resultSet.getString("opis"), resultSet.getInt("id_kategorije")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(connection);
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
        }
        return kategorije;
    }

    @Override
    public Kategorija findKategorija(String naziv) {
        Kategorija kategorija = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM kategorije where ime_kategorije = ?");
            preparedStatement.setString(1, naziv);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String opis = resultSet.getString("opis");
                Integer id = resultSet.getInt("id_kategorije");
                kategorija = new Kategorija(naziv, opis);
                kategorija.setId(id);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return kategorija;
    }

    @Override
    public Kategorija updateKategorija(Kategorija kategorija) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("update kategorije set ime_kategorije = ?, opis = ? where id_kategorije = ?");
            preparedStatement.setString(1, kategorija.getIme());
            preparedStatement.setString(2, kategorija.getOpis());
            preparedStatement.setInt(3, kategorija.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return kategorija;
    }

    @Override
    public boolean deleteKategorija(Integer id) {
        boolean moze = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("select * from vesti where id_kategorije = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                moze = true;
                preparedStatement = connection.prepareStatement("delete from kategorije where id_kategorije = ?");
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
            }else{
               moze = false;
            }



            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
            return moze;
        }
    }


}
