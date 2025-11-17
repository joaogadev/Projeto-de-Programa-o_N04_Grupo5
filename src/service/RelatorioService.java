package service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Evento;
import repository.RelatorioRepository;

public class RelatorioService {
    private RelatorioRepository relatorioRepository;

    public RelatorioService(){
        this.relatorioRepository = new RelatorioRepository();
    }

    public String gerarRelatorioSemanal(int usuarioId) {

        List<Evento> eventosVisitados = relatorioRepository.eventosRelatorio(usuarioId);
        String nomeArquivo = "relatorios/RelatorioEventos_" + usuarioId + ".txt";

                try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(nomeArquivo))) {

                    if (eventosVisitados == null) {
                        arquivo.write("Nenhum evento encontrado para este usuário na última semana.\n");
                        return nomeArquivo;
                    }
                    arquivo.write("Relatório Semanal \n");
                    arquivo.write("Usuário ID: " + usuarioId + "\n");
                    arquivo.write("Período: Últimos 7 dias\n");
                    arquivo.write("------------------------------------------------\n\n");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                        for (Evento e : eventosVisitados) {

                            String nome = e.getNome();
                            String descricao = e.getDescricao();
                            LocalDateTime inicio = e.getDataInicio();
                            LocalDateTime fim = e.getDataFim();
                            String local = e.getLocal();
                            String categoria = e.getCategoria();

                            arquivo.write("Evento: " + nome + "\n");
                            arquivo.write("Descrição: " + descricao + "\n");
                            arquivo.write("Início: " + inicio + "\n");
                            arquivo.write("Fim: " + fim + "\n");
                            arquivo.write("Local: " + local + "\n");
                            arquivo.write("Categoria: " + categoria + "\n");
                            arquivo.write("-----------------------------------------------\n");
                        }

                    System.out.println("Relatório gerado: " + nomeArquivo);
                } catch (IOException e) {
                    System.err.println("Problemas ao gerar relatório: " + e.getMessage());
                }
        return nomeArquivo;
    }
}
