// permitir inscrição e cancelamento de participantes --FEITO (ou não)
// evitar conflitos de horários
// filtrar eventos
// listar eventos

package service;

import java.util.ArrayList;
import java.util.List;
import model.Evento;
import model.Usuario;

public class EventoService {

    private List<Usuario> listaUsuarios = new ArrayList<>(); // Lista com os usuários

    public List<Evento> listarEventosDoUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Unimplemented method 'listarEventosDoUsuario'");
    }

    public String inscreverUsuario(Usuario novoUsuario) {// Método para adicionar usuarios a uma lista
        for (Usuario u : listaUsuarios) {
            if (u.getId() == novoUsuario.getId()) {
                return "Esse usuário já está cadastrado.";
            }
        }

        listaUsuarios.add(novoUsuario);
        return "Usuário inscrito com sucesso!";
    }

    public String desinscreverUsuario(int idUsuario) {// Método para remover usuarios de uma lista
        for (Usuario u : listaUsuarios) {
            if (u.getId() == idUsuario) {
                listaUsuarios.remove(u);
                return "Usuário removido com sucesso.";
            }
        }
        return "Usuário não encontrado.";
    }

    public List<Usuario> listarUsuarios() {// Metodo para listar os usuarios
        return listaUsuarios;
    }
}
