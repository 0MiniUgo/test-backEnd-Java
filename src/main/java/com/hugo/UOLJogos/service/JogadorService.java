package com.hugo.UOLJogos.service;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.model.JogadorPostRequestBody;
import com.hugo.UOLJogos.model.JogadorPutRequestBody;
import com.hugo.UOLJogos.model.enums.Grupo;
import com.hugo.UOLJogos.repository.JogadorRepository;
import com.hugo.UOLJogos.service.exceptions.DatabaseUniqueException;
import com.hugo.UOLJogos.service.exceptions.NoVacancyException;
import com.hugo.UOLJogos.service.exceptions.PlayerNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository repository;
    private final ModelMapper mapper;
    public JogadorService(JogadorRepository repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    public List<Jogador> findAll(){

        return repository.findAll();
    }

    public Jogador findById(Long id){

        return repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }

    public Jogador save(JogadorPostRequestBody dto){

        Jogador jogador = mapper.map(dto, Jogador.class);
        adicionaCodinome(jogador);
        try{
            return repository.save(jogador);

        }catch (ConstraintViolationException ex){
            throw new NoVacancyException();
        }catch (DataIntegrityViolationException ex){
            throw new DatabaseUniqueException();
        }
    }

    public Jogador replace(Long id, JogadorPutRequestBody putRequestBody){

        Jogador jogador = findById(id);
        mapper.map(putRequestBody, jogador);

        try{
            return repository.save(jogador);

        }catch (DataIntegrityViolationException ex){
            throw new DatabaseUniqueException();
        }
    }

    public void delete(Long id){

        repository.delete(findById(id));
    }

    private void adicionaCodinome(Jogador jogador){

        List<String> codinomes = new ArrayList<>();

        if(jogador.getGrupo() == Grupo.VINGADORES){
            codinomes = VingadoresFromJson.Vingadores_StringToList();

        } else if (jogador.getGrupo() == Grupo.LIGA_DA_JUSTICA) {
            codinomes = LigaDaJusticaFromXML.LigaDaJustica_StringToList();
        }

        boolean existeNoBanco = true;

        for(String codinome : codinomes){
            existeNoBanco = repository.existsByCodinome(codinome);

            if(!existeNoBanco){
                jogador.setCodinome(codinome);
                return;
            }
        }

    }
}
