import * as logica from "./LocalStorageControler.js"

let botoes = document.querySelectorAll(".botao");

botoes.forEach(botao => {
    botao.addEventListener("click", function() {

        let idFormulario = botao.dataset.form;

        mostrarFormulario(idFormulario);

    });
});



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
    let alarmes= logica.listar_alarme_a_disparar()
    logica.disparar_alarme(alarmes);
    atualizar_lista();
}, minuto);




let hoje = new Date().toISOString().split("T")[0];
document.querySelector("#data_termino").min = hoje;



function atualizar_lista(lista = null) {

    let mostrador_tarefas = document.getElementById("container_tarefas");
    mostrador_tarefas.innerHTML = "";

    let html = "";

    let tarefas = lista || logica.capturar_todas_tarefas();


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




const range = document.getElementById("prioridade_tarefa");
const valor = document.getElementById("valor_prioridade");

range.addEventListener("input", () => {
    valor.textContent = range.value; 
})






const formularios = document.querySelectorAll("form");

formularios.forEach(form => {
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        atualizar_log(this);
    });
});



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
        let valor = new logica.Tarefa(nome,descricao,data,hora_maxima,categoria,status,prioridade,alarme_escolha,hora_alarme)

        logica.salvar_tarefa(chave,JSON.stringify(valor))
        atualizar_lista()


    

    }


    else if (objeto.id == "info_edicao"){

        let nova_prioridade = objeto.querySelector("#mudanca_prioridade").value
        let novo_status = objeto.querySelector("#mudanca_status").value
        let novo_alarme = objeto.querySelector("#mudanca_alarme").value
        let nova_descricao = objeto.querySelector("#mudanca_descricao").value

        let tarefas_capturadas = capturar_tarefas_por_id()
        
        logica.editar_tarefa(nova_prioridade,novo_status,novo_alarme,nova_descricao,tarefas_capturadas)
        atualizar_lista()
        


        log.innerHTML = "Tarefas editadas com sucesso"
    }



    
    else if (objeto.id == "info_remocao"){

        let tarefas = capturar_tarefas_por_id()
        

        let quantidade_removida = logica.remover_tarefas(tarefas)



        log.innerHTML = `Foram removidas ${quantidade_removida} tarefas`
        
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











function  capturar_tarefas_por_id(){


    let marcados = document.querySelectorAll(".item input[type='checkbox']:checked");
    let tarefas_capturadas = logica.retornar_tarefas_marcadas(marcados)

    return tarefas_capturadas;


}









function ordenar_tarefas(){

    let escolha_de_ordenacao = document.getElementById("escolha_ordenacao")
    let tarefas = logica.capturar_todas_tarefas()
    let lista_ordenada = logica.ordenador(tarefas,escolha_de_ordenacao.value)

    return lista_ordenada


}






    

