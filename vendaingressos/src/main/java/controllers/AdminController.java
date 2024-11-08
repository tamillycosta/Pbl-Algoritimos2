package controllers;

import models.Adimin;
import models.Evento;
import repository.EventoRepository;
import repository.AdminRepository;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class AdminController {

    private EventoRepository eventoRepository;
    private AdminRepository adminRepository;

    /***
     *
     * @param eventoRepository
     * @param adminRepository
     */
    public AdminController(EventoRepository eventoRepository, AdminRepository adminRepository) {
        this.eventoRepository = eventoRepository;
        this.adminRepository = adminRepository;
    }


    /**
     * Cadastra um novo administrador no sistema.
     * Verifica se o e-mail é válido e se já existe um administrador com o mesmo CPF ou login no repositório.
     * Se essas condições forem satisfeitas, o novo administrador é salvo no repositório.
     *
     * @param login o login do novo administrador
     * @param senha a senha do novo administrador
     * @param cpf o CPF do novo administrador
     * @param email o e-mail do novo administrador
     * @return o objeto {@link Adimin} recém-criado
     * @throws IllegalArgumentException se o e-mail for inválido ou se já houver um administrador com o mesmo CPF ou login
     */
    public Adimin cadastrarAdmim(String login, String senha, String cpf, String email){
        Adimin adimin = new Adimin(login,senha,cpf,email);
       if(!Adimin.isEmailValido(email)|| adminRepository.buscarPorId(adimin) != null){
           throw new IllegalArgumentException("Já existe um administrador com esse CPF ou login.");
       }
        adminRepository.salvar(adimin);
       return adimin;
    }


    /**
     * Cadastra um novo evento no sistema.
     * Verifica se o administrador está identificado, se a data do evento não é no passado e se a capacidade do evento é válida.
     * Se todas as condições forem satisfeitas, o evento é salvo no repositório.
     *
     * @param adimin o objeto {@link Adimin} que está cadastrando o evento
     * @param nomeEvento o nome do evento a ser cadastrado
     * @param descricaoEvento a descrição do evento a ser cadastrado
     * @param data a data do evento
     * @param capacidadeEvento a capacidade máxima do evento
     * @return o objeto {@link Evento} recém-criado
     * @throws IllegalArgumentException se o administrador não for encontrado, a data do evento for no passado ou a capacidade do evento for menor ou igual a zero
     */
    public Evento cadastrarEvento(Adimin adimin,String nomeEvento, String descricaoEvento, LocalDate data, int capacidadeEvento) {
        if (adminRepository.buscarPorId(adimin) == null){
            throw new IllegalArgumentException("Administrador não identificado.");
        }

        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data do evento não pode ser no passado.");
        }

        if (capacidadeEvento <= 0) {
            throw new IllegalArgumentException("A capacidade do evento deve ser maior que zero.");
        }
        Evento evento = new Evento(nomeEvento, descricaoEvento, data, capacidadeEvento);
        eventoRepository.salvar(evento);
        return evento;
    }


    /**
     * Desativa um evento existente no sistema.
     * Verifica se o evento existe no repositório e se já está ativo.
     * Se todas as condições forem satisfeitas, o evento é desativado.
     *
     * @param evento o objeto {@link Evento} a ser desativado
     * @return o objeto {@link Evento} desativado
     * @throws FileNotFoundException se o evento não for encontrado no repositório
     * @throws IllegalStateException se o evento já estiver desativado
     */
    public Evento desativarEvento(Evento evento) throws FileNotFoundException {
        Evento eventoEncontrado = eventoRepository.buscarPorId(evento);

        if (eventoEncontrado == null) {
            throw new IllegalStateException("O evento com ID  não foi encontrado.");
        }

        if (!eventoEncontrado.isAtivo()) {
            throw new IllegalStateException("O evento já está desativado.");
        }

        eventoEncontrado.setAtivo(false);
        eventoRepository.editarEntidade(eventoEncontrado);
        return  eventoEncontrado;
    }
}

