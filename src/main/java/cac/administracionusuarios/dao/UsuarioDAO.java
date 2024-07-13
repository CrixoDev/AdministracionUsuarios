package cac.administracionusuarios.dao;

import cac.administracionusuarios.conexion.ConexionDB;
import cac.administracionusuarios.modelo.Usuario;
/**
 *
 * @author Crixo
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO users (name, email, userName, password, contactNo, city, address, role, created_at, updated_at) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getName());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getUserName());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setString(5, usuario.getContactNo());
            pstmt.setString(6, usuario.getCity());
            pstmt.setString(7, usuario.getAddress());
            pstmt.setString(8, usuario.getRole());
            pstmt.setTimestamp(9, new java.sql.Timestamp(usuario.getCreated_at().getTime()));
            pstmt.setTimestamp(10, new java.sql.Timestamp(usuario.getUpdated_at().getTime()));

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Usuario extraerUsuarioDeResultSet(ResultSet rs) throws Exception {
        Usuario usuario = new Usuario(
                rs.getInt("userId"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("userName"),
                rs.getString("password"),
                rs.getString("contactNo"),
                rs.getString("city"),
                rs.getString("address"),
                rs.getString("role"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"));
        return usuario;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = ConexionDB.obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = extraerUsuarioDeResultSet(rs);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario obtenerPorId(int id) {
        String query = "SELECT * FROM users WHERE userId = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioDeResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean modificar(Usuario usuario) {
        String query = "UPDATE users SET name = ?, email = ?, userName = ?, password = ?, contactNo = ?, city = ?, address = ?, role = ?, updated_at = ? WHERE userId = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario.getName());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getUserName());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setString(5, usuario.getContactNo());
            pstmt.setString(6, usuario.getCity());
            pstmt.setString(7, usuario.getAddress());
            pstmt.setString(8, usuario.getRole());
            pstmt.setTimestamp(9, new java.sql.Timestamp(usuario.getUpdated_at().getTime()));
            pstmt.setInt(10, usuario.getId());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM users WHERE userId = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
