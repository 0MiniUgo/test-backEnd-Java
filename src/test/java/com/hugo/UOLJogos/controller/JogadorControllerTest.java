package com.hugo.UOLJogos.controller;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.model.JogadorPostRequestBody;
import com.hugo.UOLJogos.model.JogadorPutRequestBody;
import com.hugo.UOLJogos.service.JogadorService;
import com.hugo.UOLJogos.service.exceptions.DatabaseUniqueException;
import com.hugo.UOLJogos.service.exceptions.NoVacancyException;
import com.hugo.UOLJogos.service.exceptions.PlayerNotFoundException;
import com.hugo.UOLJogos.utils.JogadorUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(JogadorController.class)
class JogadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JogadorService service;

    @Test
    void findAll_WillReturnAListOfPlayers_WhenSuccessful() throws Exception {

        Mockito.when(service.findAll()).thenReturn(List.of(JogadorUtils.createJogador()));

        mockMvc.perform(get("/api/jogadores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Rick")));
    }

    @Test
    void findById_WillReturnAPlayer_WhenSuccessful() throws Exception {

        Long id = JogadorUtils.createJogador().getId();

        Mockito.when(service.findById(id))
                .thenReturn(JogadorUtils.createJogador());

        mockMvc.perform(get("/api/jogadores/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Rick")))
                .andExpect(jsonPath("$.email", is("rick@gmail.com")));
    }

    @Test
    void findById_WillThrowNotFound_WhenPlayerNotFound() throws Exception{

        Long id = JogadorUtils.createJogador().getId();

        Mockito.when(service.findById(id))
                .thenThrow(PlayerNotFoundException.class);


        mockMvc.perform(get("/api/jogadores/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void save_WillReturnAPlayer_WhenSuccessful() throws Exception{

        String json = "{\"nome\":\"Rick\",\"email\":\"rick@gmail.com\",\"telefone\":\"19111111111\",\"grupo\":0}";
        Jogador jogador = JogadorUtils.createJogador();

        Mockito.when(service.save(any(JogadorPostRequestBody.class)))
                .thenReturn(jogador);

        mockMvc.perform(post("/api/jogadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(jsonPath("$.nome", is(jogador.getNome())))
                .andExpect(jsonPath("$.email", is(jogador.getEmail())))
                .andExpect(jsonPath("$.telefone", is(jogador.getTelefone())))
                .andExpect(jsonPath("$.grupo", is(jogador.getGrupo().toString())))
                .andExpect(jsonPath("$.codinome", is(jogador.getCodinome())))
                .andExpect(status().isCreated());
    }

    @Test
    void save_WillThrowBadRequest_WhenNoVacanciesAvailable() throws Exception{

        String json = "{\"nome\":\"Rick\",\"email\":\"rick@gmail.com\",\"telefone\":\"19111111111\",\"grupo\":0}";

        Mockito.when(service.save(any(JogadorPostRequestBody.class)))
                .thenThrow(NoVacancyException.class);

        mockMvc.perform(post("/api/jogadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void save_WillThrowBadRequest_WhenNameOrEmailIsNotUnique() throws Exception{

        String json = "{\"nome\":\"Rick\",\"email\":\"rick@gmail.com\",\"telefone\":\"19111111111\",\"grupo\":0}";

        Mockito.when(service.save(any(JogadorPostRequestBody.class)))
                .thenThrow(DatabaseUniqueException.class);

        mockMvc.perform(post("/api/jogadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void replace_WillReturnAPlayer_WhenSuccessful() throws Exception{

        String json = "{\"nome\":\"Rick\",\"email\":\"rick1@gmail.com\",\"telefone\":\"19111111111\"}";

        JogadorPutRequestBody jogadorToBeUpdated = JogadorUtils.jogadorToBeUpdated();
        Long id = 1L;

        Mockito.when(service.replace(anyLong(), any(JogadorPutRequestBody.class)))
                .thenReturn(JogadorUtils.updatedJogador());

        mockMvc.perform(put("/api/jogadores/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(jogadorToBeUpdated.getNome())))
                .andExpect(jsonPath("$.email", is(jogadorToBeUpdated.getEmail())));
    }

    @Test
    void replace_WillThrowBadRequest_WhenNameOrEmailIsNotUnique() throws Exception {

        String json = "{\"nome\":\"Rick\",\"email\":\"rick1@gmail.com\",\"telefone\":\"19111111111\"}";
        Long id = 1L;

        Mockito.when(service.replace(anyLong(), any(JogadorPutRequestBody.class)))
                .thenThrow(DatabaseUniqueException.class);

        mockMvc.perform(put("/api/jogadores/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_WillReturnNoContent_WhenSuccessful() throws Exception{

        Long id = 1L;
        Mockito.when(service.findById(id))
                .thenReturn(JogadorUtils.createJogador());
        Mockito.doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/jogadores/delete/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete__WillThrowNotFound_WhenPlayerNotFound() throws Exception {

        Long id = 1L;
        Mockito.when(service.findById(id))
                .thenThrow(PlayerNotFoundException.class);

        mockMvc.perform(get("/api/jogadores/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}