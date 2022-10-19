package com.hugo.UOLJogos.model.enums;

public enum ContentType {
    XML(1, "xml"),
    JSON(2, "json");

    private final int valor;
    private final String descricao;

    ContentType(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }
    public String getDescricao() {
        return descricao;
    }
}
