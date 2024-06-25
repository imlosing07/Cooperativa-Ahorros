package com.codigo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CConexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/CooperativaAhorros";
    private static final String USER = "postgres";
    private static final String PASSWORD = "viza1234";
    private Connection conexion;

    public Connection estableceConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void cierraConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    // Si necesitas obtener nombres de columnas, podrías implementarlo similar a tu método anterior
    public String[] obtenerNombresColumnas(String tabla) {
        String[] columNames = new String[3]; // Ajusta el tamaño según el número de columnas esperadas

        try (Connection conexion = estableceConexion()) {
            String consulta = "SELECT * FROM " + tabla + " LIMIT 1"; // Consulta para obtener solo una fila
            java.sql.Statement statement = conexion.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(consulta);
            java.sql.ResultSetMetaData metaData = resultSet.getMetaData();

            int cantidadColumnas = metaData.getColumnCount();
            System.out.println("Nombres de columnas:");

            for (int i = 1; i <= cantidadColumnas; i++) {
                String nombreColumna = metaData.getColumnName(i);
                columNames[i - 1] = nombreColumna; // Ajusta el índice para empezar desde 0
                System.out.println(nombreColumna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columNames;
    }

    public static void main(String[] args) {
        CConexion conexion = new CConexion();
        conexion.estableceConexion();
        // Ejemplo de uso para obtener nombres de columnas
        String[] nombresColumnas = conexion.obtenerNombresColumnas("moneda");
        for (String nombre : nombresColumnas) {
            System.out.println("Nombre columna: " + nombre);
        }
        conexion.cierraConexion();
    }
}


