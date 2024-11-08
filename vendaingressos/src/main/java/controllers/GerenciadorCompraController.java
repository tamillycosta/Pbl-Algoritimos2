package controllers;

import models.Evento;
import models.Ingresso;
import models.NotaFiscal;
import models.UsuarioComum;
import models.interfaces.MetodoPagamento;
import repository.EventoRepository;
import repository.NotaFiscalRepository;
import repository.UserRepository;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

public class GerenciadorCompraController {



    private EventoController eventoController;
    private EventoRepository eventoRepository;
    private UserRepository userRepository;
    private NotaFiscalRepository notaFiscalRepository;

    /***
     *
     * @param eventoController
     * @param eventoRepository
     * @param userRepository
     * @param notaFiscalRepository
     */
    public GerenciadorCompraController(EventoController eventoController, EventoRepository eventoRepository, UserRepository userRepository, NotaFiscalRepository notaFiscalRepository) {
        this.eventoController = eventoController;
        this.eventoRepository = eventoRepository;
        this.userRepository = userRepository;
        this.notaFiscalRepository = notaFiscalRepository;
    }


    /***
     *
     * @param usuario
     * @param evento
     * @param quantidadeIngressos
     * @param metodoPagamento
     * @return
     * @throws FileNotFoundException
     */
    public NotaFiscal comprarIngresso(UsuarioComum usuario, Evento evento, int quantidadeIngressos, MetodoPagamento metodoPagamento) throws FileNotFoundException {

        // Valida se há ingressos disponíveis
        Evento eventoEncontrado = eventoRepository.buscarPorId(evento);
        if (eventoEncontrado == null || eventoEncontrado.getCapacidadeDisponivel() < quantidadeIngressos) {
            throw new IllegalArgumentException("Evento não encontrado ou ingressos esgotados.");
        }

        // Gera ingressos e associa ao usuário
        usuario.setIngressos(eventoController.gerarIngressos(quantidadeIngressos, eventoEncontrado));
        userRepository.editarEntidade(usuario);

        // Confirma o pagamento e gera a nota fiscal
        NotaFiscal notaFiscal = confirmarCompra(usuario, metodoPagamento);

        if (notaFiscal != null) {
            notaFiscalRepository.salvar(notaFiscal);   // Salva a nota fiscal no repositório
        } else {
            throw new IllegalStateException("Erro ao processar o pagamento. Nota fiscal é null.");
        }

        return notaFiscal;
    }


    /***
     *
     * @param usuario
     * @param metodoPagamento
     * @return
     * @throws FileNotFoundException
     */
    private NotaFiscal confirmarCompra(UsuarioComum usuario, MetodoPagamento metodoPagamento) throws FileNotFoundException {
        double valorPagar = calcularValorPagar(usuario);
        metodoPagamento.setValorPagar(valorPagar);

        // Processa o pagamento
        if (metodoPagamento.processarPagamento(valorPagar)) {
            return emitirNota(usuario, metodoPagamento);
        }
        return null;
    }


    /***
     *
     * @param usuario
     * @param metodoPagamento
     * @return
     */
    private NotaFiscal emitirNota(UsuarioComum usuario, MetodoPagamento metodoPagamento) {
        LocalDate dataAtual = LocalDate.now();
        double valorTotal = calcularValorPagar(usuario);
        return new NotaFiscal(valorTotal, dataAtual, usuario.getIngressos(), metodoPagamento, usuario);
    }


    /**
     *
     * @param usuario
     * @return
     */

    private double calcularValorPagar(UsuarioComum usuario) {
        double valorIngresso = usuario.getIngressos().getFirst().getPreco();
        return usuario.getIngressos().size() * valorIngresso;
    }


    /***
     *
     * @param usuario
     * @param ingresso
     * @param notaFiscal
     * @return
     */
    // Verifica se o ingresso pertence ao usuário e está presente na nota fiscal, comparando por ID
    private boolean validarCancelamentoCompra(UsuarioComum usuario, Ingresso ingresso, NotaFiscal notaFiscal) {
        Optional<Ingresso> ingressoUsuario = usuario.getIngressos().stream()
                .filter(i -> i.getId().equals(ingresso.getId()))  // Comparação por ID
                .findFirst();

        Optional<Ingresso> ingressoNota = notaFiscal.getIngressos().stream()
                .filter(i -> i.getId().equals(ingresso.getId()))  // Comparação por ID
                .findFirst();

        return ingressoUsuario.isPresent() && ingressoNota.isPresent();
    }


    /**
     *
     * @param usuario
     * @param ingresso
     * @param notaFiscal
     * @throws FileNotFoundException
     */
    public void cancelarCompra(UsuarioComum usuario, Ingresso ingresso, NotaFiscal notaFiscal) throws FileNotFoundException {
        // Valida se o usuário está registrado
        if (!userRepository.buscarLogin(usuario.getLogin())) {
            return;
        }

        UsuarioComum usuarioEncontrado = userRepository.buscarPorId(usuario);

        if (validarCancelamentoCompra(usuario,ingresso,notaFiscal)) {
            // Cancela o ingresso e atualiza o evento correspondente
            Evento evento = ingresso.getEvento();
            Evento eventoEncontrado = eventoRepository.buscarPorId(evento);
            ingresso.cancelar();
            eventoEncontrado.desocuparAssento();
            eventoRepository.editarEntidade(eventoEncontrado);

            // Remove o ingresso do usuário
            usuarioEncontrado.getIngressos().removeIf(i -> i.getId().equals(ingresso.getId()));  // Remover por ID
            userRepository.editarEntidade(usuarioEncontrado);
        }
    }



}
