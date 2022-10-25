package com.hugo.UOLJogos.model;

import com.hugo.UOLJogos.model.enums.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JogadorPostRequestBody {
    private String nome;
    private String email;
    private String telefone;
    private Grupo grupo;
}
