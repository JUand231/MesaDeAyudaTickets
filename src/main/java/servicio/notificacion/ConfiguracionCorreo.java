package servicio.notificacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfiguracionCorreo {

    private final Properties propiedades;

    public ConfiguracionCorreo() {
        propiedades = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("mail.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "No se encontro el archivo mail.properties"
                );
            }

            propiedades.load(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo cargar mail.properties",
                    e
            );
        }
    }

    public String getUsername() {
        return propiedades.getProperty("mail.username");
    }

    public String getPassword() {
        return propiedades.getProperty("mail.password");
    }

    public String getSmtpHost() {
        return propiedades.getProperty("mail.smtp.host");
    }

    public String getSmtpPort() {
        return propiedades.getProperty("mail.smtp.port");
    }
}
