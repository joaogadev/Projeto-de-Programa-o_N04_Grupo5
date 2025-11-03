package repository;
import model.Permissao;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {

    /* Cadastra um novo usuario no banco */
    public void salvar(Usuario usuario) throws SQLException {
        String sqlUsuario = "INSERT INTO Usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

            psUsuario.setString(1, usuario.getNome());
            psUsuario.setString(2, usuario.getEmail());
            psUsuario.setString(3, usuario.getSenha());
            psUsuario.executeUpdate();

            ResultSet rs = psUsuario.getGeneratedKeys();
            if (rs.next()) {
                int usuarioId = rs.getInt(1);
                usuario.setId(usuarioId);

                if (usuario.getPermissao() == Permissao.ADMIN) {
                    String sqlAdmin = "INSERT INTO Admin (Usuario_id, nome, email) VALUES (?, ?, ?)";
                    try (PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin)) {
                        psAdmin.setInt(1, usuarioId);
                        psAdmin.setString(2, usuario.getNome());
                        psAdmin.setString(3, usuario.getEmail());
                        psAdmin.executeUpdate();
                    }
                } else if (usuario.getPermissao() == Permissao.VISUALIZADOR) {
                    String sqlVis = "INSERT INTO Visualizador (Usuario_id) VALUES (?)";
                    try (PreparedStatement psVis = conn.prepareStatement(sqlVis)) {
                        psVis.setInt(1, usuarioId);
                        psVis.executeUpdate();
                    }
                }
            }
        }
    }

    /* Busca um usuario por email */
    public Optional<Usuario> buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE email = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuario = mapearUsuario(rs, conn);
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    /* Lista todos os usuarios */
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs, conn));
            }
        }
        return usuarios;
    }

    /* Atualiza informações de um usuario */
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ? WHERE Usuario_id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getId());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado.");
            }
        }
    }

    /* Remove um usuario */
    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE Usuario_id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário removido com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado.");
            }
        }
    }

    /* Mapeia o resultado para um objeto Usuario */
    private Usuario mapearUsuario(ResultSet rs, Connection conn) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("Usuario_id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));

        // Descobre o tipo de usuário (ADMIN ou VISUALIZADOR)
        int usuarioId = usuario.getId();

        /* verifica se é  admin */
        try (PreparedStatement psAdmin = conn.prepareStatement("SELECT 1 FROM Admin WHERE Usuario_id = ?")) {
            psAdmin.setInt(1, usuarioId);
            ResultSet rsAdmin = psAdmin.executeQuery();
            if (rsAdmin.next()) {
                usuario.setPermissao(Permissao.ADMIN);
                return usuario;
            }
        }

        /* verifica se é visualizador */
        try (PreparedStatement psVis = conn.prepareStatement("SELECT 1 FROM Visualizador WHERE Usuario_id = ?")) {
            psVis.setInt(1, usuarioId);
            ResultSet rsVis = psVis.executeQuery();
            if (rsVis.next()) {
                usuario.setPermissao(Permissao.VISUALIZADOR);
                return usuario;
            }
        }

        // Caso não pertença a nenhuma (usuário comum)
        usuario.setPermissao(Permissao.USUARIO);
        return usuario;
    }
}
