package com.hugo.UOLJogos.model;

import com.hugo.UOLJogos.model.enums.Grupo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome deve ser preenchido")
    @Column(unique = true)
    private String nome;
    @NotBlank(message = "Email deve ser preenchido")
    @Column(unique = true)
    private String email;
    private String telefone;
    private Grupo grupo;
    @Column(unique = true)
    @NotBlank
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
