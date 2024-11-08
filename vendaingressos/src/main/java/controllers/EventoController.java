package controllers;
import models.*;
import  repository.EventoRepository;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private final  EventoRepository eventoRepository;


    /**
     *
     * @param eventoRepository
     */
    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    /***
     *
     * @param quantidadeIngressos
     * @param evento
     * @return
     * @throws FileNotFoundException
     */
    public List<Ingresso> gerarIngressos(int quantidadeIngressos, Evento evento) throws FileNotFoundException {

        List<Ingresso> ingressos = new ArrayList<>();
        if (quantidadeIngressos == 0 || evento.getCapacidadeDisponivel() == 0) {
            throw new IllegalArgumentException("O evento esta cheio.");
        }

        for (int i = 1; i <= quantidadeIngressos; i++) {
            ingressos.add(new Ingresso(evento, evento.calcularValorIngresso()));
            evento.ocuparEspaco();
        }
        eventoRepository.editarEntidade(evento);
        return ingressos;
    }

    /***
     *
     * @return
     */
    public List<Evento> listarEventosDisponiveis() {
        LocalDate dataDisponivel = LocalDate.now();
        return eventoRepository.getEventosDisponiveis(dataDisponivel);  // Retorna a lista completa
    }


    /**
     *
     * @param avaliacao
     * @param evento
     * @throws FileNotFoundException
     */
    protected void receberComentariosEvento(Avaliacao avaliacao, Evento evento) throws FileNotFoundException {
        List<Avaliacao> avaliacaos = new ArrayList<>();
        avaliacaos.add(avaliacao);
        evento.setAvalicoes(avaliacaos);
        eventoRepository.editarEntidade(evento);
    }
}
