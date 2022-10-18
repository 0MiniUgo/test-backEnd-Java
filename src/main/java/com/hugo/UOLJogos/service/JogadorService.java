package com.hugo.UOLJogos.service;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.repository.JogadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogadorService {

    private JogadorRepository repository;
    public JogadorService(JogadorRepository repository) {
        this.repository = repository;
    }

    public List<Jogador> findAll(){

        return repository.findAll();
    }

    public Jogador findById(Long id){

        return repository.findById(id).orElseThrow(RuntimeException::new);
    }
}
