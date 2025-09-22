package com.example.pi_nathan_agil;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FilmeEntityTest {

    @Test
    void prePersist_preencheDataCadastro() {
        Filme f = new Filme();
        f.prePersist();
        assertThat(f.getDataCadastro()).isNotNull();
    }
}
