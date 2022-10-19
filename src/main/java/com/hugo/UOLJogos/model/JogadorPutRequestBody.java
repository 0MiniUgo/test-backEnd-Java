package com.hugo.UOLJogos.model;

import com.hugo.UOLJogos.model.enums.Grupo;
import lombok.Data;

@Data
public class JogadorPutRequestBody {
    private String nome;
    private String email;
    private String telefone;
}
