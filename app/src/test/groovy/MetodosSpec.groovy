import spock.lang.Specification

class MetodosSpec extends Specification{

    def "Teste organizador de tarefas categoria/status/prioridade"(){
        given: "Lista de tarefas inicial e suas tarefas"

        def t1 = new tarefa("Tarefa A", "...", "...", "trabalho", "To DO", "1", false, "...")
        def t2 = new tarefa("Tarefa B", "...", "...", "lazer", "Doing", "2", false, "...")
        def t3 = new tarefa("Tarefa C", "...", "...", "hobbyes", "Done", "3", false, "...")
        def t4 = new tarefa("Tarefa D", "...", "...", "trabalho", "To DO", "1", false, "...")
        def t5 = new tarefa("Tarefa E", "...", "...", "lazer", "Doing", "2", false, "...")
        def t6 = new tarefa("Tarefa F", "...", "...", "hobbyes", "Done", "3", false, "...")

        List <tarefa> tarefas = [t1,t2,t3,t4,t5,t6]

        String categoria = "categoria"
        String status= "status"
        String prioridade = "prioridade"

        when: "Passando a lista com a opçao de organização selecionada"

        ArrayList<tarefa> OrganizacaoPorcategoria =Metodos.ordenarTarefas(tarefas,categoria)
        ArrayList<tarefa>  OrganizacaoPorStatus =Metodos.ordenarTarefas(tarefas,status)
        ArrayList<tarefa>  OrganizacaoPorprioridade =Metodos.ordenarTarefas(tarefas,prioridade)


        then: "A lista deve ser organizada usando como parametro a opção escolhida"

        OrganizacaoPorcategoria[0].getCategoria() == OrganizacaoPorcategoria[1].getCategoria()
        OrganizacaoPorStatus[0].getStatus() == OrganizacaoPorStatus[1].getStatus()
        OrganizacaoPorprioridade[0].getPrioridade() == OrganizacaoPorprioridade[1].getPrioridade()




    }

    def "Teste verificador disponibilidade de nome"(){

        def t1 = new tarefa("exemplo", "...", "...", "trabalho", "To DO", "1", false, "...")
        def t2 = new tarefa("exemplo2", "...", "...", "lazer", "Doing", "2", false, "...")

        def lista =[t1,t2]

        when: "é realizada uma uma pesquisa se o nome esta disponivel"

        Boolean n1 = Metodos.verificarDisponibilidadeNome(lista,"exemplo")
        Boolean n2 = Metodos.verificarDisponibilidadeNome(lista,"exemplo2")
        Boolean n3 = Metodos.verificarDisponibilidadeNome(lista,"exemplo3")

        then:

        n1 == false
        n2 == false
        n3 == true



    }

    def "Teste remover de um objeto tarefa especifico de uma lista tarefa"(){
        given: "uma lista e tarefas"

        def t1 = new tarefa("exemplo", "...", "...", "trabalho", "To DO", "1", false, "...")
        def t2 = new tarefa("exemplo2", "...", "...", "lazer", "Doing", "2", false, "...")

        def lista =[t1,t2]

        when: "é informado o nome da tarefa que quer ser removida"

        lista = Metodos.removerTarefa(lista, "exemplo")

        then: "a tarefa especificada é removida"

        lista.size() ==1
        lista[0].getNome()=="exemplo2"

    }


    def "Teste de edição de status de uma tarefa escolhida pelo nome"(){
        given: "uma lista e tarefas"

        def t1 = new tarefa("Tarefa1", "...", "...", "trabalho", "To DO", "1", false, "...")
        def t2 = new tarefa("Tarefa2", "...", "...", "lazer", "Doing", "2", false, "...")

        def lista =[t1,t2]



        when: "passasse uma lista de tarefas , o nome da tarefa que tera seu status alterado e o novo status"
        lista = Metodos.editorTarefa(lista,"Tarefa1","NovoStatus")

        then: "Somente o status da tarefa ifnormada deve ser editado"

        lista[0].getStatus()=="NovoStatus"
        lista[1].getStatus() == "Doing"


    }





}
