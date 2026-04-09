public class tarefa {
    private final String nome;
    private final String descricao;
    private final String datatermino;
    private final String categoria;
    private String status;
    private final String prioridade;
    private final Boolean alarme;
    private final String horario;

    public tarefa(String nome, String descricao, String datatermino, String categoria, String status, String prioridade, Boolean alarme,  String horario ) {
        this.nome = nome;
        this.descricao = descricao;
        this.datatermino = datatermino;
        this.prioridade = prioridade;
        this.alarme=alarme;
        this.horario=horario;


        this.categoria = traduzirCategoria(categoria);


        this.status = traduzirStatus(status);
    }

    private String traduzirCategoria(String valor) {
        return switch (valor) {
            case "1", "trabalho" -> "trabalho";
            case "2", "lazer" -> "lazer";
            case "3", "hobbyes" -> "hobbyes";
            default -> valor;
        };
    }

    private String traduzirStatus(String valor) {
        return switch (valor) {
            case "1", "To DO" -> "To DO";
            case "2", "Doing" -> "Doing";
            case "3", "Done" -> "Done";
            default -> valor;
        };
    }


    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDatatermino() { return datatermino; }
    public String getCategoria() { return categoria; }
    public String getStatus() { return status; }
    public void setStatus(String status){this.status = status;}
    public String getPrioridade() { return prioridade; }
    public String getAlarme() { return Boolean.toString(alarme); }
    public String getHorario() { return horario; }
}