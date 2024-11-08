package repository.abstrata;
import GsonAdapter.src.GsonUtil;
import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe abstrata Repository representa uma estrutura genérica de repositório para manipulação de entidades
 * serializadas em arquivos JSON, utilizando a biblioteca Gson.
 *
 * @param <T> O tipo de entidade que o repositório manipula.
 */
public abstract class Repository<T> {


    private String diretorioBase;
    protected Gson gson;
    protected Class<T> entityType;


    /**
     * Construtor da classe Repository.
     *
     * @param diretorioBase O diretório base onde as entidades serão armazenadas.
     * @param entityType    A classe da entidade, usada para deserialização de JSON.
     */
    public Repository(String diretorioBase, Class<T> entityType) {
        this.diretorioBase = diretorioBase;
        this.gson = GsonUtil.getGson();
        this.entityType = entityType;

        // Certifique-se de que o diretório base exista
        File dir = new File(diretorioBase);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    /**
     * Método abstrato para obter o identificador único de uma entidade.
     *
     * @param entidade A entidade para qual o ID será retornado.
     * @return O identificador único da entidade.
     */
    public abstract String getId(T entidade);



    /**
     * Método para obter o diretório específico onde o arquivo será salvo.
     *
     * @param entidade A entidade para a qual o diretório será obtido.
     * @return O caminho do diretório específico.
     */
    public String getDiretorioEspecifico(T entidade) {
        return diretorioBase;
    }




    /**
     * Salva a entidade fornecida em um arquivo JSON no diretório específico.
     *
     * @param entidade A entidade a ser salva.
     */
    public void salvar(T entidade) {
        String pastaEspecifica = getDiretorioEspecifico(entidade);
        File dir = new File(pastaEspecifica);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(pastaEspecifica + getId(entidade) + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(entidade, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Busca uma entidade pelo seu ID no repositório.
     *
     * @param entidade A entidade com o ID a ser buscado.
     * @return A entidade encontrada ou {@code null} se não existir.
     */
    public T buscarPorId(T entidade) {
        File file = new File(getDiretorioEspecifico(entidade) + getId(entidade) + ".json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, entityType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Retorna uma lista com todas as entidades presentes no diretório base.
     *
     * @return Uma lista de todas as entidades armazenadas.
     */
    public List<T> buscarTodos() {
        List<T> entidades = new ArrayList<>();
        File directory = new File(diretorioBase);

        if (directory.exists() && directory.isDirectory()) {
            File[] subDirs = directory.listFiles(File::isDirectory);

            if (subDirs != null) {
                for (File subDir : subDirs) {
                    File[] files = subDir.listFiles((dir, name) -> name.endsWith(".json"));
                    if (files != null) {
                        for (File file : files) {
                            try (FileReader reader = new FileReader(file)) {
                                T entidade = gson.fromJson(reader, entityType);
                                entidades.add(entidade);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return entidades;
    }



    /**
     * Edita uma entidade já existente, substituindo o conteúdo do arquivo JSON.
     *
     * @param entidadeAtualizada A entidade atualizada a ser salva.
     * @throws FileNotFoundException Se o arquivo da entidade não for encontrado.
     */
    public void editarEntidade(T entidadeAtualizada) throws FileNotFoundException {
        File file = new File(getDiretorioEspecifico(entidadeAtualizada) + getId(entidadeAtualizada) + ".json");
        if (file.exists()) {
            T entidate = buscarPorId(entidadeAtualizada);
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(entidadeAtualizada, writer);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar a entidade atualizada no arquivo.", e);
            }
    } else {
        throw new FileNotFoundException("Arquivo da entidade não encontrado.");
    }
    }

}
