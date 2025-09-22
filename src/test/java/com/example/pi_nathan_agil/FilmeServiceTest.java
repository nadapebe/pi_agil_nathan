package com.example.pi_nathan_agil;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilmeServiceTest {

    @Test
    void salvar_listar_deletar_exists() {
        FilmeRepository repo = Mockito.mock(FilmeRepository.class);
        FilmeService service = new FilmeService(repo);

        Filme f = new Filme();
        f.setTitulo("Matrix");

        Mockito.when(repo.save(Mockito.any(Filme.class))).thenAnswer(inv -> {
            Filme x = inv.getArgument(0);
            x.setId(1L);
            return x;
        });
        Filme salvo = service.salvar(f);
        assertThat(salvo.getId()).isEqualTo(1L);

        Mockito.when(repo.findAll()).thenReturn(List.of(salvo));
        assertThat(service.listar()).hasSize(1);

        Mockito.when(repo.existsById(1L)).thenReturn(true);
        assertThat(service.existsById(1L)).isTrue();

        service.deletar(1L);
        Mockito.verify(repo).deleteById(1L);
    }
}