package com.hugo.UOLJogos.model;

import com.hugo.UOLJogos.model.enums.Grupo;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome deve ser preenchido")
    private String nome;
    @NotBlank(message = "Email deve ser preenchido")
    private String email;
    private String telefone;
    private Grupo grupo;
    private String codinome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return Objects.equals(id, jogador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
