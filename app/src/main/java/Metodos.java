import java.io.*;
import java.util.*;

public class  Metodos {




    public static List<tarefa> organizador( ArrayList<tarefa> lista, String opcao){
       switch (opcao){

           case "categoria":
               Map<String, List<tarefa>> gruposcategoria = new LinkedHashMap<>();
               for (tarefa t: lista){
                   String categoria=t.getCategoria();
                   gruposcategoria.putIfAbsent(categoria, new ArrayList<>());
                   gruposcategoria.get(categoria).add(t);
               }
               List <tarefa> listaorganizadaC= new ArrayList<>();
               for (List<tarefa> grupos: gruposcategoria.values()){
                   listaorganizadaC.addAll(grupos);
               }

               return listaorganizadaC;


           case "status":
               Map <String, List<tarefa>>grupoStatus = new LinkedHashMap<>();
               for  (tarefa t: lista){
                   String status = t.getStatus();
                   grupoStatus.putIfAbsent(status,new ArrayList<>());
                   grupoStatus.get(status).add(t);

               }
               List<tarefa> listaorganizadaS = new ArrayList<>();
               for (List<tarefa> grupo : grupoStatus.values()){
                   listaorganizadaS.addAll(grupo);
               }

               return listaorganizadaS;



           case "prioridade":
               Map <Integer, List<tarefa>> grupos = new TreeMap <> (Collections.reverseOrder());
               for (tarefa t: lista){
                   int prioridade;
                   prioridade=Integer.parseInt(t.getPrioridade());
                   grupos.putIfAbsent(prioridade,new ArrayList<>());
                   grupos.get(prioridade).add(t);
               }
               List<tarefa> listaorganizadaP = new ArrayList<>();
               for (List<tarefa> grupo : grupos.values()){
                   listaorganizadaP.addAll(grupo);
               }
               return  listaorganizadaP;


       }
        return List.of();
    }



    public static Boolean checarmaior(int maior ,int menor){
        if (maior<menor){
            return false;
        }

        else{
            return true;
        }
    }



    public static Boolean verificarExistencia(ArrayList<tarefa> tarefas_registradas, String nome){

        boolean disponivel=true;

        for (tarefa t: tarefas_registradas){
            if (t.getNome().equals(nome)){
                disponivel=false;
                break;

            }
        }

        return disponivel;

    }




    public static Boolean disponibilidadenome( String nome){

        ArrayList<tarefa> tarefas_registradas;
        tarefas_registradas=FileManager.emitirtarefas();
        return verificarExistencia(tarefas_registradas,nome);


    }



    public static ArrayList <tarefa> removedor (ArrayList<tarefa> tarefas_registradas, String nome_tarefa){
        for (tarefa t: tarefas_registradas){
            if(t.getNome().equals(nome_tarefa)){
                tarefas_registradas.remove(t);
                break;
            }
        }

        return tarefas_registradas;
    }






    public static void removertarefa (String nome_tarefa){
        ArrayList<tarefa> tarefas_registradas = FileManager.emitirtarefas();

        removedor (tarefas_registradas, nome_tarefa);

        FileManager.atualiza_arquivo(tarefas_registradas);
    }

    public static ArrayList<tarefa> editorTarefa(ArrayList<tarefa> tarefas, String nome_tarefa, String novo_status){

        for (tarefa t: tarefas){
            if(t.getNome().equals(nome_tarefa)){
                t.setStatus(novo_status);
                break;
            }
        }

        return tarefas;


    }





    public static void editar_status(String novo_status,String nome_tarefa){
        ArrayList<tarefa> tarefas = FileManager.emitirtarefas();

        tarefas = editorTarefa(tarefas,nome_tarefa,novo_status);

        FileManager.atualiza_arquivo(tarefas);
    }



}
