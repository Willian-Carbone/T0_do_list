





export function validar_data (data){


    const hoje = new Date();
    const dia = String(hoje.getDate()).padStart(2, "0");
    const mes = String(hoje.getMonth() + 1).padStart(2, "0");
    const ano = hoje.getFullYear();
    const dataFormatada = `${dia}/${mes}/${ano}`;





    return /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/.test(data) && data >= dataFormatada



}

export function validar_status(status){

    return /Feita||Fazendo||A fazer/.test(status)




}

export function validar_categoria(categoria){
    return /hobbyes||lazer||trabalho/.test(categoria)


}

export function validar_prioridade(prioridade){
    return /[1-5]/.teste(prioridade)


}

