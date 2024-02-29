package com.javier.estudy.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    
    private static DatabaseConnection instance = null; // Para generar el patron Singleton
    
    private Connection conn; // instancia que permite conexion a la base de datos
    private Properties properties = new Properties(); // Clase que permite traer las propiedades de la databaseConnection.properties
    private InputStream stream; // Clase que permite leer los datos del archivo que se indique
    
    private String url;
    
    /* Para implementar correctamente el patrón Singleton en una clase, se debe hacer el constructor de la clase privado para prevenir la creación de nuevas instancias utilizando el operador `new`. 
       Luego, se crea un método estático `getInstance()` que cree y devuelva la única instancia permitida de la clase si aún no existe, y simplemente devuelva la instancia existente en lugar de crear una nueva si ya ha sido creada. */
    private DatabaseConnection() {
    }
    
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
    
    /**
     * @return la única instacia de la conexión, crea una nueva si no está creada o devuelve la ya creada
     */
    public static DatabaseConnection getInstance(){
        if (instance == null) {
            return instance = new DatabaseConnection();
        } else {
            return instance;
        }
    }
}
