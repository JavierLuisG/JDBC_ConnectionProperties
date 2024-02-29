package com.javier.estudy.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    
    Connection conn; // instancia que permite conexion a la base de datos
    Properties properties = new Properties(); // Clase que permite traer las propiedades de la databaseConnection.properties
    InputStream stream; // Clase que permite leer los datos del archivo que se indique
    
    String url;
    
    /**
     * @return Connection: permite la conexión a la base de datos 
     */    
    public Connection getConnection() {
        loadDatabaseProperties();
        String user = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion exitosa");
        } catch (SQLException ex) {
            System.out.println("Error, " + ex);
        }
        return conn;
    }
    
    /**
     * Método que permite leer la databaseConnection.properties y asignar los valores en la url
     */    
    public void loadDatabaseProperties() {
        try {
            stream = getClass().getClassLoader().getResourceAsStream("databaseConnection.properties"); // Se ubica el archivo properties
            properties.load(stream); // Se lee la informacion que hay en el archivo
            // se traen los datos de la databaseConnection.properties
            String hostname = properties.getProperty("hostname"); 
            String port = properties.getProperty("port");
            String database = properties.getProperty("database");
            url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
        } catch (IOException ex) {
            System.out.println("Error, " + ex);
        }
    }
}
