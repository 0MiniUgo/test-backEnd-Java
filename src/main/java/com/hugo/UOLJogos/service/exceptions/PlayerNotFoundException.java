package com.hugo.UOLJogos.service.exceptions;

public class PlayerNotFoundException extends RuntimeException{

    public PlayerNotFoundException(Object id) {

        super("Player not found, id: " + id);
    }
}
