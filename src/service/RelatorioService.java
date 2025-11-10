package service;
import repository.MyJDBC;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class RelatorioService {
    // adicionar mensagem para o caso de não tenham eventos que o usuário participou

    public RelatorioService(){}

    public String gerarRelatorioSemanal(int usuarioId) {
        String sql = """
            SELECT e.nome, e.descricao, e.dataInicio, e.dataFim, e.local, e.categoria
            FROM HistoricoEventos h
            JOIN Evento e ON h.Evento_id = e.Evento_id
            WHERE h.Usuario_id = ? 
              AND h.status = 1
              AND e.dataInicio >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
            ORDER BY e.dataInicio DESC;
        """;
        String nomeArquivo = "relatorios/RelatorioEventos_" + usuarioId + ".txt";

        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {



                try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(nomeArquivo))) {

                    arquivo.write("Relatório Semanal \n");
                    arquivo.write("Usuário ID: " + usuarioId + "\n");
                    arquivo.write("Período: Últimos 7 dias\n");
                    arquivo.write("------------------------------------------------\n\n");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    boolean eventos = false;

                    while (rs.next()) { // percorrer as linhas da tabela sql
                        eventos = true;
                        String nome = rs.getString("nome");
                        String descricao = rs.getString("descricao");
                        Timestamp inicio = rs.getTimestamp("dataInicio");
                        Timestamp fim = rs.getTimestamp("dataFim");
                        String local = rs.getString("local");
                        String categoria = rs.getString("categoria");

                        arquivo.write("Evento: " + nome + "\n");
                        arquivo.write("Descrição: " + descricao + "\n");
                        arquivo.write("Início: " + inicio.toLocalDateTime().format(formatter) + "\n");
                        arquivo.write("Fim: " + fim.toLocalDateTime().format(formatter) + "\n");
                        arquivo.write("Local: " + local + "\n");
                        arquivo.write("Categoria: " + categoria + "\n");
                        arquivo.write("-----------------------------------------------\n");
                    }

                    if (!eventos) {
                        arquivo.write("Nenhum evento encontrado para este usuário na última semana.\n");
                    }

                    System.out.println("Relatório gerado: " + nomeArquivo);
                } catch (IOException e) {
                    System.err.println("Problemas ao gerar relatório: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return nomeArquivo;
    }
}
