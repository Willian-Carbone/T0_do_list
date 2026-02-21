import spock.lang.Specification
import java.time.LocalDate
import java.time.LocalTime

class FileManagerSpec extends Specification {

    def "Criar tarefas com um vetor de atributos"(){
        given: "um vetor de atributos"

        String [] lista = ["nome","descriçao","datatermino","prioridade","categoria","status","true","horaalarme"]

        when: "esse vetor é passado ao contrutor"

        def objeto = FileManager.criador_tarefa(lista)

        then:"um objeto tarefa deve ser criado"

        objeto instanceof tarefa

    }


    def "Transformar um objeto tarefa em linhas legiveis" (){


        given: "um objeto tarefa com atributos definidos"

        tarefa tarefaExemplo = new tarefa ("nome","descriçao","datatermino","prioridade","categoria","status",true,"horaalarme")

        when: "o objeto é passado ao metodo transformar em linha"

        String atributoEmlinha=FileManager.transformarTarefaEmLinha(tarefaExemplo)

        then:

        atributoEmlinha == "nome,descriçao,datatermino,prioridade,categoria,status,true,horaalarme"



    }


    def "deve processar o alarme corretamente para diferentes cenários"() {
        given: "o dia de hoje fixo para o teste"
        def hoje = LocalDate.of(2024, 5, 20)
        def agora = LocalTime.of(12, 0)

        expect: "o processamento da linha deve retornar o resultado esperado"
        FileManager.processarAlarmeDaLinha(linha, hoje, agora) == resultadoEsperado

        where:
        cenario                | linha                                          | resultadoEsperado
        "Tempo faltante"       | "Estudar,desc,20/05 15:00,cat,status,1,F,10:00" | "Tarefa:Estudar Tempo faltante: 03:00"
        "Prazo acaba agora"    | "Café,desc,20/05 12:00,cat,status,1,F,08:00"   | "Tarefa:Café Tempo faltante: Prazo acaba agora"
        "Esgotado"             | "Aula,desc,20/05 11:30,cat,status,1,F,09:00"   | "Tarefa:Aula Tempo faltante: Esgotado"
        "Dia diferente (mês)"  | "Viagem,desc,20/06 15:00,cat,status,1,F,10:00" | null
        "Ainda não deu o alarme"| "Sono,desc,20/05 15:00,cat,status,1,F,13:00"   | null
        "Linha mal formatada"  | "Erro,incompleto,123"                          | null
    }



}
