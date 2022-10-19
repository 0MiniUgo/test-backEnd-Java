package com.hugo.UOLJogos.repository;

import com.hugo.UOLJogos.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    boolean existsByCodinome(String codinome);
}
