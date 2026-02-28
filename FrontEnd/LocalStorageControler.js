



export class Tarefa{

    constructor(nome, descricao, data,hora_maxima, categoria, status, prioridade,alarme_escolha, hora_alarme, ){
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.hora_maxima = hora_maxima;
        this.categoria = categoria;
        this.status = status;
        this.prioridade = prioridade;
        this.alarme_escolha = alarme_escolha;
        this.hora_alarme = hora_alarme
    }




}


export function ordenador(tarefas,escolha_de_ordenacao){


     switch (escolha_de_ordenacao) {
        case "prioridade":

        
        let gruposPrioridade = {};

        for (let t of tarefas) {

            let prioridade= t.prioridade;

            if (!gruposPrioridade[prioridade]) {
                gruposPrioridade[prioridade] = [];
            }

            gruposPrioridade[prioridade].push(t);
        }

        let listaOrganizadaP = [];

        for (let grupo of Object.values(gruposPrioridade)) {
            listaOrganizadaP.push(...grupo); 
        }

        return listaOrganizadaP;


        case "status":

        let gruposStatus = {};

        for (let t of tarefas) {

            let status= t.status;

            if (!gruposStatus[status]) {
                gruposStatus[status] = [];
            }

            gruposStatus[status].push(t);
        }

        let listaOrganizadaS = [];

        for (let grupo of Object.values(gruposStatus)) {
            listaOrganizadaS.push(...grupo); 
        }

        return listaOrganizadaS;






        case "categoria":
          

        let gruposCategoria = {};

        for (let t of tarefas) {

            let categoria = t.categoria;

            if (!gruposCategoria[categoria]) {
                gruposCategoria[categoria] = [];
            }

            gruposCategoria[categoria].push(t);
        }

        let listaOrganizadaC = [];

        for (let grupo of Object.values(gruposCategoria)) {
            listaOrganizadaC.push(...grupo); 
        }

        return listaOrganizadaC;



    }




}




export function salvar_tarefa(chave,valor){

    localStorage.setItem(chave,valor)

}


export function capturar_todas_tarefas(){

    let tarefas = [];


    for (let i = 0; i < localStorage.length; i++) {
        let chave = localStorage.key(i);
        let valor = JSON.parse(localStorage.getItem(chave));
        tarefas.push(valor);
    }

    return tarefas;



}


export function editar_tarefa(nova_prioridade,novo_status,novo_alarme,nova_descricao,tarefas){
   

    for (let i=0; i<tarefas.length; i++){

        if (nova_descricao != ""){
            tarefas[i].descricao = nova_descricao
            
        }
        if (nova_prioridade != "manter_anterior"){
            tarefas[i].prioridade = nova_prioridade
            
        }

        if (novo_status != "manter_anterior"){
            tarefas[i].status = novo_status
            
        }           


        if (novo_alarme !=""){
            tarefas[i].alarme_escolha = "definido"
            tarefas[i].hora_alarme = novo_alarme
            
        }


        localStorage.setItem(tarefas[i].nome,JSON.stringify(tarefas[i]))
            

        
    
    
    
    }

   
}

export function listar_alarme_a_disparar() {

    let tarefas = capturar_todas_tarefas();
    let alarmes_a_disparar = [];

    let agora = new Date();

    for (let i = 0; i < tarefas.length; i++) {

        if (tarefas[i].alarme_escolha !== "definido") continue;

       
        let [dia, mes, ano] = tarefas[i].data.split("/");

        let dataHoraAlarme = new Date(
            `${ano}-${mes}-${dia}T${tarefas[i].hora_alarme}`
        );

        if (agora >= dataHoraAlarme) {
            alarmes_a_disparar.push(tarefas[i]);
        }
    }

    return alarmes_a_disparar;
}


export function disparar_alarme(lista_alarmes){
   
    for (let i = 0; i < lista_alarmes.length; i++) {

        alert("Alarme disparado para a tarefa " + lista_alarmes[i].nome);

        lista_alarmes[i].alarme_escolha = "disparado";

        localStorage.setItem(
            lista_alarmes[i].nome,
            JSON.stringify(lista_alarmes[i])
        );
    }

    


}

export function remover_tarefas(tarefas_a_serem_removidas){

    
    for (let tarefa in tarefas_a_serem_removidas){
        localStorage.removeItem(tarefas_a_serem_removidas[tarefa].nome)
    }

    return tarefas_a_serem_removidas

    


}



export function retornar_tarefas_marcadas (marcados) {

    let tarefas = [];

    marcados.forEach(checkbox => {
        let chave = checkbox.id;

        let dado = JSON.parse(localStorage.getItem(chave));

        tarefas.push(dado);

    });

    return tarefas;

}
