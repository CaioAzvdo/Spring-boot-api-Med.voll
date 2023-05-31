package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private boolean ativo;


    public Paciente(DadosCadastrosPacientes dados){
        this.ativo=true;
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.email = dados.email();
        this.endereco = new Endereco(dados.endereco());
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void AtualizarInformacoes(DadosAtualizarPaciente dados) {
        if (dados.nome()!=null) {
            this.nome = dados.nome();
        }
        if (dados.telefone()!=null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco()!=null){
            this.endereco.AtualizarInformacoes(dados.endereco());
        }
    }
    public void ativar(){
        this.ativo = true;
    }
    public void excluir() {
        this.ativo = false;
    }
}
