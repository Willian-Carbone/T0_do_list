import * as logica from "./LocalStorageControler.js"

let botoes = document.querySelectorAll(".botao");

botoes.forEach(botao => {
    botao.addEventListener("click", function() {

        let idFormulario = botao.dataset.form;

        mostrarFormulario(idFormulario);

    });
});



document.addEventListener("DOMContentLoaded", function() {
    dataHeader();
    horaHeader();
    atualizarLista();
    
    
});

const segundo = 1000;
const minuto = segundo * 60;



setInterval(function() {
    horaHeader();
}, segundo);


setInterval(function() {
    let alarmes= logica.listarAlarmesParaDisparar()
    logica.dispararAlarme(alarmes);
    atualizarLista();
}, minuto);




let hoje = new Date().toISOString().split("T")[0];
document.querySelector("#data_termino").min = hoje;



function atualizarLista(lista = null) {

    let mostradorTarefas = document.getElementById("container_tarefas");
    mostradorTarefas.innerHTML = "";

    let html = "";

    let tarefas = lista || logica.CapturarTarefas();


    for (let i = 0; i < tarefas.length; i++) {

        let valor = tarefas[i];
        



        html += `
            <div class="item">
                <input type="checkbox" id="${valor.nome}">
                <label for="${valor.nome}">
                    nome: ${valor.nome} %
                    desc: ${valor.descricao} %
                    data: ${valor.data} %
                    hora: ${valor.hora_maxima} %
                    categoria: ${valor.categoria} %
                    status: ${valor.status} %
                    prioridade: ${valor.prioridade} % 
                    alarme: ${valor.alarme_escolha} %
                    hora: ${valor.hora_alarme}
                </label>
            </div>

            
        `;
    }

    mostradorTarefas.innerHTML = html;
}




function dataHeader() {
    document.getElementById("data").innerHTML = new Date().toLocaleDateString()

}

function horaHeader() {
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
        atualizarLog(this);
    });
});



function atualizarLog(objeto){


    const log = document.getElementById("log")


    if (objeto.id === "info_criacao"){
        

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
        
        if (hora_alarme === ""){
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

        logica.salvarTarefa(chave,JSON.stringify(valor))
        atualizarLista()


    

    }


    else if (objeto.id === "info_edicao"){

        let novaPrioridade = objeto.querySelector("#mudanca_prioridade").value
        let novoStatus = objeto.querySelector("#mudanca_status").value
        let novoAlarme = objeto.querySelector("#mudanca_alarme").value
        let novaDescricao = objeto.querySelector("#mudanca_descricao").value

        let tarefasCapturadas = capturarTarefasPorIdentificador()
        
        logica.editarTarefa(novaPrioridade,novoStatus,novoAlarme,novaDescricao,tarefasCapturadas)
        atualizarLista()
        


        log.innerHTML = "Tarefas editadas com sucesso"
    }



    
    else if (objeto.id === "info_remocao"){

        let tarefas = capturarTarefasPorIdentificador()
        

        let quantidadeRemovida = logica.removerTarefas(tarefas)



        log.innerHTML = `Foram removidas ${quantidadeRemovida} tarefas`
        
        atualizarLista()


        
    }

    else if (objeto.id === "info_ordenacao"){

        let lista_ordenada = ordenarTarefas()
        atualizarLista(lista_ordenada)
        log.innerHTML = "tarefas ordenadas na forma especificada"
    }


    else{
        log.innerHTML = "escolha uma opção primeiro"
    }


    return false;


}











function  capturarTarefasPorIdentificador(){


    let marcados = document.querySelectorAll(".item input[type='checkbox']:checked");
    return logica.capturarTarefasMarcadas(marcados);


}









function ordenarTarefas(){

    let escolhaDeOrdenacao = document.getElementById("escolha_ordenacao")
    let tarefas = logica.CapturarTarefas()
    return logica.ordenador(tarefas, escolhaDeOrdenacao.value)


}






    

