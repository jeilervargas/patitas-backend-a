package pe.edu.cibertec.patitas_backend_a.service;

import pe.edu.cibertec.patitas_backend_a.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend_a.dto.SignOutRequestDTO;

import java.io.IOException;

public interface AutenticacionService {

    String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException;

    String[] CierreSesion(SignOutRequestDTO logoutRequestDTO) throws IOException;
}
