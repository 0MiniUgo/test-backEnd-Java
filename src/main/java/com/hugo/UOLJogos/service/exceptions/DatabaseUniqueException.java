package com.hugo.UOLJogos.service.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DatabaseUniqueException extends DataIntegrityViolationException {
    public DatabaseUniqueException() {
        super("Nome ou Email já cadastrado");
    }
}
