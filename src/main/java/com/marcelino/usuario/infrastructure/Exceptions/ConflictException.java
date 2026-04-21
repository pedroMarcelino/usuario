package com.marcelino.usuario.infrastructure.Exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException (String mensagem){
        super(mensagem);
    }

    public ConflictException ( String mensagem, Throwable throwable ){
        super(mensagem);
    }
}
