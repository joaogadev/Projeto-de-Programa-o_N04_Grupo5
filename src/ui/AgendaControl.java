package ui;
import model.Evento;
import model.Usuario;
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
    private Usuario usuario;


    public AgendaControl(AgendaView agendaView) {
        this.agendaView = agendaView;

        // Inicialização das dependências (Services)
        this.agendaService = new AgendaService();
        this.relatorioService = new RelatorioService();
        this.notificacaoService = new NotificacaoService();
        this.configService = new NotificacaoConfigService();

        listeners();
        // Inicializa a View
        incializaAgendaView();
    }
    // contrutor para usuário logado
    public AgendaControl(AgendaView agendaView, Usuario usuario) {
        this.usuario = usuario;
        this.agendaView = agendaView;
        // Inicialização das dependências (Services)
        this.agendaService = new AgendaService();
        this.relatorioService = new RelatorioService();
        this.notificacaoService = new NotificacaoService();
        this.configService = new NotificacaoConfigService();

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
        agendaView.getConfigNotificacoesButton().addActionListener(e -> configNotificacoesClicado());
    }


    // Inicializa o estado inicial da View.
    private void incializaAgendaView() {
        atualizarEventos();
        atualizarCoresDias();
        agendaView.setVisible(true);

        Integer userid = getUsuarioId();
        String notificacao;

        if(userid == null){
            return;
        }else {
            // Exibir notificação automática ao abrir o sistema
            if (configService.estadoNotificacao()) {
                String msg = notificacaoService.gerarNotificacao(usuario.getId());
                if (!msg.contains("Nenhum evento")) {
                    agendaView.exibirMensagem(msg, "Notificação Automática", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    //Busca os eventos para a data selecionada e atualiza a área de texto da View.
    private void atualizarEventos() {
        Date dataSelecionada = agendaView.getCalendar().getDate();
        LocalDate localDate = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer userid = getUsuarioId();

        if(userid == null) {
            // Busca os eventos
            List<Evento> eventos = agendaService.filtrarPorDia(localDate);
            // Atualiza a View
            agendaView.exibirEventos(eventos, localDate);
        }else{
            List<Evento> eventos = agendaService.listarInscricoesUsuario(localDate, usuario.getId());

            // Atualiza a View
            agendaView.exibirEventos(eventos, localDate);
        }
    }


     //Atualiza as cores dos dias no calendário com base na existência de eventos.
     //Atualiza as cores dos dias no calendário com base na existência de eventos.
     private void atualizarCoresDias() {
         Integer userid = getUsuarioId();
         int mes = agendaView.getCalendar().getMonthChooser().getMonth() + 1; // janeiro = 0
         int ano = agendaView.getCalendar().getYearChooser().getYear();
         if (userid == null) {
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
         }else{
             for (int i = 1; i <= 31; i++) {
                 LocalDate data;
                 try {
                     data = LocalDate.of(ano, mes, i);
                 } catch (Exception e) {
                     continue;
                 }

                 List<Evento> eventos = agendaService.listarInscricoesUsuario(data, userid);

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
     }

    // handlers de eventos na view
    private void gerarRelatorio() {
        Integer userid = getUsuarioId();
        String notificacao;

        if(userid == null){
            agendaView.exibirMensagem(
                    "Você precisa estar logado para ver o relatório.",
                    "Usuário não logado",
                    JOptionPane.WARNING_MESSAGE
            );
        }else {
            try {

                String caminho = relatorioService.gerarRelatorioSemanal(usuario.getId());

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
    }

    private void onNotificacoesClicked() {
        Integer userid = getUsuarioId();
        String notificacao;

        if(userid != null){
            // usuario logado
            notificacao = notificacaoService.gerarNotificacao(usuario.getId());
        }else{
            agendaView.exibirMensagem(
                    "Você precisa estar logado para ver notificações.",
                    "Usuário não logado",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void configNotificacoesClicado() {
        Integer user_id = getUsuarioId();

        if(user_id == null){
            agendaView.exibirMensagem(
                    "Você precisa estar logado para ver notificações.",
                    "Usuário não logado",
                    JOptionPane.WARNING_MESSAGE
            );
        }else {
            // Pergunta ao usuário
            int opcao = agendaView.exibirConfirmacao(
                    "Deseja receber notificações automáticas ao abrir o sistema?",
                    "Configurar Notificações"
            );

            boolean ativado = (opcao == JOptionPane.YES_OPTION);

            configService.setNotificacaoAtivada(ativado);

            if (ativado) {
                String mensagem = notificacaoService.gerarNotificacao(usuario.getId());

                if (!mensagem.contains("Sem eventos")) {
                    agendaView.exibirMensagem(mensagem, "Notificação imediata (24h)", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            String mensagem = ativado ?
                    "Notificações automáticas ativadas!" :
                    "Notificações automáticas desativadas.";
            agendaView.exibirMensagem(mensagem, "Configuração", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // metodo auxiliar
    private Integer getUsuarioId(){
        return usuario != null ? usuario.getId() : null; // se usuário for diferente de nullo retorne o id, se não retorne nulo
    }
}