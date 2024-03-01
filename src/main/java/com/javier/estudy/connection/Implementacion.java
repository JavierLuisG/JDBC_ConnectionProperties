package com.javier.estudy.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Implementacion {

    private Connection conn;
    private PreparedStatement statement;
    private ResultSet rs;

    // nombramiento metodo ejecutadorConsulta
    public void ejecutadorConsulta() {

        String insertQuery = "INSERT INTO usuario(nombre, edad, telefono, correo, idioma) VALUES (?,?,?,?,?)";
        String selectQuery = "SELECT * FROM usuario";

        conn = DatabaseConnection.getInstance().getConnection();

        try {
            /**
             * Activar una transaccion por medio de setAutoCommit Con false se
             * inicia una transaccion implicitamente y no se ejecuta sino hasta
             * confirmarla al final de mismo.
             * Con .commit() se confirma 
             * Con .rollback() se cancela
             */
            conn.setAutoCommit(false);

            /**
             * En la forma como se presenta a continuación los dos primeros
             * registros aparecen bien y el tercero tiene un null, ese null
             * arrojará un error pero los dos primeros se ejecutarán y se
             * guardarán en la base de datos para ello se utiliza una
             * transaccion que permite ejecutarse si todos están bien, de lo
             * contrario no, en este caso con autoCommit en False no se guardará
             * ningun dato por el error en el tercero
             */
            statement = conn.prepareStatement(insertQuery); // Preparar la query
            // Se envían los datos a la query preparada (se ingresan los datos de forma directa)
            statement.setString(1, "Heidy");
            statement.setInt(2, 21);
            statement.setString(3, "431456");
            statement.setString(4, "samuelito@gmail.com");
            statement.setString(5, "Español");
            int filasAfectadas1 = statement.executeUpdate(); //Ejecutar la inserción
            System.out.println("executeUpdate: " + filasAfectadas1);

            statement.setString(1, "Darwin");
            statement.setInt(2, 21);
            statement.setString(3, "431456");
            statement.setString(4, "samuelito@gmail.com");
            statement.setString(5, "Español");
            int filasAfectadas2 = statement.executeUpdate(); //Ejecutar la inserción
            System.out.println("executeUpdate: " + filasAfectadas2);

            statement.setString(1, "Susana");
            statement.setInt(2, 21);
            statement.setString(3, null); // por este null(arroja un fallo) la transaccion termina en rollback, 
            statement.setString(4, "samuelito@gmail.com");
            statement.setString(5, "Español");
            int filasAfectadas3 = statement.executeUpdate(); //Ejecutar la inserción
            System.out.println("executeUpdate: " + filasAfectadas3);

            conn.commit(); // Se confirma en caso que todo salga bien

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
        } catch (SQLException e) {
            try {
                /**
                 * Realizar el rollback es muy importante ya que si se realizan
                 * operaciones despues de esta se podran ejecutar, de lo contrario no.
                 */
                conn.rollback(); // en caso de error en alguna operación, se cancelan las operaciones involucradas
            } catch (SQLException ex) {
                System.err.println("Error en el rollback, " + ex);
            }
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
