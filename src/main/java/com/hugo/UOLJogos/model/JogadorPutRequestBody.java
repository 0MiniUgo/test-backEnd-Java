package com.hugo.UOLJogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JogadorPutRequestBody {
    private String nome;
    private String email;
    private String telefone;
}
