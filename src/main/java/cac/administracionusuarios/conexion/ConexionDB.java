package cac.administracionusuarios.conexion;

import cac.administracionusuarios.dao.UsuarioDAO;
import cac.administracionusuarios.modelo.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ConexionDB {

    private static final String url = "jdbc:mysql://localhost:3306/usermanagement";
    private static final String user = "root";
    private static final String password = "123456";

    public static Connection obtenerConexion() throws SQLException {
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    // Prueba de conexion
    public static void main(String[] args) {
        try {
            Connection con = ConexionDB.obtenerConexion();
            if (con != null) {
                System.out.println("Conexion exitosa");
                UsuarioDAO userDao = new UsuarioDAO();
                List<Usuario> lista = userDao.obtenerTodos();
                for (Usuario lista1 : lista) {
                    System.out.println(lista1.toString());
                }
                // testInsertarUsuario();
                System.out.println(userDao.eliminar(12));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "algo");
        }
    }

    public static void testInsertarUsuario() {
        // Preparar datos de prueba
        Usuario usuario = new Usuario();
        usuario.setName("Nombre de Prueba22");
        usuario.setEmail("prueba22@example.com");
        usuario.setUserName("prueba_user22");
        usuario.setPassword("contraseña12322");
        usuario.setContactNo("12345678922");
        usuario.setCity("Ciudad de Prueba22");
        usuario.setAddress("Calle de Prueba 12232");
        usuario.setRole("rol_de_prueba22");
        usuario.setCreated_at(new Date()); // Reemplaza con la fecha de creación actual
        usuario.setUpdated_at(new Date()); // Reemplaza con la fecha de actualización actual

        // Instanciar UsuarioDAO y llamar al método insertarUsuario
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean resultado = usuarioDAO.insertarUsuario(usuario);

        System.out.println(resultado);
    }

}
