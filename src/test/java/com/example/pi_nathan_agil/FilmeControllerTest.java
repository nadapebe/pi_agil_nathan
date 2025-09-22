package com.example.pi_nathan_agil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilmeController.class)
class FilmeControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean FilmeService service;

    private Filme novo(Long id) {
        Filme f = new Filme();
        f.setId(id);
        f.setTitulo("Matrix");
        f.setDescricao("Ação/Sci-Fi");
        f.setDuracao(2);
        f.setDiretor("Wachowski");
        return f;
    }

    @Test
    void criar_201() throws Exception {
        Mockito.when(service.salvar(any(Filme.class))).thenReturn(novo(1L));

        String json = """
          {"titulo":"Matrix","descricao":"Ação/Sci-Fi","duracao":2,"diretor":"Wachowski"}
        """;

        mvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/filmes/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Matrix"));
    }

    @Test
    void listar_200() throws Exception {
        Mockito.when(service.listar()).thenReturn(List.of(novo(1L), novo(2L)));

        mvc.perform(get("/filmes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deletar_existente_204() throws Exception {
        Mockito.when(service.existsById(1L)).thenReturn(true);

        mvc.perform(delete("/filmes/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(service).deletar(1L);
    }

    @Test
    void deletar_inexistente_404() throws Exception {
        Mockito.when(service.existsById(999L)).thenReturn(false);

        mvc.perform(delete("/filmes/999"))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.never()).deletar(eq(999L));
    }
}
