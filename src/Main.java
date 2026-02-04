
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList <String> alarmesdehoje = Metodos.emissaoalarme();

        System.out.println("=====================");
        System.out.println("tarefas que vencem hoje");
        if (alarmesdehoje.isEmpty()) {
            System.out.println("Nenhum alarme disparado, volte mais tarde");
            System.out.println("=====================");
        }

        else {
            for (String tarefadehoje:alarmesdehoje) {
                System.out.println(tarefadehoje);
                System.out.println();
            }
            System.out.println("========================");
        }


        ArrayList<tarefa> minhas_tarefas = Metodos.emitirtarefas();


        Scanner sc = new Scanner(System.in);
        System.out.println("Digite 1 para inserir uma tarefa ou 2 para ver as tarefas ja registradas");
        String opcaoacao = sc.nextLine();

        while(!opcaoacao.equals("1") && !opcaoacao.equals("2")){
            System.out.println("Digite um valor valido");
            opcaoacao= sc.nextLine();
        }


        if (opcaoacao.equals("1")){
            String nome, descricao,datatermino;
            System.out.println("Digite o nome do tarefa");
            nome = sc.nextLine();

            while (!Metodos.disponibilidadenome(nome)){
                System.out.println("O nome já esta sendo usado por outra tarefa ,escolha outro");
                nome=sc.nextLine();

            }


            System.out.println("Digite a descricao do tarefa");
            descricao = sc.nextLine();
            System.out.println("Digite a data de termino da tarefa com o horario de vencimento no formato dd/mm/hora:minutos ");
            datatermino = sc.nextLine();

            while (!datatermino.matches("^[0-3][0-9]/[0-1][0-9]/([0-1][0-9]|2[0-3]):[0-5][0-9]$")){
                System.out.println("Digite um valor valido");
                datatermino = sc.nextLine();
            }

            System.out.println("Deseja colocar um alarme para a tarefa? digite s para sim ou n para não");
            String alarme = sc.nextLine();

            while ( !(alarme.equals("s") || alarme.equals("n"))){
                System.out.println("Digite um valor valido");
                alarme = sc.nextLine();
            };

            Boolean alarmeescolha = false;
            String hora= null;






            if (alarme.equals("s")){
                alarmeescolha=true;

                System.out.println("Digite o horario do alarme para o dia");
                hora = sc.nextLine();

                while (!hora.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")){
                    System.out.println("Digite um valor valido");
                    hora = sc.nextLine();
                }



                int horamaxtarefa= Integer.parseInt(datatermino.substring(6,8));
                int minutomaxtarefa = Integer.parseInt(datatermino.substring(9));



                int horaalarme= Integer.parseInt(hora.substring(0,2));
                int minutoalarme=Integer.parseInt(hora.substring(3));

                while (!Metodos.checarmaior(horamaxtarefa,horaalarme) || (horamaxtarefa==horaalarme && !Metodos.checarmaior(minutomaxtarefa,minutoalarme)) ){
                    System.out.println("Digite um horario de alarmme inferior ao horario maximo da tarefa");
                    hora=sc.nextLine();
                    horaalarme= Integer.parseInt(hora.substring(0,2));
                    minutoalarme=Integer.parseInt(hora.substring(3));


                    while (!hora.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")){
                        System.out.println("Digite um valor valido");
                        hora = sc.nextLine();
                    }

                    horaalarme= Integer.parseInt(hora.substring(0,2));
                    minutoalarme=Integer.parseInt(hora.substring(3));

                }




            };




            System.out.println("Digite a categoria da tarefa: 1 para trabalho, 2 para lazer e 3 para hobbyes");
            String opcaocategoria = sc.nextLine();

            while(!opcaocategoria.equals("1") && !opcaocategoria.equals("2") && !opcaocategoria.equals("3")){
                System.out.println("Digite um valor valido");
                opcaocategoria = sc.nextLine();
            }

            System.out.println("Digite um valor de prioridade de 1 a 5 , sendo 1 menos urgente e 5 mais urgente");
            String opcaoprioridade = sc.nextLine();

            while(!List.of("1","2","3","4","5").contains(opcaoprioridade)) {
                System.out.println("Insira um valor válido");
                opcaoprioridade=sc.nextLine();

            }

            System.out.println("Digite o status atual da tarefa 1 para to Do 2 para Doing e 3 para Done");
            String opcaostatusatual = sc.nextLine();

            while(!List.of("1","2","3").contains(opcaostatusatual)) {
                System.out.println("Insira um valor válido");
                opcaostatusatual=sc.nextLine();

            }

            tarefa tarefacriada= new tarefa (nome, descricao , datatermino , opcaocategoria,opcaostatusatual,opcaoprioridade,alarmeescolha,hora);
            System.out.println("Tarefa registrada com sucesso");
            minhas_tarefas.add(tarefacriada);
            Metodos.atualiza_arquivo(minhas_tarefas);

            sc.close();


        }

        else{

            System.out.println("Defina o Método de : 1 categoria, 2 status , 3 prioridade");
            String opcaovisu = sc.nextLine();
            while(!List.of("1","2","3").contains(opcaovisu)) {
                System.out.println("Insira um valor válido");
                opcaovisu=sc.nextLine();

            }

            List<tarefa> impressao=new ArrayList<>();

            switch (opcaovisu){
                case "1":
                    impressao=Metodos.organizador(minhas_tarefas,"categoria");
                    break;
                case "2" :
                    impressao=Metodos.organizador(minhas_tarefas,"status");
                    break;
                case "3":
                    impressao=Metodos.organizador(minhas_tarefas,"prioridade");
            }

            if (impressao.isEmpty()){
                System.out.println("Nenhuma tarefa foi encontrada");
            }

            else{
                System.out.println("==============================");
                System.out.println("     TAREFAS ENCONTRADAS      ");
                System.out.println("=============================");

                int i=1;
                for (tarefa tarefa : impressao) {
                    System.out.println("Tarefa: "+ i++);
                    System.out.println("Nome: " + tarefa.getNome());
                    System.out.println("Descricao: " + tarefa.getDescricao());
                    System.out.println("Categoria: " + tarefa.getCategoria());
                    System.out.println("Status: " + tarefa.getStatus());
                    System.out.println("Prioridade: " + tarefa.getPrioridade());
                    System.out.println("data fim:" + tarefa.getDatatermino());
                    System.out.println("Alarme definido:" + tarefa.getAlarme());
                    System.out.println("Horario:" +tarefa.getHorario());
                    System.out.println();
                }
            }








        }




    }
}
