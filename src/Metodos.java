import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class  Metodos {
    public static ArrayList<tarefa> emitirtarefas( ){
        File arq = new File("tarefas.txt");
        ArrayList<tarefa> lista = new ArrayList<>();

        try {Scanner leitor = new Scanner(arq);

        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine();
            String[] partes = linha.split(",");
            tarefa tr = new tarefa(partes[0],partes[1],partes[2],partes[3],partes[4],partes[5],Boolean.parseBoolean(partes[6]),partes[7]);
            lista.add(tr);

        }

        leitor.close();}

        catch (FileNotFoundException e) {
            System.out.print("arquivo nao encontrado");
        }

        return lista;



    }

    public static void atualiza_arquivo(ArrayList<tarefa> lista){

        try{
           BufferedWriter bw = new BufferedWriter(new FileWriter("tarefas.txt"));
           for (tarefa t: lista){
               bw.write(t.getNome()+",");
               bw.write(t.getDescricao()+",");
               bw.write(t.getDatatermino()+",");
               bw.write(t.getCategoria()+",");
               bw.write(t.getStatus()+",");
               bw.write(t.getPrioridade()+",");
               bw.write(t.getAlarme()+",");
               bw.write(t.getHorario()+",");

               bw.newLine();
           }
           bw.close();

        } catch (IOException e) {
            System.out.print("arquivo nao encontrado");
        }

    }

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

    public static ArrayList <String> emissaoalarme()  {

        File arq = new File("tarefas.txt");
        ArrayList <String> saida = new ArrayList<>();

        try{Scanner sc= new  Scanner(arq);

            int horaatual= LocalTime.now().getHour();
            int minutoatual= LocalTime.now().getMinute();

            int diaatual = LocalDate.now().getDayOfMonth();
            int mesatual = LocalDate.now().getMonthValue();

            while (sc.hasNextLine()){
                String linha=sc.nextLine();
                String[] partes = linha.split(",");
                if (partes[6].equals("true")){

                    int diatarefa= Integer.parseInt(partes[2].substring(0,2));
                    int mestarefa = Integer.parseInt(partes[2].substring(3,5));

                    int horaalarme = Integer.parseInt(partes[7].substring(0,2));
                    int minutoalarme = Integer.parseInt(partes[7].substring(3));

                    int horatarefa = Integer.parseInt(partes[2].substring(6,8));
                    int minutotarefa = Integer.parseInt(partes[2].substring(9));




                    if ( diaatual==diatarefa && mesatual==mestarefa)
                    { if((horaatual>horaalarme || (horaatual==horaalarme && minutoatual>=minutoalarme))){
                        String faltante="";
                        int agoraemminutos = (horaatual*60) + minutoatual;
                        int tarefaemminutos = (horatarefa*60) + minutotarefa;

                        int diferenca= tarefaemminutos - agoraemminutos;
                        if (diferenca >0){
                            int conversaoh =diferenca / 60;
                            int conversaomin = diferenca %60;

                            faltante = String.format("%02d:%02d",conversaoh,conversaomin);
                        }
                        else if  (diferenca<0){
                            faltante = "Esgotado";
                        }
                        else{
                            faltante="Prazo acaba agora";
                        }


                        saida.add("Tarefa:" + partes[0] + " " + "Tempo faltante: " + faltante);}
                    }

                }

            }


        }

        catch(Exception e){
            e.printStackTrace();
        }

        return saida;




    }

    public static Boolean checarmaior(int maior ,int menor){
        if (maior<menor){
            return false;
        }

        else{
            return true;
        }
    }


    public static Boolean disponibilidadenome( String nome){

        ArrayList<tarefa> tarefas_registradas;
        tarefas_registradas=emitirtarefas();
        boolean disponivel=true;

        for (tarefa t: tarefas_registradas){
            if (t.getNome().equals(nome)){
                disponivel=false;
                break;

            };
        }

        return disponivel;



    }

    public static void removertarefa (String nome_tarefa){
        ArrayList<tarefa> tarefas_registradas = emitirtarefas();

        for (tarefa t: tarefas_registradas){
            if(t.getNome().equals(nome_tarefa)){
                tarefas_registradas.remove(t);
                break;
            }
        }

        atualiza_arquivo(tarefas_registradas);
    }






}
