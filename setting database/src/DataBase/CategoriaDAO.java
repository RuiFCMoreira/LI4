package DataBase;


import model.SSParques.Categoria;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;

import javax.sql.rowset.serial.SerialBlob;


public class CategoriaDAO implements Serializable {
    private Connection connection;

    public CategoriaDAO() {
        try{
            this.connection = ConnectDB.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Categoria getCategoria(String idCategoria) {
        String sql = "SELECT * FROM Categorias WHERE idCategoria=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Blob image = rs.getBlob(2);

                return new Categoria(idCategoria, null);

            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addCategoria(Categoria categoria){
        String sql = "INSERT INTO Categorias(idCategoria,Icone) VALUES(?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setBlob(2, new SerialBlob(new byte[0]));
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setCategoria(Categoria categoria){
        String sql = "UPDATE Categorias Icone=? WHERE idCategoria=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,categoria.getNome());
            stmt.setBlob(2, (InputStream) null);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCategoria(String id){

        removeParqueHasCategoria(id);

        String sql ="DELETE FROM Categorias WHERE idCategoria=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeParqueHasCategoria(String id){
        String sql ="DELETE FROM Parques_has_Categorias WHERE Categorias_idCategoria=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void main(String args[]){
        CategoriaDAO dao = new CategoriaDAO();





        Categoria c1 = new Categoria("Parque_De_Lazer", null);
        Categoria c2 = new Categoria("Parque_Urbano" , null);
        Categoria c3 = new Categoria("Parque_Infatil", null);
        Categoria c4 = new Categoria("Parque_Campismo", null);
        Categoria c5 = new Categoria("Parque_Diversoes", null);
        Categoria c6 = new Categoria("Parque_Patinagem", null);
        Categoria c7 = new Categoria("Parque_Ecologico", null);
        Categoria c8 = new Categoria("Parque_Cidade", null);
        Categoria c9 = new Categoria("Parque_Estatal", null);
        Categoria c10 = new Categoria("Parque_Nacional", null);
        Categoria c11 = new Categoria("Jardim", null);
        Categoria c12 = new Categoria("Reserva_Natural", null);


        dao.removeCategoria(c1.getNome());
        dao.removeCategoria(c2.getNome());
        dao.removeCategoria(c3.getNome());
        dao.removeCategoria(c4.getNome());
        dao.removeCategoria(c5.getNome());
        dao.removeCategoria(c6.getNome());
        dao.removeCategoria(c7.getNome());
        dao.removeCategoria(c8.getNome());
        dao.removeCategoria(c9.getNome());
        dao.removeCategoria(c10.getNome());
        dao.removeCategoria(c11.getNome());
        dao.removeCategoria(c12.getNome());


        dao.addCategoria(c1);
        dao.addCategoria(c2);
        dao.addCategoria(c3);
        dao.addCategoria(c4);
        dao.addCategoria(c5);
        dao.addCategoria(c6);
        dao.addCategoria(c7);
        dao.addCategoria(c8);
        dao.addCategoria(c9);
        dao.addCategoria(c10);
        dao.addCategoria(c11);
        dao.addCategoria(c12);



        Categoria res = dao.getCategoria("Parque_De_Lazer");

        System.out.println(res);


    }

}
