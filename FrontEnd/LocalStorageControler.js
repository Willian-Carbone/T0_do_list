


export class Tarefa{

    constructor(nome, descricao, data,hora_maxima, categoria, status, prioridade,alarmeEscolha, horaAlarme, ){
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.hora_maxima = hora_maxima;
        this.categoria = categoria;
        this.status = status;
        this.prioridade = prioridade;
        this.alarme_escolha = alarmeEscolha;
        this.hora_alarme = horaAlarme
    }




}


export function ordenador(tarefas, escolhaDeOrdenacao){


     switch (escolhaDeOrdenacao) {
        case "prioridade":

        
        let gruposPrioridade = {};

        for (let t of tarefas) {

            let prioridade= t.prioridade;

            if (!gruposPrioridade[prioridade]) {
                gruposPrioridade[prioridade] = [];
            }

            gruposPrioridade[prioridade].push(t);
        }

        let listaOrganizadaPrioridade = [];

        for (let grupo of Object.values(gruposPrioridade)) {
            listaOrganizadaPrioridade.push(...grupo);
        }

        return listaOrganizadaPrioridade;


        case "status":

        let gruposStatus = {};

        for (let t of tarefas) {

            let status= t.status;

            if (!gruposStatus[status]) {
                gruposStatus[status] = [];
            }

            gruposStatus[status].push(t);
        }

        let listaOrganizadaStatus = [];

        for (let grupo of Object.values(gruposStatus)) {
            listaOrganizadaStatus.push(...grupo);
        }

        return listaOrganizadaStatus;






        case "categoria":
          

        let gruposCategoria = {};

        for (let t of tarefas) {

            let categoria = t.categoria;

            if (!gruposCategoria[categoria]) {
                gruposCategoria[categoria] = [];
            }

            gruposCategoria[categoria].push(t);
        }

        let listaOrganizadaCategoria = [];

        for (let grupo of Object.values(gruposCategoria)) {
            listaOrganizadaCategoria.push(...grupo);
        }

        return listaOrganizadaCategoria;



    }




}



export function salvarTarefa(chave, valor){

    localStorage.setItem(chave,valor)

}


export function CapturarTarefas(){

    let tarefas = [];


    for (let i = 0; i < localStorage.length; i++) {
        let chave = localStorage.key(i);
        let valor = JSON.parse(localStorage.getItem(chave));
        tarefas.push(valor);
    }

    return tarefas;



}


export function editarTarefa(nova_prioridade, novo_status, novo_alarme, nova_descricao, tarefas){
   

    for (let i=0; i<tarefas.length; i++){

        if (nova_descricao !== ""){
            tarefas[i].descricao = nova_descricao
            
        }
        if (nova_prioridade !== "manter_anterior"){
            tarefas[i].prioridade = nova_prioridade
            
        }

        if (novo_status !== "manter_anterior"){
            tarefas[i].status = novo_status
            
        }           


        if (novo_alarme !==""){
            tarefas[i].alarme_escolha = "definido"
            tarefas[i].hora_alarme = novo_alarme
            
        }


        localStorage.setItem(tarefas[i].nome,JSON.stringify(tarefas[i]))
            

        
    
    
    
    }

   
}

export function listarAlarmesParaDisparar() {

    let tarefas = CapturarTarefas();
    let alarmesParaDisparar = [];

    let agora = new Date();

    for (let i = 0; i < tarefas.length; i++) {

        if (tarefas[i].alarme_escolha !== "definido") continue;

       
        let [dia, mes, ano] = tarefas[i].data.split("/");

        let dataHoraAlarme = new Date(
            `${ano}-${mes}-${dia}T${tarefas[i].hora_alarme}`
        );

        if (agora >= dataHoraAlarme) {
            alarmesParaDisparar.push(tarefas[i]);
        }
    }

    return alarmesParaDisparar;
}


export function dispararAlarme(listaAlarmes){
   
    for (let i = 0; i < listaAlarmes.length; i++) {

        alert("Alarme disparado para a tarefa " + listaAlarmes[i].nome);

        listaAlarmes[i].alarme_escolha = "disparado";

        localStorage.setItem(
            listaAlarmes[i].nome,
            JSON.stringify(listaAlarmes[i])
        );
    }

    


}

export function removerTarefas(tarefasParaRemover){

    let contador =0
    
    for (let tarefa in tarefasParaRemover){
        contador ++
        localStorage.removeItem(tarefasParaRemover[tarefa].nome)
    }

    return contador

    


}



export function capturarTarefasMarcadas (marcados) {

    let tarefas = [];

    marcados.forEach(checkbox => {
        let chave = checkbox.id;

        let dado = JSON.parse(localStorage.getItem(chave));

        tarefas.push(dado);

    });

    return tarefas;

}
