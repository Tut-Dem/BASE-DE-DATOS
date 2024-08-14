package com.prototype.maruja.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=MarujaQuito;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "P@ssw0rd";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Controlador no encontrado: " + e.getMessage());
        }
        return con;
    }
}
