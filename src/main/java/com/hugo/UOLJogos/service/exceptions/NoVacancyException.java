package com.hugo.UOLJogos.service.exceptions;

import javax.validation.ConstraintViolationException;

public class NoVacancyException extends RuntimeException {
    public NoVacancyException() {
        super("Não há mais codinomes livres nesse grupo");
    }
}
