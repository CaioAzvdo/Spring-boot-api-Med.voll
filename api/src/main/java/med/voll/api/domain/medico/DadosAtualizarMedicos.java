package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.Endereco;

public record DadosAtualizarMedicos(
        @NotNull
        Long id,

        String nome,

        String Telefone,
        Endereco endereco) {
}

