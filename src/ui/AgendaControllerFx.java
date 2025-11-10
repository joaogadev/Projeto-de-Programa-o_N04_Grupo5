package ui;

import com.toedter.calendar.JCalendar;
import service.AgendaService;
import model.Evento;
import service.RelatorioService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AgendaControllerFx {
    private JFrame frame;
    private JCalendar calendar;
    private JTextArea eventosTextArea;
    private AgendaService agendaService;


    public AgendaControllerFx() {
        agendaService = new AgendaService();
        RelatorioService relatorioService = new RelatorioService();

        frame = new JFrame("Agenda de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        calendar = new JCalendar();
        frame.add(calendar, BorderLayout.NORTH);



        // Painel superior real do JCalendar (contém mês, ano e setas)
        JPanel topPanel = (JPanel) calendar.getComponent(0);

        // Troca o layout para permitir inserir botão à direita
        topPanel.setLayout(new BorderLayout());

        // Painel original contendo mês e ano (fica na esquerda)
        JPanel monthYearPanel = (JPanel) topPanel.getComponent(0);

        // Painel da direita com o botão
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton relatorioButton = new JButton("Gerar Relatório");
        relatorioButton.setFont(new Font("Arial", Font.BOLD, 14));

        rightPanel.add(relatorioButton);

        // Remonta o topPanel agora dividido
        topPanel.removeAll();
        topPanel.add(monthYearPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        topPanel.revalidate();
        topPanel.repaint();

        // Ação do botão
        relatorioButton.addActionListener(e -> {
            try {
                int usuarioId = 1; // ID real do usuário

                // Gera o relatório e recebe o caminho do arquivo txt
                String caminho = relatorioService.gerarRelatorioSemanal(usuarioId);

                // Lê o conteúdo do arquivo
                String conteudo = new String(java.nio.file.Files.readAllBytes(
                        java.nio.file.Paths.get(caminho)
                ));

                // Exibir o conteúdo em uma janela
                JTextArea txt = new JTextArea(conteudo);
                txt.setEditable(false);
                txt.setFont(new Font("Monospaced", Font.PLAIN, 14));

                JScrollPane scroll = new JScrollPane(txt);

                JDialog dialog = new JDialog(frame, "Relatório Semanal", true);
                dialog.setSize(600, 500);
                dialog.setLocationRelativeTo(frame);
                dialog.add(scroll);
                dialog.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Erro ao gerar relatório: " + ex.getMessage());
            }
        });



        // Para ajustar o tamanho dos botões e fontes do mês/ano
        ((JComboBox<?>) calendar.getMonthChooser().getComboBox())
                .setFont(new Font("Arial", Font.BOLD, 16));
        ((JSpinner) calendar.getYearChooser().getSpinner())
                .setFont(new Font("Arial", Font.BOLD, 16));

        // Para aumentar o tamanho dos botões
        Arrays.stream(calendar.getDayChooser().getDayPanel().getComponents())
                .filter(c -> c instanceof JButton)
                .map(c -> (JButton) c)
                .forEach(btn -> {
                    btn.setFont(new Font("Arial", Font.BOLD, 14));
                    btn.setPreferredSize(new Dimension(45, 45));
                });

        eventosTextArea = new JTextArea();
        eventosTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventosTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Atualiza eventos quando a data do calendário mudar
        calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
            atualizarEventos();
            atualizarCoresDias();
        });

        // Atualiza cores quando mudar mês ou ano
        ((JComboBox<?>) calendar.getMonthChooser().getComboBox())
                .addActionListener(e -> atualizarCoresDias());
        ((JSpinner) calendar.getYearChooser().getSpinner())
                .addChangeListener(e -> atualizarCoresDias());

        // Mostra eventos do dia inicial e cores
        atualizarEventos();
        atualizarCoresDias();

        frame.setVisible(true);
    }

    private void atualizarEventos() {
        Date dataSelecionada = calendar.getDate();
        LocalDate localDate = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Evento> eventos = agendaService.filtrarPorDia(localDate);

        eventosTextArea.setText(""); // limpa área de texto
        if (eventos.isEmpty()) {
            eventosTextArea.append("Nenhum evento para " + localDate + "\n");
        } else {
            for (Evento e : eventos) {
                eventosTextArea.append(e.getNome() + " - " + e.getDescricao() + " (" +
                        e.getDataInicio() + " a " + e.getDataFim() + ")\n");
            }
        }
    }

    private void atualizarCoresDias() {
        JButton[] days = Arrays.stream(calendar.getDayChooser().getDayPanel().getComponents())
                .filter(c -> c instanceof JButton)
                .map(c -> (JButton) c)
                .toArray(JButton[]::new);

        // Limpa cores
        for (JButton btn : days) {
            btn.setBackground(Color.WHITE);
            btn.setOpaque(true);
        }

        int mes = calendar.getMonthChooser().getMonth() + 1; // janeiro = 0
        int ano = calendar.getYearChooser().getYear();

        for (int i = 1; i <= 31; i++) {
            LocalDate data;
            try {
                data = LocalDate.of(ano, mes, i);
            } catch (Exception e) {
                continue;
            }

            List<Evento> eventos = agendaService.filtrarPorDia(data);

            Color cor = Color.WHITE; // padrão
            for (Evento ev : eventos) {
                if (ev.getStatus().equalsIgnoreCase("cheio")) {
                    cor = Color.RED;
                    break; // se algum evento estiver cheio fica vermelho
                } else {
                    cor = Color.YELLOW; // tem evento com vagas
                }
            }

            for (JButton btn : days) {
                if (btn.getText().equals(String.valueOf(i))) {
                    btn.setBackground(cor);
                    btn.setOpaque(true);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgendaControllerFx::new);
    }
}
