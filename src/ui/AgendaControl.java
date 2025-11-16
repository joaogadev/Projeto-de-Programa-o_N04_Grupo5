package ui;
import model.Evento;
import service.AgendaService;
import service.NotificacaoConfigService;
import service.NotificacaoService;
import service.RelatorioService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AgendaControl {
    private final AgendaView agendaView;
    private final AgendaService agendaService;
    private final RelatorioService relatorioService;
    private final NotificacaoService notificacaoService;
    private final NotificacaoConfigService configService;
    private final int usuarioId = 6; // ID do usuário logado

    public AgendaControl(AgendaView agendaView) {
        this.agendaView = agendaView;

        // Inicialização das dependências (Services)
        this.agendaService = new AgendaService();
        this.relatorioService = new RelatorioService();
        this.notificacaoService = new NotificacaoService();
        this.configService = new NotificacaoConfigService();

        // Configura os listeners da View
        listeners();

        // Inicializa a View
        incializaAgendaView();
    }
    // botões aqui
    private void listeners() {
        // Mudança de dia
        agendaView.getCalendar().getDayChooser().addPropertyChangeListener("day", evt -> {
            atualizarEventos();
            atualizarCoresDias();
        });

        // Listener do Calendário (mudança de mês/ano)
        ((JComboBox<?>) agendaView.getCalendar().getMonthChooser().getComboBox()).addActionListener(e -> atualizarCoresDias());
        ((JSpinner) agendaView.getCalendar().getYearChooser().getSpinner()).addChangeListener(e -> atualizarCoresDias());

        //agendaView.getCalendar().getYearChooser().addPropertyChangeListener("year", evt -> atualizarCoresDias());

        // Listeners dos Botões
        agendaView.getRelatorioButton().addActionListener(e -> gerarRelatorio());
        agendaView.getNotificacoesButton().addActionListener(e -> onNotificacoesClicked());
        agendaView.getConfigNotificacoesButton().addActionListener(e -> configNotificacoesClicado());
    }


    // Inicializa o estado inicial da View.
    private void incializaAgendaView() {
        atualizarEventos();
        atualizarCoresDias();
        agendaView.setVisible(true);
        /*
        // Exibir notificação automática ao abrir o sistema
        if (configService.estadoNotificacao()) {
            String msg = notificacaoService.gerarNotificacao();
            if (!msg.contains("Nenhum evento")) {
                view.exibirMensagem(msg, "Notificação Automática", JOptionPane.INFORMATION_MESSAGE);
            }
        }*/
    }

    //Busca os eventos para a data selecionada e atualiza a área de texto da View.
    private void atualizarEventos() {
        Date dataSelecionada = agendaView.getCalendar().getDate();
        LocalDate localDate = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Busca os eventos
        List<Evento> eventos = agendaService.filtrarPorDia(localDate);

        // caso tenha um usuário logado use .filtrarPorusuario abaixo
        // List<Evento> eventos = agendaService.filtrarPorUsuario(this.usuarioId);

        // Atualiza a View
        agendaView.exibirEventos(eventos, localDate);
    }


     //Atualiza as cores dos dias no calendário com base na existência de eventos.
    private void atualizarCoresDias() {
        int mes = agendaView.getCalendar().getMonthChooser().getMonth() + 1; // janeiro = 0
        int ano = agendaView.getCalendar().getYearChooser().getYear();

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
                    break;
                } else {
                    cor = Color.YELLOW;
                }
            }

            // Atualiza a cor do dia na View
            agendaView.colorirDias(i, cor);
        }
    }

    // handlers de eventos na view
    private void gerarRelatorio() {
        try {

            String caminho = relatorioService.gerarRelatorioSemanal(this.usuarioId);

            //Apresentação (Leitura do arquivo e exibição)
            String conteudo = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(caminho)
            ));

            // Atualiza a View
            agendaView.exibirRelatorio(conteudo);

        } catch (Exception ex) {
            // Trata o erro e exibe na View
            agendaView.exibirMensagem("Erro ao gerar relatório: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onNotificacoesClicked() {

        String notificacao = notificacaoService.gerarNotificacao(this.usuarioId);

        // Atualiza a View
        agendaView.exibirMensagem(notificacao, "Notificações", JOptionPane.INFORMATION_MESSAGE);
    }

    private void configNotificacoesClicado() {
        // Pergunta ao usuário
        int opcao = agendaView.exibirConfirmacao(
                "Deseja receber notificações automáticas ao abrir o sistema?",
                "Configurar Notificações"
        );

        boolean ativado = (opcao == JOptionPane.YES_OPTION);

        configService.setNotificacaoAtivada(ativado);

        String mensagem = ativado ?
                "Notificações automáticas ativadas!" :
                "Notificações automáticas desativadas.";
        agendaView.exibirMensagem(mensagem, "Configuração", JOptionPane.INFORMATION_MESSAGE);
    }
}
