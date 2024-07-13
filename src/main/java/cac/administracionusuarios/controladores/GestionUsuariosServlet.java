package cac.administracionusuarios.controladores;

import cac.administracionusuarios.dao.UsuarioDAO;
import cac.administracionusuarios.modelo.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Crixo
 */
@WebServlet("/usuarios")
public class GestionUsuariosServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GestionUsuariosServlet.class.getName());
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            objectMapper.writeValue(response.getWriter(), usuarios);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener usuarios", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getWriter(), new ErrorResponse("Ocurrió un error al obtener usuarios"));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = objectMapper.readValue(request.getReader(), Usuario.class);
        usuario.setCreated_at(new Date());
        usuario.setUpdated_at(new Date());
        try {
            // Intentar insertar el usuario en la base de datos
            boolean insertado = usuarioDAO.insertarUsuario(usuario);
            if (insertado) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(response.getWriter(), usuario);
                response.sendRedirect("index.html?exito=true");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(response.getWriter(),
                        new ErrorResponse("No se pudo insertar el usuario" + usuario.toString()));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al insertar usuario", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getWriter(),
                    new ErrorResponse("Ocurrió un error al insertar el usuario" + usuario.toString()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Usuario usuario = objectMapper.readValue(request.getReader(), Usuario.class);
        try {
            boolean modificado = usuarioDAO.modificar(usuario);
            if (modificado) {
                objectMapper.writeValue(response.getWriter(), usuario);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(response.getWriter(), new ErrorResponse("No se pudo modificar el usuario"));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al modificar usuario", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getWriter(),
                    new ErrorResponse("Ocurrió un error al modificar el usuario"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int idUsuario = Integer.parseInt(request.getParameter("id"));

        try {
            boolean eliminado = usuarioDAO.eliminar(idUsuario);
            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(response.getWriter(), new ErrorResponse("No se pudo eliminar el usuario"));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar usuario", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getWriter(), new ErrorResponse("Ocurrió un error al eliminar el usuario"));
        }
    }

    /*
     * @Override
     * protected void doDelete(HttpServletRequest request, HttpServletResponse
     * response) throws IOException {
     * response.setContentType("application/json");
     * response.setCharacterEncoding("UTF-8");
     * 
     * try {
     * // Leer el cuerpo de la solicitud para obtener el ID
     * StringBuilder sb = new StringBuilder();
     * String line;
     * try (BufferedReader reader = request.getReader()) {
     * while ((line = reader.readLine()) != null) {
     * sb.append(line);
     * }
     * }
     * String json = sb.toString();
     * ObjectMapper objectMapper = new ObjectMapper();
     * Map<String, Integer> data = objectMapper.readValue(json, Map.class);
     * int idUsuario = data.get("id");
     * 
     * boolean eliminado = usuarioDAO.eliminar(idUsuario);
     * if (eliminado) {
     * response.setStatus(HttpServletResponse.SC_NO_CONTENT);
     * } else {
     * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
     * objectMapper.writeValue(response.getWriter(), new
     * ErrorResponse("No se pudo eliminar el usuario"));
     * }
     * } catch (Exception e) {
     * logger.log(Level.SEVERE, "Error al eliminar usuario", e);
     * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
     * objectMapper.writeValue(response.getWriter(), new
     * ErrorResponse("Ocurrió un error al eliminar el usuario"));
     * }
     * }
     */

    // Clase de respuesta de error para JSON
    static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}