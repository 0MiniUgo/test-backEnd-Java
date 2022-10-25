package com.hugo.UOLJogos.service;

import com.hugo.UOLJogos.model.Jogador;
import com.hugo.UOLJogos.model.JogadorPostRequestBody;
import com.hugo.UOLJogos.model.JogadorPutRequestBody;
import com.hugo.UOLJogos.repository.JogadorRepository;
import com.hugo.UOLJogos.service.exceptions.DatabaseUniqueException;
import com.hugo.UOLJogos.service.exceptions.NoVacancyException;
import com.hugo.UOLJogos.service.exceptions.PlayerNotFoundException;
import com.hugo.UOLJogos.utils.JogadorUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class JogadorServiceTest {

    @InjectMocks
    private JogadorService service;
    @Mock
    private JogadorRepository repository;

    @Test
    void findAll_WillReturnAllPlayers(){

        Mockito.when(repository.findAll()).thenReturn(List.of(JogadorUtils.createJogador()));

        List<Jogador> jogadores = service.findAll();

                Assertions.assertThat(jogadores)
                        .hasSize(1)
                        .isNotEmpty()
                        .isNotNull();
    }

    @Test
    void findById_WillReturnOnePlayer_WhenSuccessful(){

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(JogadorUtils.createJogador()));

        Jogador jogador = service.findById(1L);

        Assertions.assertThat(jogador)
                .isNotNull()
                .isEqualTo(JogadorUtils.createJogador());
    }

    @Test
    void findById_WillThrowAPlayerNotFoundException_WhenPlayerNotExists(){

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(PlayerNotFoundException.class)
                .isThrownBy(() -> service.findById(1L));
    }

    @Test
    void save_WillReturnAPlayer_WhenSuccessful(){

        Mockito.when(repository.save(ArgumentMatchers.any(Jogador.class)))
                .thenReturn(JogadorUtils.createJogador());

        JogadorPostRequestBody jogadorToBeSaved = JogadorUtils.jogadorToBeSaved();
        Jogador savedJogador = service.save(jogadorToBeSaved);

        Assertions.assertThat(savedJogador)
                .isNotNull()
                .isEqualTo(JogadorUtils.createJogador());
    }

    @Test
    void save_WillThrowADatabaseUniqueException_WhenNameOrEmailIsNotUnique(){

        Mockito.when(repository.save(ArgumentMatchers.any(Jogador.class)))
                .thenThrow(DatabaseUniqueException.class);

        JogadorPostRequestBody jogadorToBeSaved = JogadorUtils.jogadorToBeSaved();

        Assertions.assertThatExceptionOfType(DatabaseUniqueException.class)
                .isThrownBy(() -> service.save(jogadorToBeSaved));
    }

    @Test
    void save_WillThrowANoVacancyException_WhenNoCodenamesAvailable(){

        Mockito.when(repository.save(ArgumentMatchers.any(Jogador.class)))
                .thenThrow(ConstraintViolationException.class);

        JogadorPostRequestBody jogadorToBeSaved = JogadorUtils.jogadorToBeSaved();

        Assertions.assertThatExceptionOfType(NoVacancyException.class)
                .isThrownBy(() -> service.save(jogadorToBeSaved));
    }

    @Test
    void replace_WillReturnAPlayer_WhenSuccessful(){

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(JogadorUtils.createJogador()));
        Mockito.when(repository.save(ArgumentMatchers.any(Jogador.class)))
                .thenReturn(JogadorUtils.updatedJogador());

        JogadorPutRequestBody jogadorToBeUpdated = JogadorUtils.jogadorToBeUpdated();
        Jogador expected = service.replace(1L, jogadorToBeUpdated);

        Assertions.assertThat(expected)
                .isNotNull()
                .isEqualTo(JogadorUtils.updatedJogador());
    }

    @Test
    void replace_WillThrowADatabaseUniqueException_WhenNameOrEmailIsNotUnique(){

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(JogadorUtils.createJogador()));
        Mockito.when(repository.save(ArgumentMatchers.any(Jogador.class)))
                .thenThrow(DatabaseUniqueException.class);

        JogadorPutRequestBody jogadorToBeUpdated = JogadorUtils.jogadorToBeUpdated();

        Assertions.assertThatExceptionOfType(DatabaseUniqueException.class)
                .isThrownBy(() -> service.replace(1L, jogadorToBeUpdated));
    }

    @Test
    void delete(){

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(JogadorUtils.createJogador()));

        Jogador jogadorToBeDeleted = JogadorUtils.createJogador();

        service.delete(1L);
        Mockito.verify(repository).delete(jogadorToBeDeleted);
    }
}