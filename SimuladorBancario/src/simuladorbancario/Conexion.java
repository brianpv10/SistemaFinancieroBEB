/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simuladorbancario;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author User
 */
public class Conexion {
    public static Connection getConexion() {
        Connection conn = null;

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/banco?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace(); 
        }

        return conn;
    }
}
