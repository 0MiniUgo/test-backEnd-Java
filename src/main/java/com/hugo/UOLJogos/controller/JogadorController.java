package com.hugo.UOLJogos.controller;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.model.JogadorPostRequestBody;
import com.hugo.UOLJogos.model.JogadorPutRequestBody;
import com.hugo.UOLJogos.service.JogadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Jogador> save(@RequestBody JogadorPostRequestBody postRequestBody){
        Jogador jogador = service.save(postRequestBody);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(jogador.getId()).toUri();

        return ResponseEntity.created(uri).body(jogador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> replace(@PathVariable Long id, @RequestBody JogadorPutRequestBody putRequestBody){

        Jogador jogador = service.replace(id, putRequestBody);

        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
