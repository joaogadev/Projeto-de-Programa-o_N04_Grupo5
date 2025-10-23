package repository;

import model.Permissao;
import model.Usuario;
import repository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
* Classe responsavel por realizar operações com o banco relacionado ao usuario
* */
public class UsuarioRepository {
    /*Cadatra um novo usuario no banco*/
    public void salvar(Usuario usuario) throws SQLException{
        String sql = "INSERT INTO usuario (nome, email, senha, permissao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setObject(4, usuario.getPermissao());
            ps.executeUpdate();
        }
    }
    /*Busca um usuario por email
    * Retorna Optional<Usuario> pra evitar nullpointerexception*/
    public Optional<Usuario> buscarPorEmail(String email) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Usuario u = mapearUsuario(rs);
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
    /*lista todos os usuario no banco*/
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuario = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                usuario.add(mapearUsuario(rs));
            }
        }
        return usuario;
    }
    /*atualiza informações de um usuario*/
    public void atualizar(Usuario usuario) throws SQLException{
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setObject(4, usuario.getId());
            int linhas = ps.executeUpdate();

            if(linhas > 0){System.out.println("Atualizado");
            } else {System.out.println("Nenhum usuario encontrado");}
        }
    }
    /*remove um usuario pelo seu id*/
    public void remover(int id) throws SQLException{
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            if(linhas > 0){System.out.println("Removido com sucesso");
            } else {System.out.println("Nenhum usuario encontrado");}

        }
    }
    /*Mapeia o resultado do banco para um objeto usuario */
    public Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPermissao((Permissao) rs.getObject("tipo")); // admin, usuario ou visualizador
        return usuario;
    }
}
