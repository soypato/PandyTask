import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:identifier.sqlite.tablaDeUsuarios";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, email TEXT)";
        String insertUserSQL = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
        String nombreUsuario = "Usuario de Prueba";
        String emailUsuario = "prueba@example.com";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement createStmt = conn.prepareStatement(createTableSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertUserSQL)) {

            // Crear la tabla si no existe
            createStmt.executeUpdate();

            // Establecer los valores de los parámetros de la instrucción SQL
            insertStmt.setString(1, nombreUsuario);
            insertStmt.setString(2, emailUsuario);

            // Ejecutar la instrucción SQL para insertar el usuario
            insertStmt.executeUpdate();

            System.out.println("Usuario insertado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
        }
    }
}
