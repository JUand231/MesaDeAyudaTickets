package repositorio.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static final Properties PROPIEDADES = cargarPropiedades();

    static {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "No se encontro el driver de SQL Server en el classpath: " + e.getMessage());
        }
    }

    private ConexionDB() {

    }

    public static Connection obtener() throws SQLException {
        return DriverManager.getConnection(
                PROPIEDADES.getProperty("db.url"),
                PROPIEDADES.getProperty("db.user"),
                PROPIEDADES.getProperty("db.password")
        );
    }

    private static Properties cargarPropiedades() {
        Properties props = new Properties();

        try (InputStream in = ConexionDB.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in == null) {
                throw new IllegalStateException(
                        "No se encontro db.properties en src/main/resources. "
                        + "Copia db.properties.example y pon tus credenciales."
                );
            }

            props.load(in);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error leyendo db.properties", e
            );
        }

        return props;
    }
}
