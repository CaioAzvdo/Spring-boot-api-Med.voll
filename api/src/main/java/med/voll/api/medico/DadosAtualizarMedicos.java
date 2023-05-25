package med.voll.api.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.Endereco;

public record DadosAtualizarMedicos(
        @NotNull
        Long id,

        String nome,

        String Telefone,
        Endereco endereco) {
}

