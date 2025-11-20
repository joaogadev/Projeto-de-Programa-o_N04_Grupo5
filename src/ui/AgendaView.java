package ui;
import com.toedter.calendar.JCalendar;
import model.Evento;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

// ui e captura eventos do usuário sem lógica de botões
public class AgendaView extends JFrame{
    private final JCalendar calendar;
    private final JTextArea eventosTextArea; // exibição de txt
    private final JButton relatorioButton;
    private final JButton configNotificacoesButton;

    public AgendaView(){
        super("Agenda de Eventos");

        // config da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.setLayout(new BorderLayout());

        //componentes
        this.calendar = new JCalendar();
        this.eventosTextArea = new JTextArea();
        this.relatorioButton = new JButton("Gerar Relatório");
        //this.notificacoesButton = new JButton("Notificações");
        this.configNotificacoesButton = new JButton("Ativar Notificações");

        // montar ui
        setupCalendarPanel();
        setupEventosPanel();

        // estilo
        aplicarEstilos();
    }

    public void setupCalendarPanel(){
        this.add(calendar, BorderLayout.NORTH); // calendário no topo
        // botões do topo
        JPanel topPanel = (JPanel) calendar.getComponent(0);
        topPanel.setLayout(new BorderLayout());

        JPanel monthYearPanel = (JPanel) topPanel.getComponent(0);
        // Painel da direita com o botão
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        rightPanel.add(relatorioButton);
        rightPanel.add(configNotificacoesButton);

        // Remonta o topPanel agora dividido
        topPanel.removeAll();
        topPanel.add(monthYearPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        topPanel.revalidate();
        topPanel.repaint();
    }

    public void setupEventosPanel(){
        eventosTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventosTextArea);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void aplicarEstilos(){
        Font fonteBotoes = new Font("Arial", Font.BOLD, 14);

        relatorioButton.setFont(fonteBotoes);
        //notificacoesButton.setFont(fonteBotoes);
        configNotificacoesButton.setFont(fonteBotoes);

        // estilo principal do calendário
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

    }
    public void exibirEventos(List<Evento> eventos, LocalDate data){
        eventosTextArea.setText(""); // limpa área de texto
        if (eventos.isEmpty()) {
            eventosTextArea.append("Nenhum evento para " + data + "\n");
        } else {
            for (Evento e : eventos) {
                eventosTextArea.append(e.getNome() + " - " + e.getDescricao() + " (" +
                        e.getDataInicio() + " a " + e.getDataFim() + ")\n");
            }
        }
    }
    public void colorirDias(int dia, Color cor){
        Arrays.stream(calendar.getDayChooser().getDayPanel().getComponents())
                .filter(c -> c instanceof JButton)
                .map(c -> (JButton) c)
                .filter(btn -> btn.getText().equals(String.valueOf(dia)))
                .findFirst()
                .ifPresent(btn -> {
                    btn.setBackground(cor);
                    btn.setOpaque(true);
                });
    }
    // dialogo de confirmação
    public void exibirMensagem(String mensagem, String titulo, int tipo){
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    // confirmação para notificações
    public int exibirConfirmacao(String mensagem, String titulo){
        return JOptionPane.showConfirmDialog(this, mensagem, titulo, JOptionPane.YES_NO_OPTION);
    }
    public void exibirRelatorio(String conteudo){

        // Exibição do txt
        JTextArea txt = new JTextArea(conteudo);
        txt.setEditable(false);
        txt.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(txt);

        JDialog dialog = new JDialog(this, "Relatório Semanal", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        dialog.add(scroll);
        dialog.setVisible(true);
    }

    // getters que serão usados no AgendaController
    public JCalendar getCalendar() {
        return calendar;
    }

    public JTextArea getEventosTextArea() {
        return eventosTextArea;
    }

    public JButton getRelatorioButton() {
        return relatorioButton;
    }

    public JButton getConfigNotificacoesButton() {
        return configNotificacoesButton;
    }
}
