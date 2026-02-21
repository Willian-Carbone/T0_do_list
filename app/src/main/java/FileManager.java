import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileManager {

    public static tarefa  criador_tarefa(String[] partes){

        tarefa tr = new tarefa(partes[0],partes[1],partes[2],partes[3],partes[4],partes[5],Boolean.parseBoolean(partes[6]),partes[7]);

        return tr;
    }

    public static ArrayList<tarefa> emitirtarefas( ){
        File arq = new File("tarefas.txt");
        ArrayList<tarefa> lista = new ArrayList<>();

        try {
            Scanner leitor = new Scanner(arq);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                String[] partes = linha.split(",");

                tarefa tarefaCriada= criador_tarefa(partes);

                lista.add(tarefaCriada);

            }

            leitor.close();}
        catch (FileNotFoundException e) {
            System.out.print("arquivo nao encontrado");
        }

        return lista;


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




    public static void atualiza_arquivo(ArrayList<tarefa> lista){

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("tarefas.txt"));
            for (tarefa t: lista){

                String linhaFormatada = transformarTarefaEmLinha(t);
                bw.write(linhaFormatada);

                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.print("arquivo nao encontrado");
        }

    }

    public static String processarAlarmeDaLinha(String linha, LocalDate hoje, LocalTime agora) {
        String[] partes = linha.split(",");

        try {

            int diaT = Integer.parseInt(partes[2].substring(0, 2));
            int mesT = Integer.parseInt(partes[2].substring(3, 5));
            int horaA = Integer.parseInt(partes[7].substring(0, 2));
            int minA = Integer.parseInt(partes[7].substring(3));


            if (hoje.getDayOfMonth() != diaT || hoje.getMonthValue() != mesT) return null;
            if (agora.getHour() < horaA || (agora.getHour() == horaA && agora.getMinute() < minA)) return null;


            int horaT = Integer.parseInt(partes[2].substring(6, 8));
            int minT = Integer.parseInt(partes[2].substring(9));

            int agoraEmMin = (agora.getHour() * 60) + agora.getMinute();
            int tarefaEmMin = (horaT * 60) + minT;
            int diferenca = tarefaEmMin - agoraEmMin;

            String faltante;
            if (diferenca > 0) {
                faltante = String.format("%02d:%02d", diferenca / 60, diferenca % 60);
            } else if (diferenca < 0) {
                faltante = "Esgotado";
            } else {
                faltante = "Prazo acaba agora";
            }

            return "Tarefa:" + partes[0] + " Tempo faltante: " + faltante;

        } catch (Exception e) {
            return null;
        }
    }








    public static ArrayList <String> emissaoalarme()  {

        File arq = new File("tarefas.txt");
        ArrayList<String> saida = new ArrayList<>();
        LocalTime agora = LocalTime.now();
        LocalDate hoje = LocalDate.now();

        try (Scanner sc = new Scanner(arq)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (linha.isEmpty()) continue;


                String resultado = processarAlarmeDaLinha(linha, hoje, agora);

                if (resultado != null) {
                    saida.add(resultado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saida;


    }


}
