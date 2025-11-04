package ui;

import com.toedter.calendar.JCalendar;
import service.AgendaService;
import model.Evento;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
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

        frame = new JFrame("Agenda de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        calendar = new JCalendar();
        frame.add(calendar, BorderLayout.NORTH);

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
}
