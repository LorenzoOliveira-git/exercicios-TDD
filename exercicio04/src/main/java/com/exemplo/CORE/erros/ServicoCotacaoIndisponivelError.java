package com.exemplo.CORE.erros;

public class ServicoCotacaoIndisponivelError extends RuntimeException {
    public ServicoCotacaoIndisponivelError(String message){
        super(message);
    }
}
