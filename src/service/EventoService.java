// evitar conflitos de horários
// filtrar eventos
// listar eventos

package service;

import java.util.ArrayList;
import java.util.List;
import model.Evento;
import model.Usuario;

public class EventoService {

    ArrayList<Evento> listaEventos = new ArrayList<>();

    public void filtrarEvento(){

    }

    public void listarEvento(){ // loop para exibir os dados de uma listagem de eventos
        for(Evento e : listaEventos){
            e.exibirEvento();
        }
    }

    public Evento checarConflito() throws Exception{ //Método para checar conflito entre horarios

        /*if(horarioInicio do evento x == horarioInicio do evento y){
            throw new Exception("Evento já agendado.");
        }
        else{
            System.out.println("Horario disponivel");
        }*/
    }
}
