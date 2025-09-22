package com.example.pi_nathan_agil;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilmeService {

    private final FilmeRepository repo;

    public FilmeService(FilmeRepository repo) {
        this.repo = repo;
    }

    public Filme salvar(Filme filme) {
        return repo.save(filme);
    }

    public List<Filme> listar() {
        return repo.findAll();
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }

    public boolean existsById(Long id) {   // <-- NOVO
        return repo.existsById(id);
    }
}