package com.hugo.UOLJogos.utils;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.model.JogadorPostRequestBody;
import com.hugo.UOLJogos.model.JogadorPutRequestBody;
import com.hugo.UOLJogos.model.enums.Grupo;

public class JogadorUtils {

    public static Jogador createJogador(){

        return new Jogador(1L,
                "Rick",
                "rick@gmail.com",
                "19111111111",
                Grupo.VINGADORES,
                "Hulk");
    }

    public static JogadorPostRequestBody jogadorToBeSaved(){

        return new JogadorPostRequestBody("Rick",
                "rick@gmail.com",
                "19111111111",
                Grupo.VINGADORES);
    }

    public static JogadorPutRequestBody jogadorToBeUpdated(){

        return new JogadorPutRequestBody("Hugo",
                "hugo@gmail.com",
                "19111111111");
    }

    public static Jogador updatedJogador(){

        return new Jogador(1L,
                "Hugo",
                "hugo@gmail.com",
                "19111111111",
                Grupo.VINGADORES,
                "Hulk");
    }
}
