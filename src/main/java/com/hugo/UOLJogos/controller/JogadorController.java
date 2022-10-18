package com.hugo.UOLJogos.controller;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.service.JogadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jogadores")
public class JogadorController {

    private JogadorService service;
    public JogadorController(JogadorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> findAll(){

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> findById(@PathVariable Long id){

        return ResponseEntity.ok(service.findById(id));
    }
}
