
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        ArrayList <String> alarmesDeHoje = FileManager.emissaoAlarme();

        System.out.println("=====================");
        System.out.println("tarefas que vencem hoje");
        if (alarmesDeHoje.isEmpty()) {
            System.out.println("Nenhum alarme disparado, volte mais tarde");
            System.out.println("=====================");
        }

        else {
            for (String tarefasComAlarmePAraHoje:alarmesDeHoje) {
                System.out.println(tarefasComAlarmePAraHoje);
                System.out.println();
            }
            System.out.println("========================");
        }
        


        ArrayList<tarefa> minhasTarefas = FileManager.emitirTarefas();


        Scanner scan = new Scanner(System.in);
        System.out.println("Digite 1 para inserir uma tarefa , 2 para ver as tarefas ja registradas , 3 para remover tarefa ou 4 para editar status ");
        String opcaoEscolhida = scan.nextLine();
        

        while(!List.of("1","2","3","4").contains(opcaoEscolhida)) {
            System.out.println("Digite um valor valido");
            opcaoEscolhida= scan.nextLine();
        }


        switch (opcaoEscolhida) {
            case "1" -> {
                String nome, descricao, dataTermino;
                System.out.println("Digite o nome do tarefa");
                nome = scan.nextLine();

                while (!Metodos.verificarDisponibilidadeNome(minhasTarefas,nome)) {
                    System.out.println("O nome já esta sendo usado por outra tarefa ,escolha outro");
                    nome = scan.nextLine();

                }

                System.out.println("Digite a descricao do tarefa");
                descricao = scan.nextLine();
                
                System.out.println("Digite a data de termino da tarefa com o horario de vencimento no formato dd/mm/hora:minutos ");
                dataTermino = scan.nextLine();

                while (!dataTermino.matches("^[0-3][0-9]/[0-1][0-9]/([0-1][0-9]|2[0-3]):[0-5][0-9]$")) {
                    System.out.println("Digite um valor valido");
                    dataTermino = scan.nextLine();
                }

                System.out.println("Deseja colocar um alarme para a tarefa? digite s para sim ou n para não");
                String opcaoAlarme = scan.nextLine();

                while (!List.of("s","n").contains(opcaoAlarme)) {
                    System.out.println("Digite um valor valido");
                    opcaoAlarme = scan.nextLine();
                }

                boolean alarmeEscolha = false;
                String hora = null;


                if (opcaoAlarme.equals("s")) {
                    alarmeEscolha = true;

                    System.out.println("Digite o horario do alarme para o dia");
                    hora = scan.nextLine();

                    while (!hora.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")) {
                        System.out.println("Digite um valor valido");
                        hora = scan.nextLine();
                    }


                    int horaMaximaParaTermino = Integer.parseInt(dataTermino.substring(6, 8));
                    int minutoMaximoParaTermino = Integer.parseInt(dataTermino.substring(9));


                    int horaAlarme = Integer.parseInt(hora.substring(0, 2));
                    int minutoAlarme = Integer.parseInt(hora.substring(3));

                    while (!Metodos.checarmaior(horaMaximaParaTermino, horaAlarme) || (horaMaximaParaTermino == horaAlarme && !Metodos.checarmaior(minutoMaximoParaTermino, minutoAlarme))) {
                        System.out.println("Digite um horario de alarmme inferior ao horario maximo da tarefa");
                        hora = scan.nextLine();


                        while (!hora.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")) {
                            System.out.println("Digite um valor valido");
                            hora = scan.nextLine();
                        }

                        horaAlarme = Integer.parseInt(hora.substring(0, 2));
                        minutoAlarme = Integer.parseInt(hora.substring(3));

                    }


                }


                System.out.println("Digite a categoria da tarefa: 1 para trabalho, 2 para lazer e 3 para hobbyes");
                String opcaoCategoria = scan.nextLine();

                while (!List.of("1", "2", "3").contains(opcaoCategoria)) {
                    System.out.println("Digite um valor valido");
                    opcaoCategoria = scan.nextLine();
                }

                System.out.println("Digite um valor de prioridade de 1 a 5 , sendo 1 menos urgente e 5 mais urgente");
                String opcaoPrioridade = scan.nextLine();

                while (!List.of("1", "2", "3", "4", "5").contains(opcaoPrioridade)) {
                    System.out.println("Insira um valor válido");
                    opcaoPrioridade = scan.nextLine();

                }

                System.out.println("Digite o status atual da tarefa 1 para to Do 2 para Doing e 3 para Done");
                String opcaoStatus = scan.nextLine();

                while (!List.of("1", "2", "3").contains(opcaoStatus)) {
                    System.out.println("Insira um valor válido");
                    opcaoStatus = scan.nextLine();

                }

                tarefa tarefaCriada = new tarefa(nome, descricao, dataTermino, opcaoCategoria, opcaoStatus, opcaoPrioridade, alarmeEscolha, hora);
                System.out.println("Tarefa registrada com sucesso");
                minhasTarefas.add(tarefaCriada);
                FileManager.atualizarArquivo(minhasTarefas);

                scan.close();


            }
            case "2" -> {

                System.out.println("Defina o Método de visualização : 1 categoria, 2 status , 3 prioridade");
                String opcaoVisualizacaoEscolhida = scan.nextLine();
                while (!List.of("1", "2", "3").contains(opcaoVisualizacaoEscolhida)) {
                    System.out.println("Insira um valor válido");
                    opcaoVisualizacaoEscolhida = scan.nextLine();

                }

                List<tarefa> impressao = new ArrayList<>();

                impressao = switch (opcaoVisualizacaoEscolhida) {
                    case "1" -> Metodos.ordenarTarefas(minhasTarefas, "categoria");
                    case "2" -> Metodos.ordenarTarefas(minhasTarefas, "status");
                    case "3" -> Metodos.ordenarTarefas(minhasTarefas, "prioridade");
                    default -> impressao;
                };

                if (impressao.isEmpty()) {
                    System.out.println("Nenhuma tarefa foi encontrada");
                } else {
                    System.out.println("==============================");
                    System.out.println("     TAREFAS ENCONTRADAS      ");
                    System.out.println("=============================");

                    int i = 1;
                    for (tarefa tarefa : impressao) {
                        System.out.println("Tarefa: " + i++);
                        System.out.println("Nome: " + tarefa.getNome());
                        System.out.println("Descricao: " + tarefa.getDescricao());
                        System.out.println("Categoria: " + tarefa.getCategoria());
                        System.out.println("Status: " + tarefa.getStatus());
                        System.out.println("Prioridade: " + tarefa.getPrioridade());
                        System.out.println("data fim:" + tarefa.getDatatermino());
                        System.out.println("Alarme definido:" + tarefa.getAlarme());
                        System.out.println("Horario:" + tarefa.getHorario());
                        System.out.println();
                    }
                    scan.close();
                }
            }
            case "3" -> {
                System.out.println("Digite o nome da tarefa que deseja remover");
                String nome = scan.nextLine();

                while (Metodos.verificarDisponibilidadeNome(minhasTarefas,nome)) {
                    System.out.println("Tarefa não encontrada, digite uma tarefa existente");
                    nome = scan.nextLine();
                }

                Metodos.removerTarefa(minhasTarefas,nome);
                System.out.println("Tarefa removida com sucesso");

            }
            default -> {

                System.out.println("Digite o nome da tarefa que deseja editar");
                String nome = scan.nextLine();

                while (Metodos.verificarDisponibilidadeNome(minhasTarefas,nome)) {
                    System.out.println("Tarefa não encontrada, digite uma tarefa existente");
                    nome = scan.nextLine();
                }

                String opcao1 = "";
                String opcao2 = "";
                String statusAtual = "";
                String novoStatus = "";




                for (tarefa t : minhasTarefas) {
                    if (t.getNome().equals(nome)) {
                        statusAtual = t.getStatus();

                        switch (statusAtual) {

                            case "To DO":
                                opcao1 = "Doing";
                                opcao2 = "Done";
                                break;

                            case "Doing":
                                opcao1 = "To DO";
                                opcao2 = "Done";
                                break;

                            case "Done":
                                opcao1 = "To DO";
                                opcao2 = "Doing";
                                break;
                        }

                        break;

                    }

                }

                System.out.println("A tarefa atual possui o status:" + statusAtual + " " + " digite 1 para trocar para:" + opcao1 + " " + "ou 2 para trocar para:" + opcao2);
                String escolhaDeNovoStatus = scan.nextLine();

                while (List.of("1","2").contains(escolhaDeNovoStatus)) {
                    System.out.println("Digite um valor válido");
                    escolhaDeNovoStatus = scan.nextLine();
                }


                novoStatus = switch (escolhaDeNovoStatus) {
                    case "1" -> opcao1;
                    case "2" -> opcao2;
                    default -> novoStatus;
                };

                Metodos.editorTarefa(minhasTarefas,novoStatus, nome);
                System.out.print("Status editado com sucesso");

            }
        }




    }
}
