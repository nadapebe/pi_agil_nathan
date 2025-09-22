package com.example.pi_nathan_agil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/filmes") // sem chaves
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    // POST /filmes - cadastrar
    @PostMapping
    public ResponseEntity<Filme> criar(@RequestBody Filme filme) {
        Filme salvo = service.salvar(filme);
        return ResponseEntity.created(URI.create("/filmes/" + salvo.getId())).body(salvo);
    }

    // GET /filmes - listar todos
    @GetMapping
    public List<Filme> listar() {
        return service.listar();
    }

    // DELETE /filmes/{id} - excluir (404 se não existir)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!service.existsById(id)) {                // <-- AJUSTE (3 linhas)
            return ResponseEntity.notFound().build();
        }
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
