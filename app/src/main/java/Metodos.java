
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class  Metodos {




    public static List<tarefa> ordenarTarefas(ArrayList<tarefa> lista, String opcao){
       switch (opcao){

           case "categoria":
               Map<String, List<tarefa>> AgrupamentoPorCategoria = new LinkedHashMap<>();
               for (tarefa t: lista){
                   String categoria=t.getCategoria();
                   AgrupamentoPorCategoria.putIfAbsent(categoria, new ArrayList<>());
                   AgrupamentoPorCategoria.get(categoria).add(t);
               }
               List <tarefa> listaorganizadaCategoria= new ArrayList<>();
               for (List<tarefa> grupos: AgrupamentoPorCategoria.values()){
                   listaorganizadaCategoria.addAll(grupos);
               }

               return listaorganizadaCategoria;


           case "status":
               Map <String, List<tarefa>> agrupamentoPorStatus = new LinkedHashMap<>();
               for  (tarefa t: lista){
                   String status = t.getStatus();
                   agrupamentoPorStatus.putIfAbsent(status,new ArrayList<>());
                   agrupamentoPorStatus.get(status).add(t);

               }
               List<tarefa> listaOrganizadaPorStatus = new ArrayList<>();
               for (List<tarefa> grupo : agrupamentoPorStatus.values()){
                   listaOrganizadaPorStatus.addAll(grupo);
               }

               return listaOrganizadaPorStatus;



           case "prioridade":
               Map <Integer, List<tarefa>> agrupamentoPorPrioridade = new TreeMap <> (Collections.reverseOrder());
               for (tarefa t: lista){
                   int prioridade;
                   prioridade=Integer.parseInt(t.getPrioridade());
                   agrupamentoPorPrioridade.putIfAbsent(prioridade,new ArrayList<>());
                   agrupamentoPorPrioridade.get(prioridade).add(t);
               }
               List<tarefa> listaOrganizadaPorPrioridade = new ArrayList<>();
               for (List<tarefa> grupo : agrupamentoPorPrioridade.values()){
                   listaOrganizadaPorPrioridade.addAll(grupo);
               }
               return  listaOrganizadaPorPrioridade;

           default:
               return List.of();
       }

    }



    public static Boolean checarmaior(int maior ,int menor){
        return maior >= menor;
    }



    public static Boolean verificarDisponibilidadeNome(ArrayList<tarefa> tarefasRegistradas, String nome){

        boolean disponivel=true;

        for (tarefa t: tarefasRegistradas){
            if (t.getNome().equals(nome)){
                disponivel=false;
                break;

            }
        }

        return disponivel;

    }








    public static ArrayList <tarefa> removerTarefa(ArrayList<tarefa> tarefasRegistradas, String nomeTarefa){
        for (tarefa t: tarefasRegistradas){
            if(t.getNome().equals(nomeTarefa)){
                tarefasRegistradas.remove(t);
                break;
            }
        }

        return tarefasRegistradas;
    }




    public static ArrayList<tarefa> editorTarefa(ArrayList<tarefa> tarefas, String nomeTarefa, String novoStatus){

        for (tarefa t: tarefas){
            if(t.getNome().equals(nomeTarefa)){
                t.setStatus(novoStatus);
                break;
            }
        }

        return tarefas;


    }



    public static  tarefa criarTarefa(String [] partes){

        return new tarefa(partes[0],partes[1],partes[2],partes[3],partes[4],partes[5],Boolean.parseBoolean(partes[6]),partes[7]);
    }


    public static String transformarTarefaEmLinha(tarefa t) {
        return String.join(",",
                t.getNome(),
                t.getDescricao(),
                t.getDatatermino(),
                t.getCategoria(),
                t.getStatus(),
                t.getPrioridade(),
                String.valueOf(t.getAlarme()),
                t.getHorario()
        );
    }



}
