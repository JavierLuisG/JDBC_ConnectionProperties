package com.javier.estudy.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Implementacion {
    
    private Connection conn;
    private PreparedStatement statement;
    private ResultSet rs;
   
    // nombramiento metodo ejecutadorConsulta
    public void ejecutadorConsulta(){
        
        String insertQuery = "INSERT INTO usuario(nombre, edad, telefono, correo, idioma) VALUES (?,?,?,?,?)";
        String selectQuery = "SELECT * FROM usuario";

        try {            
            conn = DatabaseConnection.getInstance().getConnection();
            statement = conn.prepareStatement(insertQuery); // Preparar la query
            // Se envían los datos a la query preparada (se ingresan los datos de forma directa)
            statement.setString(1, "Martin");
            statement.setInt(2, 22);
            statement.setString(3, "331456");
            statement.setString(4, "martin@gmail.com");
            statement.setString(5, "Ingles");            
            int filasAfectadas = statement.executeUpdate(); //Ejecutar la inserción
            
            rs = statement.executeQuery(selectQuery); // Recibir los datos de la base de datos por medio del ResultSet
            // Imprimir los datos recuperados
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ": "
                        + rs.getString("nombre") + "\t"
                        + rs.getInt("edad") + "\t"
                        + rs.getString("telefono") + "\t"
                        + rs.getString("correo") + "\t"
                        + rs.getString("idioma"));
            }
            // verificar si el usuario fue creado o no por medio de la verificacion de insercion
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario creado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear el usuario");
            }
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos, " + e);
        } finally {
            if (statement != null) { // Cerrar el PreparedStatement
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.err.println("No se pudo cerrar el Prepared Statement");
                }                
            }
            if (rs != null) { // Cerrar el ResultSet             
                try {
                    rs.close(); 
                } catch (SQLException ex) {
                    System.err.println("No se pudo cerrar el Result Set");                
                }
            }
            DatabaseConnection.getInstance().closeConnection(); // Cerrar la conexion
        }
    }
}
