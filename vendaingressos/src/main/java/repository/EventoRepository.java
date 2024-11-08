package repository;

import models.Evento;
import repository.abstrata.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoRepository extends Repository<Evento> {

    public static final String diretorio = "dataBase/eventos/";

    public EventoRepository() {
        super(diretorio, Evento.class);
    }

    @Override
    public String getDiretorioEspecifico(Evento evento) {
        return diretorio + evento.getData() + "/";
    }


    @Override
    public String getId(Evento entidade) {
        return entidade.getId();
    }


    public List<Evento> getEventosDisponiveis(LocalDate dataEvento) {

        List<Evento> eventosDisponiveis = new ArrayList<>();

        File diretorioEventos = new File(diretorio);

        if (diretorioEventos.exists() && diretorioEventos.isDirectory()) {
            File[] pastasEventos = diretorioEventos.listFiles(File::isDirectory);

            if (pastasEventos != null) {
                for (File pasta : pastasEventos) {
                    try {

                        LocalDate dataPasta = LocalDate.parse(pasta.getName());

                        // Verifica se a data do evento é maior ou igual à data fornecida
                        if (!dataPasta.isBefore(dataEvento)) {
                          eventosDisponiveis.addAll(carregarEventoDaPasta(pasta));
                        }
                    } catch (Exception e) {
                        System.out.println("Pasta ignorada: " + pasta.getName());
                    }
                }
            }
        }


        return eventosDisponiveis;
    }

    private List<Evento> carregarEventoDaPasta(File pasta) {

        List<Evento> eventosDisponiveis = new ArrayList<>();

        if (pasta != null) {

            File[] arquivos = pasta.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    try (FileReader reader = new FileReader(arquivo)) {

                        Evento entidade = gson.fromJson(reader, Evento.class);

                        eventosDisponiveis.add(entidade);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Retorna a lista de eventos (vazia se nenhum evento foi carregado)
        return eventosDisponiveis;
    }

}
