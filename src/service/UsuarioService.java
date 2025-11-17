package service;

import model.Usuario;
import repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {
    UsuarioRepository usuarioRepository;

    public UsuarioService(){
        this.usuarioRepository = new UsuarioRepository();
    }
    public List<Usuario> listarTodos(){
        return usuarioRepository.listarTodos();
    }
}
