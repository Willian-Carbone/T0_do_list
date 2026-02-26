//configuração inicial

document.addEventListener("DOMContentLoaded", function() {
    data_header();
    hora_header();
    atualizar_lista();
    
    
});

const segundo = 1000;
const minuto = segundo * 60;



setInterval(function() {
    hora_header();
}, segundo);


setInterval(function() {
    disparar_alarme(listar_alarme_a_disparar());
}, minuto);




let hoje = new Date().toISOString().split("T")[0];
document.querySelector("#data_termino").min = hoje;



function atualizar_lista(lista = null) {

    let mostrador_tarefas = document.getElementById("container_tarefas");
    mostrador_tarefas.innerHTML = "";

    let html = "";

    let tarefas = lista || capturar_todas_tarefas();


    for (let i = 0; i < tarefas.length; i++) {

        let valor = tarefas[i];
        



        html += `
            <div class="item">
                <input type="checkbox" id="${valor.nome}">
                <label for="${valor.nome}">
                    nome: ${valor.nome},
                    desc: ${valor.descricao},
                    data: ${valor.data},
                    hora: ${valor.hora_maxima},
                    categoria: ${valor.categoria},
                    status: ${valor.status},
                    prioridade: ${valor.prioridade},
                    alarme: ${valor.alarme_escolha},
                    hora: ${valor.hora_alarme}
                </label>
            </div>

            <br>
        `;
    }

    mostrador_tarefas.innerHTML = html;
}




function data_header() {
    document.getElementById("data").innerHTML = new Date().toLocaleDateString()

}

function hora_header() {
    document.getElementById("hora").innerHTML = new Date().toLocaleTimeString()

}

function mostrarFormulario(id) {
    document.querySelectorAll(".formulario")
    .forEach(f => f.classList.remove("ativo"));

    document.getElementById(id)
    .classList.add("ativo");
}

//metodo pra mostr o range ao usuario


const range = document.getElementById("prioridade_tarefa");
const valor = document.getElementById("valor_prioridade");

range.addEventListener("input", () => {
    valor.textContent = range.value; 
})





//garante a atualizaçao de log smepre que um form for submitado
const formularios = document.querySelectorAll("form");

formularios.forEach(form => {
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        atualizar_log(this);
    });
});


//metodos de atualização de log

function atualizar_log(objeto){


    const log = document.getElementById("log")


    if (objeto.id == "info_criacao"){
        

        let nome = objeto.querySelector("#nome_tarefa").value
        let descricao = objeto.querySelector("#descricao_tarefa").value

        let data_nao_fomatada = objeto.querySelector("#data_termino").value;
        let data = new Date(data_nao_fomatada).toLocaleDateString("pt-BR");


        
        let categoria =objeto.querySelector("input[name='categoria']:checked").value;
        let status= objeto.querySelector("input[name='status']:checked").value;
        let prioridade = objeto.querySelector("#prioridade_tarefa").value
        let hora_maxima = objeto.querySelector("#hora_maxima").value
        
        
        let alarme_escolha ="definido"
        let hora_alarme = objeto.querySelector("#hora_alarme").value
        
        if (hora_alarme == ""){
            alarme_escolha = "Optou por nao definir"
            hora_alarme = "Alarme nao informado"

        }
        
        log.innerHTML = 
        `a seguinte tarefa foi salva <br>
        nome:${nome}, descricao:${descricao} 
        <br>
        data termino: ${data} , hora maxima de termino: ${hora_maxima} <br>
        categoria: ${categoria} , status: ${status} , prioridade: ${prioridade} <br>
        alarme: ${alarme_escolha} , hora do alarme: ${hora_alarme}` 


        const chave=nome
        let valor = new Tarefa(nome,descricao,data,hora_maxima,categoria,status,prioridade,alarme_escolha,hora_alarme)

        salvar_tarefa(chave,JSON.stringify(valor))
        atualizar_lista()


    

    }


    else if (objeto.id == "info_edicao"){

        let nova_prioridade = objeto.querySelector("#mudanca_prioridade").value
        let novo_status = objeto.querySelector("#mudanca_status").value
        let novo_alarme = objeto.querySelector("#mudanca_alarme").value
        let nova_descricao = objeto.querySelector("#mudanca_descricao").value
        
        editar_tarefa(nova_prioridade,novo_status,novo_alarme,nova_descricao)
        atualizar_lista()
        


        log.innerHTML = "Tarefas editadas com sucesso"
    }



    
    else if (objeto.id == "info_remocao"){

        quantiade_removida = remover_tarefas()



        log.innerHTML = `Foram removidas ${quantiade_removida} tarefas`
        
        atualizar_lista()


        
    }

    else if (objeto.id == "info_ordenacao"){

        let lista_ordenada = ordenar_tarefas()
        atualizar_lista(lista_ordenada)
        log.innerHTML = "tarefas ordenadas na forma especificada"
    }


    else{
        log.innerHTML = "escolha uma opção primeiro"
    }


    return false;


}





//classes e features do backend

class Tarefa{

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


function salvar_tarefa(chave,valor){

    localStorage.setItem(chave,valor)

}

function remover_tarefas(){

    let tarefas_a_serem_removidas = capturar_tarefas_por_id()
    for (let tarefa in tarefas_a_serem_removidas){
        localStorage.removeItem(tarefas_a_serem_removidas[tarefa].nome)
    }

    return tarefas_a_serem_removidas.length

    
}

function capturar_todas_tarefas(){

    let tarefas = [];


    for (let i = 0; i < localStorage.length; i++) {
        let chave = localStorage.key(i);
        let valor = JSON.parse(localStorage.getItem(chave));
        tarefas.push(valor);
    }

    return tarefas;



}


function  capturar_tarefas_por_id(){

    let tarefas = [];
    


    let marcados = document.querySelectorAll(".item input[type='checkbox']:checked");

    marcados.forEach(checkbox => {
        let chave = checkbox.id;

        let dado = JSON.parse(localStorage.getItem(chave));

        tarefas.push(dado);

    });

    return tarefas;


}

function editar_tarefa(nova_prioridade,novo_status,novo_alarme,nova_descricao){
    let tarefas = capturar_tarefas_por_id()

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


// ordenar cria mapas, adiciona a chave categoria o objeto com a categoria especificada e junat tudo em uma lista

function ordenar_tarefas(){

    let escolha_de_ordenacao = document.getElementById("escolha_ordenacao")
    let tarefas = capturar_todas_tarefas()


    switch (escolha_de_ordenacao.value) {
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
            listaOrganizadaP.push(...grupo); // operador spread , evita arraiy aninhado
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





function listar_alarme_a_disparar() {

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



function disparar_alarme(lista_alarmes){
   
    for (let i = 0; i < lista_alarmes.length; i++) {

        alert("Alarme disparado para a tarefa " + lista_alarmes[i].nome);

        lista_alarmes[i].alarme_escolha = "disparado";

        localStorage.setItem(
            lista_alarmes[i].nome,
            JSON.stringify(lista_alarmes[i])
        );
    }

    atualizar_lista();


}

    

