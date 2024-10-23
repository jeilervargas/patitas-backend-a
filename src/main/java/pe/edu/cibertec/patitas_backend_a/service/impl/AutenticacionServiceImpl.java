package pe.edu.cibertec.patitas_backend_a.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_backend_a.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend_a.dto.SignOutRequestDTO;
import pe.edu.cibertec.patitas_backend_a.service.AutenticacionService;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {

        String[] datosUsuario = null;
        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {

            String linea;
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");
                if (loginRequestDTO.tipoDocumento().equals(datos[0]) &&
                    loginRequestDTO.numeroDocumento().equals(datos[1]) &&
                    loginRequestDTO.password().equals(datos[2])) {

                    datosUsuario = new String[4];
                    datosUsuario[0] = datos[3]; // Recuperar nombre
                    datosUsuario[1] = datos[4]; // Recuperar correo
                    datosUsuario[2] = datos[0];
                    datosUsuario[3] = datos[1];
                    break;

                }

            }

        } catch (IOException e) {

            datosUsuario = null;
            throw new IOException(e);

        }

        return datosUsuario;
    }

    @Override
    public String[] CierreSesion(SignOutRequestDTO logoutRequestDTO) throws IOException {

        String[] datosUsuario = new String[3];
        datosUsuario[0] = logoutRequestDTO.tipoDocumento();
        datosUsuario[1] = logoutRequestDTO.numeroDocumento();
        datosUsuario[2] = LocalDateTime.now().toString();
        File archivo = new File("src/main/resources/cierreSesion.txt");
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(archivo, true))) {
            String linea = logoutRequestDTO.tipoDocumento() + ";" +
                    logoutRequestDTO.numeroDocumento() + ";" +
                    LocalDateTime.now().toString() + "\n";

            bf.write(linea);

        } catch (IOException e) {
            datosUsuario = null;
            throw new IOException("Error al guardar registro", e);
        }

        return datosUsuario;
    }

}
